package basePackage;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Base_Test {
public static WebDriver driver;
	
	private static final Logger logger = LoggerFactory.getLogger(Base_Test.class);
	
	
	public String get_browser_Value() throws IOException{
		Properties prop = new Properties();
		InputStream input= getClass().getClassLoader().getResourceAsStream("config.properties");
		prop.load(input);
		return prop.getProperty("browser");
	}
	
	public static WebDriver createDriver(String browser) throws IOException{
		try {
			switch(browser) {
			case "chrome" :
				ChromeOptions option = new ChromeOptions();
				option.addArguments("--start-maximized");
				option.addArguments("--no-sandbox");
				option.addArguments("--headless");
				option.setAcceptInsecureCerts(true); //accept all SSL Ceritificates
				driver = new ChromeDriver(option);
				break;
			case "headless-chrome":
				ChromeOptions hoption = new ChromeOptions();
				hoption.addArguments("--start-maximized");
				hoption.addArguments("--no-sandbox");
				hoption.addArguments("--headless=new");
				option.setAcceptInsecureCerts(true); //accept all SSL Ceritificates
				driver = new ChromeDriver(option);
				break;
			case "firefox":
				FirefoxOptions foption = new FirefoxOptions();
				foption.addArguments("--start-maximized");
				foption.addArguments("--no-sandbox");
				foption.setAcceptInsecureCerts(true); //accept all SSL Ceritificates
				driver = new FirefoxDriver(foption);
				logger.info(" Fire fox driver created ");
				driver.manage().window().maximize();
				break;
			case "edge":
				EdgeOptions eoption = new EdgeOptions();
				eoption.addArguments("--start-maximized");
				eoption.addArguments("--no-sandbox");
				eoption.setAcceptInsecureCerts(true); //accept all SSL Ceritificates
				driver = new FirefoxDriver(eoption);
				logger.info(" Edge driver created ");
				break;
			case "safari":
				SafariOptions soption = new SafariOptions();
				soption.setAutomaticInspection(true);
				soption.setAutomaticProfiling(true);
				soption.setUseTechnologyPreview(true);
				driver = new SafariDriver(soption);
				break;
			default;
			   throw new IllegalArgumentException();
			}
		}catch(Exception e) {
			logger.error("Error occured while creating driver  :  " + e.getMessage());
		}
		return driver;
	}	


}
