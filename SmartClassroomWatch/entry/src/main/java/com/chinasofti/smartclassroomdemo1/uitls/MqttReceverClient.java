package com.chinasofti.smartclassroomdemo1.uitls;

import com.ibm.mqtt.*;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.util.Date;

public class MqttReceverClient   {

    HiLogLabel hiLogLabel = new HiLogLabel(HiLog.LOG_APP,0x10001,"mqtt");
    // constants used to define MQTT connection status
    public enum MQTTConnectionStatus {
        INITIAL, // initial status
        CONNECTING, // attempting to connect
        CONNECTED, // connected
        CONNECTEDANDSUB, // connected and subscribed
        NOTCONNECTED_WAITINGFORINTERNET, // can't connect because the phone
        // does not have Internet access
        NOTCONNECTED_USERDISCONNECT, // user has explicitly requested
        // disconnection
        NOTCONNECTED_DATADISABLED, // can't connect because the user
        // has disabled data access
        NOTCONNECTED_UNKNOWNREASON // failed to connect for some reason
    }


    // MQTT constants
    public static final int MAX_MQTT_CLIENTID_LENGTH = 22;
    // status of MQTT client connection
    private MQTTConnectionStatus connectionStatus = MQTTConnectionStatus.INITIAL;
    private IMqttClient mqttClient = null;

    public static String MQTT_CLIENT_ID = "";
    private String brokerHostName = null;
    private MqttPersistence usePersistence = null;
    private boolean cleanStart = false;
    private String mqttClientId = null;

    private String[] topicsAll={"ti"};
    private int[] qualitiesOfService;
    private short keepAliveSeconds = 20 * 60;
    private boolean isFirstConnected = false;

    MqttSimpleCallback myMqttSimpleCallback;

    public MqttReceverClient(MqttSimpleCallback myMqttSimpleCallback) {
        this.myMqttSimpleCallback = myMqttSimpleCallback;
    }

    public void onStart() {


//        String mqttConnSpec = "tcp://" + brokerHostName + "@"
//                + brokerPortNumber;
        //String mqttConnSpec = "tcp://server.natappfree.cc@34096";
        String mqttConnSpec = "tcp://server.natappfree.cc@42666";
       // String mqttConnSpec = "tcp://121.36.35.193@1883";
        qualitiesOfService = new int[topicsAll.length];
        for (int i = 0; i < topicsAll.length; i++) {
            qualitiesOfService[i] = 0;
        }
        try {
            // define the connection to the broker
            mqttClient = MqttClient.createMqttClient(mqttConnSpec,
                    usePersistence);

            // register this client app has being able to receive messages
            mqttClient.registerSimpleHandler(myMqttSimpleCallback);
        } catch (MqttException e) {
            // something went wrong!
            mqttClient = null;

            connectionStatus = MQTTConnectionStatus.NOTCONNECTED_UNKNOWNREASON;


        }
        connectToBroker();

    }

    /*
     * Send a request to the message broker to be sent messages published with
     * the specified topic name. Wildcards are allowed.
     */
    public boolean subscribeToTopic(String[] topicsAll) {
        boolean subscribed = false;
        if (isAlreadyConnected() == false) {
            // quick sanity check - don't try and subscribe if we
            // don't have a connection

            subscribed = false;

        } else {
            try {
                // mqttClient.unsubscribe(topicsAll);
                mqttClient.subscribe(topicsAll, qualitiesOfService);
                subscribed = true;

            } catch (MqttNotConnectedException e) {
                HiLog.error(hiLogLabel,"subscribe failed - MQTT not connected", e);
            } catch (IllegalArgumentException e) {
                HiLog.error(hiLogLabel, "subscribe failed - illegal argument", e);
            } catch (MqttException e) {
                HiLog.error(hiLogLabel, "subscribe failed - MQTT exception", e);
            } catch (Exception e) {
                HiLog.error(hiLogLabel, "subscribe failed - exception", e);
            }
        }

        if (subscribed == false) {
            //
            // inform the app of the failure to subscribe so that the UI can
            // display an error
            HiLog.error(hiLogLabel, "订阅失败");
        }

        return subscribed;
    }
    private boolean isAlreadyConnected() {
        return ((mqttClient != null) && (mqttClient.isConnected() == true));
    }

