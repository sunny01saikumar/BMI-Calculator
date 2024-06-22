package com.BMI.bmiCalci.service;

import java.util.List;

import com.BMI.bmiCalci.pojo.BMICalci;

public interface EmpService {

	public BMICalci create(BMICalci emp_Pojo);
	
	public List<BMICalci> get();
	
}
