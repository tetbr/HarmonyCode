package com.chinasofti.xclass.demain;

import java.io.Serializable;
import java.util.List;

public class ResultData implements Serializable {

	private List<Question> data = null;

	
	public ResultData() {
		super();
	}

	public ResultData(List<Question> data) {
		super();
		this.data = data;
	}

	public List<Question> getData() {
		return data;
	}

	public void setData(List<Question> data) {
		this.data = data;
	}

}
