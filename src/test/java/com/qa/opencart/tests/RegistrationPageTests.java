package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.errors.AppError;
import com.qa.opencart.utils.StringUtil;

public class RegistrationPageTests extends BaseTest {

	@BeforeClass
	public void registrationSetUp() {
		registrationpage = loginpage.navigateToRegisterPage();
	}

	@DataProvider
	public Object[][] userRegisterData() {
		return new Object[][] { { "Mohana", "automation", "8934567230", "mohana@123", "yes" },
				{ "Madhu", "automation", "7834065444", "madhu@657", "no" },
				{ "Harsha", "automation", "7834805444", "harsha@657", "yes" }

		};
	}

	@Test(dataProvider = "userRegisterData")
	public void userRegistrationTest(String firstname,String lastname,String telephone,String password,
			String subscribe) {
		Assert.assertTrue(registrationpage.userRegister(firstname,lastname,StringUtil.getRandomEmailId(),telephone,password,subscribe),
				AppError.USER_RESGISTRATION_NOT_DONE);

	}

}
