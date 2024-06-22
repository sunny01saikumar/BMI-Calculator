package com.BMI.bmiCalci.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BMI.bmiCalci.pojo.BMICalci;

public interface EmpRepo extends JpaRepository<BMICalci, Integer>{

	public BMICalci findByBmi(double bmi);
}
