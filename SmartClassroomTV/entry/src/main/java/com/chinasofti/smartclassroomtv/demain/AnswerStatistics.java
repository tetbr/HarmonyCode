package com.chinasofti.smartclassroomtv.demain;

import java.util.Date;

/**
 *  答题统计
 * 	接收来至TV的统计数据
 */

public class AnswerStatistics {

    private Long id;  //答题记录id
    private Long qId;  //题ID
    private Date answerDate;//答题日期
    private String deviceId;
    private int answerSum;//答题人数
    private int correctSum;//正确人数

	public AnswerStatistics() {
	}

	public AnswerStatistics(Long qId, String deviceId, int answerSum, int correctSum) {
		this.qId = qId;
		this.deviceId = deviceId;
		this.answerSum = answerSum;
		this.correctSum = correctSum;
	}

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

	public Date getAnswerDate() {
		return answerDate;
	}

	public void setAnswerDate(Date answerDate) {
		this.answerDate = answerDate;
	}

	public int getAnswerSum() {
		return answerSum;
	}

	public void setAnswerSum(int answerSum) {
		this.answerSum = answerSum;
	}

	public int getCorrectSum() {
		return correctSum;
	}

	public void setCorrectSum(int correctSum) {
		this.correctSum = correctSum;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	@Override
	public String toString() {
		return "AnswerStatistics [id=" + id + ", qId=" + qId + ", answerDate=" + answerDate + ", deviceId=" + deviceId
				+ ", answerSum=" + answerSum + ", correctSum=" + correctSum + "]";
	}

	
   

}
