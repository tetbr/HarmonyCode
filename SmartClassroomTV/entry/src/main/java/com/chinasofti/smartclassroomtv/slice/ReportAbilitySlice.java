package com.chinasofti.smartclassroomtv.slice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chinasofti.smartclassroomtv.ResourceTable;
import com.chinasofti.smartclassroomtv.demain.AnswerStatistics;
import com.chinasofti.smartclassroomtv.utils.HttpClient;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;

import ohos.agp.components.Button;
import ohos.agp.components.DirectionalLayout;
import ohos.agp.components.DirectionalLayout.LayoutConfig;
import ohos.agp.components.Text;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.utils.Color;
import ohos.agp.utils.TextAlignment;

public class ReportAbilitySlice extends AbilitySlice {

    Text tQuestionNo;//题号  t_question_no
    Text tAnswerSum;//回答人数  t_answer_sum
    Text tCorrectSum;//正确人数 t_correct_sum
    Text tAccuracy;//正确率 t_accuracy

    Button btnBackup;
    Button btnSave;//btn_save

    int no;
    int sum;
    int correctSum;
    double accuracy;
    long qId;

    AnswerStatistics answerStatistics;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        setUIContent(ResourceTable.Layout_report);
        sum = intent.getIntParam("sum", 0);
        correctSum = intent.getIntParam("correctSum", 0);
        no = intent.getIntParam("no", -1);
        qId = intent.getLongParam("qid", -1);
        answerStatistics = new AnswerStatistics(qId, null, sum, correctSum);
        try {
            tQuestionNo = (Text) findComponentById(ResourceTable.Id_t_question_no);
            tAnswerSum = (Text) findComponentById(ResourceTable.Id_t_answer_sum);
            tCorrectSum = (Text) findComponentById(ResourceTable.Id_t_correct_sum);
            tAccuracy = (Text) findComponentById(ResourceTable.Id_t_accuracy);

            btnBackup = (Button) findComponentById(ResourceTable.Id_btn_backup);
            btnSave = (Button) findComponentById(ResourceTable.Id_btn_save);

            tQuestionNo.setText(no + "");
            tAnswerSum.setText(sum + "");
            tCorrectSum.setText(correctSum + "");
            if (sum == 0) {

                tAccuracy.setText("0.0%");
            } else {
                double c = correctSum * 100.0 / sum;
                tAccuracy.setText(String.format("%.1f%%", c));
            }

            btnBackup.setClickedListener(component -> terminateAbility());
            btnSave.setClickedListener(component -> {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //get方法
                        String res = HttpClient.doGet(HttpClient.ASURL +
                                "?qId=" + answerStatistics.getqId()
                                + "&deviceId=" + "tv1"
                                + "&answerSum=" + sum
                                +"&correctSum="+correctSum);
                        try {
                            AnswerStatistics answerStatistics = JSON.parseObject(res, AnswerStatistics.class);
                            if (answerStatistics.getId() != null) {//如果有id，保存成功
                                getUITaskDispatcher().asyncDispatch(new Runnable() {//获取UI线程，
                                    @Override
                                    public void run() {
                                        btnSave.setText("已上传"); //修改  按钮的名称
                                        btnSave.setClickable(false);// 禁止 再次提交，
                                    }
                                });
                            }
                        } catch (Exception e) {

                        }
                    }
                }).start();
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
