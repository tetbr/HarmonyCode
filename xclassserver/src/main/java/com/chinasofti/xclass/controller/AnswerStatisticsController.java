package com.chinasofti.xclass.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinasofti.xclass.dao.AnswerStatisticsDao;
import com.chinasofti.xclass.demain.AnswerStatistics;

@Controller
@RequestMapping("/as") // 路径设置

public class AnswerStatisticsController {
	
	@Autowired    //可以理解为创建对象  
	private AnswerStatisticsDao answerStatisticsDao;

	@RequestMapping("/findAll")
	@ResponseBody    //返回
	public Object findAll() {
		//方法由框架实现
		return answerStatisticsDao.findAll();
	}

	
    @RequestMapping("/findOne")
    @ResponseBody
    public Object findOne(Long id){
        return answerStatisticsDao.findById(id);
    }
    
    
    @RequestMapping("/save")
    @ResponseBody
    public Object save(AnswerStatistics answerStatistics){
    	answerStatistics.setAnswerDate(new Date()); //保存时插入保存时间
        return answerStatisticsDao.save(answerStatistics);//保存
    }
    
    @RequestMapping("/update")
    @ResponseBody
    public Object update(AnswerStatistics answerStatistics){
        return answerStatisticsDao.save(answerStatistics);
    }
    
}
