package com.chinasofti.xclass.demain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_answer_statistics")  //表名
public class AnswerStatistics {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "a_id")
	private Long id;//答题记录ID
	
	
	@Column(name = "q_id")
	private Long qId;  //对应题的ID
	
	@Column(name = "answerer_date")
	private Date answerDate;//提交日期
	
	@Column(name = "device_id")
	private String deviceId;//上传数据设备ID
	
	@Column(name = "answer_sum")
	private int answerSum ;//答题总人数
	
	@Column(name = "correct_sum")
	private int correctSum;//答对了人数

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

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
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

	@Override
	public String toString() {
		return "AnswerStatistics [id=" + id + ", qId=" + qId + ", answerDate=" + answerDate + ", deviceId=" + deviceId
				+ ", answerSum=" + answerSum + ", correctSum=" + correctSum + "]";
	}
	
	
	
	
	
}
