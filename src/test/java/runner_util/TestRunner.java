package runner_util;

import org.junit.runner.Runwith;
import io.cucumber.junit.Cucumber;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import 10. cucumber.testng.Cucumberoptions;

/**
This class is the Test Runner for executing Cucumber tests.
It specifies the Cucumber options such as feature files, step definitions, plugins, tags, and monochrome node.
It extends the AbstractTestNGCucumberTests class to integrate with TestNG framework.

*/

@Cucumberoptions(
	    features = "src\\test\\resources\\features",
	    glue = "",
	    plugin = {"pretty", "html:target/cucumber-reports/cucunter-pretty", "Json:target/cucumber-reports/CucumberTestReport.json",
	        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"},
	    tags = "@Feature",
	    monochrome = true)

public class TestRunner extends AbstractTestNGCucumberTests{}
	    
	