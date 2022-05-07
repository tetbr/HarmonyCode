package com.chinasofti.smartclassroomdemo1.slice;

import com.alibaba.fastjson.JSON;
import com.chinasofti.smartclassroomdemo1.data.AnswerResult;
import com.chinasofti.smartclassroomdemo1.data.QOption;
import com.chinasofti.smartclassroomdemo1.data.Question;
import com.chinasofti.smartclassroomdemo1.uitls.MqttReceverClient;
import com.chinasofti.smartclassroomdemo1.ResourceTable;
import com.chinasofti.smartclassroomdemo1.data.RecycleSimpleItemProvider;
import com.ibm.mqtt.MqttSimpleCallback;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;
import ohos.eventhandler.EventHandler;
import ohos.eventhandler.EventRunner;
import ohos.eventhandler.InnerEvent;

import java.util.ArrayList;
import java.util.List;


public class MainAbilitySlice extends AbilitySlice {


    private  Text title;
    private Text tContent;
    private Button button;
    private DirectionalLayout dlResult;
    private Text tCorrctAnswer;
    private Text tMyAnswer;
    private ListContainer mListContainer;


    MqttReceverClient mqttReceverClient;
    private List<QOption> mListInfo = new ArrayList<>();
    Question question;
    MyEventHandler myHandler;
    long time = 0;

    String myAnswer = "";
    String correctAnswer = "";
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        setUIContent(ResourceTable.Layout_main);

        mListContainer = (ListContainer) findComponentById(ResourceTable.Id_list_container);

        title = (Text)findComponentById(ResourceTable.Id_title) ;
        tContent = (Text)findComponentById(ResourceTable.Id_t_content);
        button = (Button) findComponentById(ResourceTable.Id_button) ;

        dlResult = (DirectionalLayout)findComponentById(ResourceTable.Id_dl_result);
        tCorrctAnswer = (Text)findComponentById(ResourceTable.Id_t_corrct_answer) ;
        tMyAnswer = (Text)findComponentById(ResourceTable.Id_t_my_answer) ;

        dlResult.setVisibility(Component.INVISIBLE);//把答题结果 隐藏

        //题干  以跑马灯的方式显示
        tContent.setTruncationMode(Text.TruncationMode.AUTO_SCROLLING);
        tContent.setAutoScrollingCount(-1);
        tContent.startAutoScrolling();
        RecycleSimpleItemProvider itemProvider = new RecycleSimpleItemProvider(mListInfo, this);
        itemProvider.setCacheSize(20);
        mListContainer.setItemProvider(itemProvider);
        mListContainer.setItemClickedListener(new ListContainer.ItemClickedListener() {
            @Override
            public void onItemClicked(ListContainer listContainer, Component component, int i, long l) {
                QOption option = itemProvider.getItem(i);
                option.setSelect(!option.isSelect());
                itemProvider.notifyDataChanged();
            }
        });
        myHandler = new MyEventHandler(EventRunner.getMainEventRunner());
        title = (Text) findComponentById(ResourceTable.Id_title);
        button.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {


                new Thread(new Runnable(){

                    @Override
                    public void run() {
                        AnswerResult answerResult = new AnswerResult();
                        answerResult.setAnswerResult(true);
                        answerResult.setqId(question.getqId());
                        answerResult.setAnswerDeviceId("watch001");
                        answerResult.setAnswerUsedTime(time);
                         myAnswer = "";
                        correctAnswer = "";
                        for(QOption myOption:question.getqOptions()){
                            if(myOption.isAnswer()!=myOption.isSelect()){
                                answerResult.setAnswerResult(false);

                            }
                            //统计个人的答案
                            if(myOption.isSelect()){
                                myAnswer =myAnswer+myOption.getNum();

                            }
                            //统计正确答案
                            if(myOption.isAnswer()){
                                correctAnswer =correctAnswer+myOption.getNum();
                            }
                        }

                        String res = JSON.toJSONString(answerResult);
                        mqttReceverClient.publish("answer",res);
                        myHandler.removeAllEvent();
                        getUITaskDispatcher().asyncDispatch(new Runnable() {
                            @Override
                            public void run() {
                                button.setClickable(false);
                                dlResult.setVisibility(Component.VISIBLE);
                                tCorrctAnswer.setText(correctAnswer);
                                tMyAnswer.setText(myAnswer);
                                button.setText("已提交");
                            }
                        });
                    }
                }).start();
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                mqttReceverClient = new MqttReceverClient(new MqttSimpleCallback() {

                    @Override
                    public void connectionLost() throws Exception {

                        int i = 0;
                        while (!mqttReceverClient.connectToBroker() && i++ < 5) {
                        }

                    }

                    @Override
                    public void publishArrived(String s, byte[] bytes, int i, boolean b) throws Exception {

                        question = JSON.parseObject(bytes, Question.class);

                        getUITaskDispatcher().asyncDispatch(new Runnable() {
                            @Override
                            public void run() {
                                button.setClickable(true);
                                button.setText("提交");
                                dlResult.setVisibility(Component.INVISIBLE);
                                tContent.setText(question.getqNum()+"."+question.getqStem());
                                tContent.setTruncationMode(Text.TruncationMode.AUTO_SCROLLING);
                                tContent.setAutoScrollingCount(-1);
                                tContent.startAutoScrolling();
                                myHandler.removeAllEvent();
                                itemProvider.clearData();
                                itemProvider.notifyDataChanged();
                                itemProvider.setmListInfo(question.getqOptions());
                                mListContainer.setItemProvider(itemProvider);
                                itemProvider.notifyDataChanged();
                                time=0;
                                 myHandler.sendEvent(0, 1000);//向事件队列发送延迟的空事件

                            }
                        });
                    }
                });
                mqttReceverClient.onStart();
            }
        }).start();
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }

    class MyEventHandler extends EventHandler {

        private MyEventHandler(EventRunner runner) {
            super(runner);
        }
        @Override
        public void processEvent(InnerEvent event) {
            super.processEvent(event);


                myHandler.sendEvent(0, 1000);//如果正在计时，继续发送事件
                time++;
                title.setText(getTimeStrin(time));

        }
    }
    String getTimeStrin(long t) {
        return String.format("%02d:%02d",  t / 60 % 60, t % 60);
    }
}
