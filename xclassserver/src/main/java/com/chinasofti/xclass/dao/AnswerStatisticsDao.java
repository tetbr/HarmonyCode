package com.chinasofti.xclass.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.chinasofti.xclass.demain.AnswerStatistics;

public interface AnswerStatisticsDao extends 
		JpaRepository<AnswerStatistics, Long>, 
		JpaSpecificationExecutor<AnswerStatistics>{

}
