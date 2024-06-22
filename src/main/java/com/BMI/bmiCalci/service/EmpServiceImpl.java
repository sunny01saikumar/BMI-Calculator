package com.BMI.bmiCalci.service;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.BMI.bmiCalci.pojo.BMICalci;
import com.BMI.bmiCalci.repo.EmpRepo;

@Service
public class EmpServiceImpl implements EmpService {
	
	String underWeight = "Balanced Diet:\n"
			+ "\n"
			+ "Consume a well-balanced diet that includes a variety of food groups, such as lean proteins, whole grains, fruits, vegetables, and healthy fats.\n"
			+ "Calorie Surplus:\n"
			+ "\n"
			+ "Aim for a calorie surplus by consuming more calories than your body burns. This can be achieved by increasing portion sizes and incorporating snacks between meals.\n"
			+ "Protein Intake:\n"
			+ "\n"
			+ "Focus on increasing your protein intake. Include sources of lean protein like poultry, fish, eggs, dairy, legumes, and nuts in your diet.\n"
			+ "Regular Meals:\n"
			+ "\n"
			+ "Eat regular, frequent meals throughout the day. Include snacks between meals to increase overall calorie intake.";
	
	String normal = "Balanced Diet:\n"
			+ "\n"
			+ "Consume a well-balanced diet that includes a variety of fruits, vegetables, whole grains, lean proteins, and healthy fats.\n"
			+ "Avoid excessive intake of processed foods, sugary snacks, and high-calorie beverages.\n"
			+ "Regular Physical Activity:\n"
			+ "\n"
			+ "Engage in regular physical activity, aiming for at least 150 minutes of moderate-intensity exercise per week.\n"
			+ "Include a mix of aerobic exercises, strength training, and flexibility exercises in your routine.\n"
			+ "Hydration:\n"
			+ "\n"
			+ "Stay hydrated by drinking an adequate amount of water throughout the day.\n"
			+ "Sometimes, our bodies may confuse thirst with hunger, so ensure you are well-hydrated.";
	
	String overWeight = "Consult with a Healthcare Professional:\n"
			+ "\n"
			+ "Before making significant changes, it's advisable to consult with a healthcare professional or a registered dietitian to ensure a personalized approach based on your individual health needs.\n"
			+ "Balanced Diet:\n"
			+ "\n"
			+ "Focus on a well-balanced diet that includes a variety of nutrient-dense foods such as fruits, vegetables, whole grains, lean proteins, and healthy fats.\n"
			+ "Control portion sizes to avoid overeating, and be mindful of calorie intake.\n"
			+ "Hydration:\n"
			+ "\n"
			+ "Drink plenty of water throughout the day. Sometimes, our bodies can mistake thirst for hunger.";

	@Autowired
	private EmpRepo empRepo;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private EmpServiceImpl(JavaMailSender javaMailSender) {
		this.javaMailSender=javaMailSender;
	}
	
	public void sendMail(String empName, int age, double height, double weight, double bmi, String mali) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        try {
        	
            helper.setTo(mali);
            helper.setSubject("Your BMI Checkup Results");

            String htmlContent = "<html><body>"
                    + "<p>Dear " + empName + "</p>"
                    + "<p>I hope this email finds you well. Thank you for participating in our recent BMI checkup.</p>"
                    + "<p>We are pleased to provide you with the results of your BMI checkup based on the information you provided regarding your height and weight.</p>"
                    + "<p>Your Body Mass Index (BMI) is a valuable indicator of your overall health and helps us understand more about your fitness level.</p>"
                    + "<p><strong>BMI Checkup Results:</strong>,</p>"
                    + "<p><strong>Height : " + height + "</strong>,</p>"
                    + "<p><strong>Weight : " + weight + "</strong>,</p>"
                    + "<p><strong>BMI : " + bmi + "</strong></p>"
					+ "<p><strong>BMI below 18.5: Underweight\n" +
					"\n" +
					"BMI between 18.5 and 24.9: Normal weight\n" +
					"\n" +
					"BMI between 25.0 and 29.9: Overweight\n" +
					"\n" +
					"BMI of 30 or higher: Obese<Strong><p>"
                    + "</body></html>";

            helper.setText(htmlContent, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


	@Override
	public BMICalci create(BMICalci emp_Pojo) {
		
		double heightInMeters = emp_Pojo.getHeight() / 100.0;
		double weight = emp_Pojo.getWeight();

		if (heightInMeters > 0) {
		    double bmi = weight / (heightInMeters * heightInMeters);
		    emp_Pojo.setBmi(bmi);
		} else {
		    emp_Pojo.setBmi(0.0);
		}
		
		EmpServiceImpl impl = new EmpServiceImpl(javaMailSender);
			impl.sendMail(emp_Pojo.getEmpName(), emp_Pojo.getAge(), emp_Pojo.getHeight(), emp_Pojo.getWeight(), emp_Pojo.getBmi(), emp_Pojo.getMail());
		

		return empRepo.save(emp_Pojo);
	}

	@Override
	public List<BMICalci> get() {
		return empRepo.findAll();
	}

	public BMICalci findEmpByBmi(double bmi) {
		return empRepo.findByBmi(bmi);
	}


}
