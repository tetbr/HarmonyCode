package com.chinasofti.xclass.demain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "t_question")
public class Question {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "q_id")
    private Long qId;  //表一个题 对应  id

    @Column(name = "q_num")
    private Integer qNum;  //题号  

    @Column(name = "q_stem")
    private String qStem; //题干

    //返回 重复包含
    @JsonIgnoreProperties({"question"})  //防止 重复包含：题中包含选项，  选项又包含题    题中包含选项
    @OneToMany(mappedBy = "question")
    private List<QOption> qOptions =new ArrayList<>();//当前这个题所拥有选项

    
    //添加setter\getter方法
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

	public List<QOption> getqOptions() {
		return qOptions;
	}

	public void setqOptions(List<QOption> qOptions) {
		this.qOptions = qOptions;
	}

	@Override
	public String toString() {
		return "Question [qId=" + qId + ", qNum=" + qNum + ", qStem=" + qStem + ", qOptions=" + qOptions + "]";
	}
    
    
	
    
    
}
