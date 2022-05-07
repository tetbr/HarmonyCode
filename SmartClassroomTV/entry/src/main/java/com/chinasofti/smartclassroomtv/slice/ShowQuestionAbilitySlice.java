package com.chinasofti.smartclassroomtv.slice;

import com.alibaba.fastjson.JSON;
import com.chinasofti.smartclassroomtv.ResourceTable;
import com.chinasofti.smartclassroomtv.data.ShowOptionProvider;
import com.chinasofti.smartclassroomtv.demain.AnswerResult;
import com.chinasofti.smartclassroomtv.demain.Question;
import com.chinasofti.smartclassroomtv.utils.MqttReceverClient;
import com.ibm.mqtt.MqttSimpleCallback;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Button;
import ohos.agp.components.ListContainer;
import ohos.agp.components.Text;
import ohos.agp.window.dialog.ToastDialog;
import ohos.eventhandler.EventHandler;
import ohos.eventhandler.EventRunner;
import ohos.eventhandler.InnerEvent;

import java.util.ArrayList;
import java.util.List;

public class ShowQuestionAbilitySlice extends AbilitySlice {

    Question question;

    Button btnStart;
    Button btnStop;
    Button btnReport;
    Button btnExite;
    Text tContent;

    Text tTimer;
    long time = 0;

    MqttReceverClient mqttReceverClient;
    String[] topic = {"answer"};  //mqtt订阅主题

    private ListContainer mListContainer;
    ShowOptionProvider itemProvider;

    //保存学生提交答题结果
    public static List<AnswerResult> answers = new ArrayList<>();
    MyEventHandler handler;//用来做计时器

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);

        super.setUIContent(ResourceTable.Layout_show_question);

        //获取题的数据
        question = intent.getSerializableParam("question");
        if (question == null) {
            terminateAbility(); //退出Ability
            return;
        }

        startMqttServer();
        initSliceLayout();
        handler = new MyEventHandler(EventRunner.getMainEventRunner());
    }


    private void initSliceLayout() {

        mListContainer = (ListContainer) findComponentById(ResourceTable.Id_list_container);
        btnStart = (Button) findComponentById(ResourceTable.Id_btn_start);
        btnStop = (Button) findComponentById(ResourceTable.Id_btn_stop);
        btnReport = (Button) findComponentById(ResourceTable.Id_btn_report);
        btnExite = (Button) findComponentById(ResourceTable.Id_btn_exit);
        tContent = (Text) findComponentById(ResourceTable.Id_t_content);
        tTimer = (Text) findComponentById(ResourceTable.Id_t_timer);

        //                                              选项的集合，     上下文
        itemProvider = new ShowOptionProvider(question.getqOptions(), this);
        mListContainer.setItemProvider(itemProvider);


        btnStart.setClickedListener(component -> {
                    new ToastDialog(getAbility()).setContentText("答题已开始！").setDuration(2500).show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String res = JSON.toJSONString(question); //把对象转换为 json字符串
                            mqttReceverClient.publish("ti", res); //把题发布出去，主题：ti
                            answers.clear(); //把已经 接收到手表端提交的答题结果 清空，  因为从新开始了
                            handler.removeAllEvent(); //参考计时器  章节内容
                            mqttReceverClient.subscribeToTopic(topic); //这个主题用接收 手表提交答题结果
                            time = 0;
                            handler.sendEvent(1, 1000);//开始计时
                        }
                    }).start();
                }
        );
        btnStop.setClickedListener(component -> {
                    new ToastDialog(getAbility()).setContentText("答题已结束！").setDuration(2500).show();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            handler.removeAllEvent();
                            handler.sendEvent(1);
                            mqttReceverClient.unsubscribe(topic); //结束了，不再接收学生答题结题，不再订阅主题
                        }
                    }).start();
                }
        );
        btnReport.setClickedListener(component ->
                new Thread(new Runnable() {//在非UI线程中执行 耗时比较长或 有阻塞操作
                    @Override
                    public void run() {
                        int sum = answers.size();//接收多少条数据
                        int correctSum = 0;  //保存答题正确的人数
                        //统计 答题正确人数
                        for (AnswerResult answerResult : answers) {
                            if (answerResult.isAnswerResult()) {
                                correctSum++;
                            }
                        }

                        //跳转到统计结果 界面
                        Operation operation = new Intent.OperationBuilder()
                                .withAbilityName("com.chinasofti.smartclassroomtv.ReportAbility")
                                .withBundleName("com.chinasofti.smartclassroomtv").build();

                        Intent intent = new Intent();
                        intent.setParam("sum", sum);
                        intent.setParam("correctSum", correctSum);
                        intent.setParam("no", question.getqNum());
                        intent.setParam("qid", question.getqId());
                        intent.setOperation(operation);//设置操作动作
                        startAbility(intent);
                    }
                }).start()
        );
        btnExite.setClickedListener(listener -> terminateAbility());
        tContent.setText(question.getqNum() + ". " + question.getqStem());
    }


    private void startMqttServer() {
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

                    @Override  //当有新的数据到来会执行下面的方法  参数1：主题，参数2：内容，
                    public void publishArrived(String s, byte[] bytes, int i, boolean b) throws Exception {
                        //接收  手表端提交的答案
                        AnswerResult answerResult = JSON.parseObject(bytes, AnswerResult.class);
                        answers.add(answerResult);
                        getUITaskDispatcher().asyncDispatch(new Runnable() {
                            @Override
                            public void run() {
                                //通过 ToastDialog提交  谁提交答题结果
                                new ToastDialog(getAbility()).setContentText(answerResult.toString()).show();
                            }
                        });
                    }
                });
                mqttReceverClient.onStart();
            }
        }).start();
    }


    class MyEventHandler extends EventHandler {

        public MyEventHandler(EventRunner runner) throws IllegalArgumentException {
            super(runner);
        }

        @Override
        protected void processEvent(InnerEvent event) {
            super.processEvent(event);
            time++;
            tTimer.setText(String.format("%02d:%02d", time / 60, time % 60));
            handler.sendEvent(1, 1000);
        }
    }

}
