package com.chinasofti.smartclassroomtv.data;

import com.chinasofti.smartclassroomtv.demain.Question;

import java.io.Serializable;
import java.util.List;

public class ResultData implements Serializable {
    private List<Question> data = null;

    public ResultData(List<Question> data) {
        this.data = data;
    }

    public ResultData() {
    }

    public List<Question> getData() {
        return data;
    }

    public void setData(List<Question> data) {
        this.data = data;
    }
}
