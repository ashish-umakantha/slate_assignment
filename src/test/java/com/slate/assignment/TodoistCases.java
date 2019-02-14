package com.slate.assignment;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.slate.apiTasks.ApiTasks;
import com.slate.base.InitDriver;
import com.slate.page.Homepage;
import com.slate.page.Login;
import com.slate.page.ProjectDetail;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class TodoistCases extends InitDriver {
  
	/**
     * Test case to create project via api and verify in mobile
     */
	@Test(priority=1)
	public void createProjectCase() throws Exception {
		AppiumDriver<MobileElement> driver = getDriver();
		ApiTasks.createProjectViaApi(); //Creation of unique and random test project
		Login login = onboarding.navigateToLoginWithEmail(driver);
		Homepage home = login.loginWithEmail(driver);
		ProjectDetail project = home.navigateToProjectTab(driver);
		Assert.assertTrue(project.verifyProject(driver));	
	}
	
}