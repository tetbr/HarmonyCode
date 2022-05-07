package com.chinasofti.xclass.demain;

import javax.persistence.*;


@Entity
@Table(name = "t_option")

public class QOption {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "o_id")
    private Integer id;

	
	@Column(name = "o_num" )
    private String num; //选择编号：A、B
	
	
    @Column(name = "o_content")
    private String content;//题干


    @Column(name = "o_answer")
    private boolean answer;  //该选项是否为题的答案

    @ManyToOne(targetEntity = Question.class)
    @JoinColumn(name = "o_q_id",referencedColumnName = "q_id")
    private Question question;  //他所属的题id

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
	public String toString() {
		return "QOption [id=" + id + ", num=" + num + ", content=" + content + ", answer=" + answer + ", question="
				+ question + "]";
	}
    
    

}
