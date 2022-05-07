package com.chinasofti.smartclassroomdemo1.data;


import java.io.Serializable;

/**
 * 示题 选项类
 */

public class QOption implements Comparable<QOption>, Serializable {
    private Integer id;

    private String num;
    private String content;//题干

    private  boolean select;//是否选择
    private boolean answer;
    private Question question;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "Option{" +
                "id=" + id +
                ", num='" + num + '\'' +
                ", content='" + content + '\'' +
                ", answer=" + answer +
                ", question=" + question +
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

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    @Override
    public int compareTo(QOption myOption) {
        return num.compareTo(myOption.getNum());
    }
}
