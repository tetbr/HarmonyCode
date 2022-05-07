package com.chinasofti.smartclassroomtv.demain;

/**
 * 答题结果
 */

public class AnswerResult {


    private Long id;  //答题记录id
    private Long qId;  //题ID
    private Long answerUsedTime;//答题时间
    private String answerDeviceId;//答题设备
    private String answererName;//答题者姓名
    private boolean  answerResult;//用来描述答题结题是否正确


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getqId() {
        return qId;
    }

    public void setqId(Long qId) {
        this.qId = qId;
    }

    public Long getAnswerUsedTime() {
        return answerUsedTime;
    }

    public void setAnswerUsedTime(Long answerUsedTime) {
        this.answerUsedTime = answerUsedTime;
    }

    public String getAnswerDeviceId() {
        return answerDeviceId;
    }

    public void setAnswerDeviceId(String answerDeviceId) {
        this.answerDeviceId = answerDeviceId;
    }

    public String getAnswererName() {
        return answererName;
    }

    public void setAnswererName(String answererName) {
        this.answererName = answererName;
    }

    public boolean isAnswerResult() {
        return answerResult;
    }

    public void setAnswerResult(boolean answerResult) {
        this.answerResult = answerResult;
    }

    @Override
    public String toString() {
        return "AnswerStatistics{" +
                "id=" + id +
                ", qId=" + qId +
                ", answerUsedTime=" + answerUsedTime +
                ", answerDeviceId='" + answerDeviceId + '\'' +
                ", answererName='" + answererName + '\'' +
                ", answerResult=" + answerResult +
                '}';
    }


}
