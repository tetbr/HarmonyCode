package com.chinasofti.xclass.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinasofti.xclass.dao.QuestionDao;
import com.chinasofti.xclass.demain.ResultData;

@RequestMapping("/question")
@Controller

public class QuestionController {

	@Autowired
	private QuestionDao questionDao;

    @RequestMapping("/findAll")
    @ResponseBody
    public Object findAll(){
    	ResultData result = new ResultData(questionDao.findAll());
        return result;
    }
    
    @RequestMapping("/findOne")
    @ResponseBody
    public Object findOne(Long id){
        return questionDao.findById(id);
    }


}
