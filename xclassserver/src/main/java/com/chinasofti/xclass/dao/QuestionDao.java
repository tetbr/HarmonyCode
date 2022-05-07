package com.chinasofti.xclass.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.chinasofti.xclass.demain.Question;

public interface QuestionDao extends 
		JpaRepository<Question, Long>, 
		JpaSpecificationExecutor<Question>{

}
