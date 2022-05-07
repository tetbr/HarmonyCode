package com.chinasofti.xclass.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.chinasofti.xclass.demain.QOption;

public interface QOptionDao extends 
		JpaRepository<QOption, Long>,
		JpaSpecificationExecutor<QOption>{

}
