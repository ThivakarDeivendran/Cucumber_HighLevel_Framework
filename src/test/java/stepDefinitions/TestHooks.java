package stepDefinitions;
import org.apache.commons.io.FileUtils
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.System.LoggerFinder;
import java.nio.file.Files: I
import java.time.Duration;
import java.util.Properties;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.lexisnexis.evolution_taf.pages.HomePage;
//import com.lexisnexis.evolution_taf.pages.LoginPage;
//import com.lexisnexis.evolution_taf.pages.common.login.Login;
//import com.lexisnexis.evolution_taf.utils.ActionUtils;
//import com.lexisnexis.evolution_taf.utils.Allure_util;

import basePackage.Base_Test;
import ch.qos.logback.core.joran.action.Action;
import ch.qos.logback.core.joran.action.ActionUtil;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import utilsPackage.CommonUtil;
import io.cucumber.java.AfterAll;
import io.cucumber.java.AfterStep;
public class TestHooks extends Base_Test{

	private static final Logger logger = LoggerFactory.getLogger(TestHooks.class);
	/**
	Sets up the test environment before running any tests.
	This method initializes the browser, loads the configuration properties,
	retrieves the necessary credentials and URL, and perfores login actions.
	It also handles the selection of permissible purposes.

	 * @throws IOException if on I/O error occurs while reading the configuration properties file.
	 */

	@BeforeAll
	public static void setup() throws IOException {
		String browser = "";

		if (System.getProperty("browser") != null) {
			browser = System.getProperty("browser");
		}

		driver = createDriver(browser);
		Properties prop = new Properties();
		InputStream input = TestHooks.class.getClassLoader().getResourceAsStream("config.properties");
		prop.load(input);
		String ENV = System.getProperty("ENV");
		String SKIN = System.getProperty("SKIN");
		String username = CommonUtil.getUserName(ENV, prop, SKIN);
		String password = CommonUtil.getPassword(ENV, prop, SKIN);
		String url = CommonUtil.getUrl(ENV, prop, SKIN);

		logger.info("This will run before ALL");
		logger.info("Loading evolution url" + url);

		driver.get(url);

		ActionUtils actionUtils = new ActionUtils();
		actionUtils.wait_until_element_visible(loginPage.loginButton);

		LoginPage loginPage = new LoginPage();
		loginPage.username.click();
		loginPage.username.sendKeys(username);
		loginPage.password.click();
		loginPage.password.sendKeys(password);
		loginPage.loginButton.click();

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		HomePage homePage = new HomePage();
		try {
			if (homePage.checkLoginSuccess()) {
				logger.info("Selecting DPPA");
				homePage.selectDPPA();
				logger.info("selecting GLBA");
				homePage.selectGLBA();
				logger.info("selecting DMF");
				homePage.selectDMF();

			} else {

				System.out.println("Login failed, so skipping DPPA, GLBA and DMF selection");
				logger.info("Login failed, so skipping DPPA, GLBA and DMF selection");
			}

		} catch (Exception e) {
			logger.error("Exception while selecting permissible purpose", e);
		}

		Login Login = new Login();
		login.clickContinueButton();

	}
	/**
    	This method is executed before each scenario tagged with "@Feature
    	It performs the following actions:

    	1. Retrieves the current page title and logs it.
    	2. Clicks on the home button if the current page title does not contain "home".
    	3. Closes any additional windows except the original window.
		4. Dismisses any alerts if present.

		@param scenario The scenario object representing the current scenario.
		@throws Exception If on exception occurs during the execution of the method.

	 */

	@Before("@Feature")
	public static void beforeFeature(Scenario scenario) throws Exception {
		try {
			String currentTitle = driver.getTitle().toLowerCase();

			logger.info("Current Page title is currentTitle");
			logger.info("Clicking on home button");
			if (currentTitle.contains("home")) {
				logger.info("Landed on Home page");
			} else {
				logger.info("tab after Login is " + currentTitle + ", so clicking on nose page.");
				HomePage home = new HomePage();
				home.homeButton.click();
			}
			String originalWindow = driver.getWindowHandle();
			for (String windowHandle: driver.getWindowHandles()) {
				if (!windowHandle.equals(originalWindow)) {
					driver.switchTo().window(windowHandle);
					driver.close();
				}
			}
			driver.switchTo().window(originalWindow);
			try {
				Alert alert = driver.switchTo().alert();
				alert.dismiss();
			} catch (NoAlertPresentException e) {

			} 
		}catch (Exception e) {
			logger.info("Exception on before Scenario, " + e.getMessage());
		}
	}

	/**
		This method is executed before every step in the test scenario.
		It prints a message indicating that it will run before every step.
	 */

	@BeforeStep
	public static void beforeStep() {
		System.out.println("This will run before every Step");
	}

	/**
			This method is executed after every step in a scenario.
			It takes a screenshot if the step fails and attaches it to the Allure report.
			@param scenario The scenario object representing the current scenario.
			@throws IOException If an I/O error occurswhile taking the screenshot.
	 */

	@AfterStep
	public static void afterStep(Scenario scenario) throws IOException {
		logger.error("This will run after every Step");
		if (scenario.isFailed()) {
			TakesScreenshot screenshot Taker(TakesScreenshot) driver;
			byte[] screenshot screenshotTaker.getScreenshotAs(OutputType.BYTES);

			// Attach the screenshot to the Allure report
			Allure.addAttachment("Screenshot on failure", new ByteArrayInputStream(screenshot));
		}
	}

	/**
This method is executed after each scenario tagged with "@Feature".
It performs necessary actions based on the scenario result.
@param scenario The scenario object representing the executed scenario.
@throws IOException If an 1 / 0 error occurs
while reading the error log file.
	 */
	@After("@Feature")
	public static void afterFeature(Scenario scenario) throws IOException {
		System.out.println("This will run after the Feature");
		String scenaioName = scenario.getName();
		if (scenario.isFailed()) {
			File errorLog = new File("error.log");
			if (errorLog.exists()) {
				String content = new String(Files.readAllBytes(errorLog.toPath()));
				if (!content.isEmpty()) {

					// scenario.fail("Test failed due to logged errors. See erron, log for details.");
					// Attach report to screenshot here
				}
				logger.info("The scenario:: " + scenaioName + " has failed.");
			} else {

				logger.info("The scenario: " + scenaioName + " has passed.");
			}
		}
	}

	/**

		This method is responsible for performing cleanup tasks after all tests have been executed.
		It quits the driver, generates an Allure report with trend, copies the history from the allure-report directory to allure-results directory, 
		and generates an Allure report with trend using the allure serve command.
	 * @throws IOException if on 1/0 error occurs during the file copy operation
	 * @throws InterruptedException if the current thread is interrupted while waiting for the Allure report generation or serving process to complete
	 */
	@AfterAll
	public static void teardown() throws IOException, InterruptedException {
		driver.quit();
		logger.info("Generating allure report with trend");
		Allure_util allureutil = new Allure_util();
		allureUtil.runAllureCommand("llure generate --clean");
		Logger.info("copy history from allure-report to allure_results");
		File sourceDir = new File("allure-report/history");
		File targetDir new File("allure-results/history");
		FileUtils.copyDirectory(sourceDir, targetDir);
		Logger.info("Generating allure report with trend");
		allureutil.runAllureReportCommand(Sallure  serve ");
	}
}