    /*
    @Override
    public void connectionLost() throws Exception {

            // we are still online
            // the most likely reason for this connectionLost is that we've
            // switched from wifi to cell, or vice versa
            // so we try to reconnect immediately
            //


            // jxh:重连服务器
            connectionStatus = MQTTConnectionStatus.NOTCONNECTED_UNKNOWNREASON;
            // inform the app that we are not connected any more, and are
            // attempting to reconnect
            // broadcastServiceStatus("尝试重连");//Connection lost -
            // reconnecting...

            // try to reconnect

            int i = 0;
            while (!connectToBroker() && i++ < 5) {
            }
            isFirstConnected = false;


    }

     */
    public boolean connectToBroker() {

        if (mqttClient == null)
            return false;

        boolean ret = false;
        try {
            try {
                if (isAlreadyConnected()) {
                    mqttClient.unsubscribe(topicsAll);
                    mqttClient.disconnect();
                }
            } catch (MqttPersistenceException ee) {
                HiLog.error(hiLogLabel,"mqtt", "disconnect failed - persistence exception", ee);
            } catch (MqttException ee) {
                ee.printStackTrace();
            }

            // try to connect
            mqttClient
                    .connect(generateClientId(), cleanStart, keepAliveSeconds);

            // 连续订阅两次会提示失败
            if (subscribeToTopic(topicsAll))
                connectionStatus = MQTTConnectionStatus.CONNECTEDANDSUB;
            else {
                connectionStatus = MQTTConnectionStatus.CONNECTED;
                return false;
            }



            return true;
        } catch (MqttException e) {
            // something went wrong!
            // JXH:是否要加上以下语句呢?
            try {
                if (isAlreadyConnected()) {
                    mqttClient.unsubscribe(topicsAll);
                    mqttClient.disconnect();
                }
            } catch (MqttPersistenceException ee) {
                HiLog.error(hiLogLabel, "disconnect failed - persistence exception", ee);
            } catch (MqttException ee) {
                ee.printStackTrace();
            }
            connectionStatus = MQTTConnectionStatus.NOTCONNECTED_UNKNOWNREASON;

            HiLog.error(hiLogLabel,"无法连接到推送服务器");

            HiLog.error(hiLogLabel, String.valueOf(e.getCause()));

            return ret;
        }
    }
//    @Override
//    public void publishArrived(String s, byte[] bytes, int i, boolean b) throws Exception {
//        String messageBody = new String(bytes);
//        HiLog.error(hiLogLabel,messageBody);
//    }

    private String generateClientId() {
        // generate a unique client id if we haven't done so before, otherwise
        // re-use the one we already have

        if (mqttClientId == null) {
            // generate a unique client ID - I'm basing this on a combination of
            // the phone device id and the current timestamp
            String timestamp = "" + (new Date()).getTime();
//            String android_id = Settings.System.getString(getContentResolver(),
//                    Secure.ANDROID_ID);
//            mqttClientId = MQTT_CLIENT_ID + timestamp + android_id;
            mqttClientId = MQTT_CLIENT_ID + timestamp ;

            // truncate - MQTT spec doesn't allow client ids longer than 23
            // chars
            if (mqttClientId.length() > MAX_MQTT_CLIENTID_LENGTH) {
                mqttClientId = mqttClientId.substring(0,
                        MAX_MQTT_CLIENTID_LENGTH);
            }
        }

        return mqttClientId;
    }

    /**
     * 发布消息
     * @param topic 主题
     * @param msg 消息
     * @param thisQoS 消息质量
     * @param retained 是否保留
     * @return
     */
    public  void publish(final String topic,String msg, int thisQoS, boolean retained) {
        if (topic!=null && !"".equals(topic) && msg!=null && !"".equals(msg)) {

            try {
                mqttClient.publish(topic,msg.getBytes(),0,false);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public  void publish(final String topic,String msg) {
        publish(topic,msg,0,false);
    }
}
