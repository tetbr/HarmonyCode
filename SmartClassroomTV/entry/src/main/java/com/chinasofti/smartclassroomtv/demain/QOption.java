package com.chinasofti.smartclassroomtv.demain;


import java.io.Serializable;
import java.util.Objects;

/**
 * 示题 选项类
 */

public class QOption implements Comparable<QOption>, Serializable {
    private Integer id;
    private String num;
    private String content;//题干
    private boolean answer;
    private Question question;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }



    @Override
    public int compareTo(QOption qOption) {
        return num.compareTo(qOption.getNum());
    }
}
