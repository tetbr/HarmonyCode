package com.chinasofti.smartclassroomtv.demain;

import java.io.Serializable;
import java.util.*;


public class Question implements Comparable<Question>, Serializable {
    private Long qId;

    private Integer qNum;

    private String qStem;
    private List<QOption> qOptions =new ArrayList<>();

    public List<QOption> getqOptions() {
        Collections.sort(qOptions);
        return qOptions;
    }

    public void setqOptions(List<QOption> qOptions) {
        this.qOptions = qOptions;
    }

    public Long getqId() {
        return qId;
    }

    public void setqId(Long qId) {
        this.qId = qId;
    }

    public Integer getqNum() {
        return qNum;
    }

    public void setqNum(Integer qNum) {
        this.qNum = qNum;
    }

    public String getqStem() {
        return qStem;
    }

    public void setqStem(String qStem) {
        this.qStem = qStem;
    }




    @Override
    public String toString() {
        return "Question{" +
                "qId=" + qId +
                ", qNum=" + qNum +
                ", qStem='" + qStem + '\'' +
                ", qOptions=" + qOptions +
                '}';
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int compareTo(Question question) {
        return this.qNum.compareTo(question.getqNum());
    }
}
