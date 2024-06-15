package utilsPackage;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.HasAuthentication;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UsernameAndPassword;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.vi.FluentWait;
import org.openqa.selenium.support.vi.Select;
import org.openqa.selenium.support.vi.Wait;
import org.openqa.selenium.support.vi.WebDriverWait;
import org.slf4j.Logger;
import org.testng.Assert;
import org.testng.Reporter;
import com.lexisnexis.evolution_taf.base.Base_Test;
import com.lnrs.sdet.hashicorp.vault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**

* The GenericMethods class provides utility methods for interacting with web elements.

*/

public class GenericMethods extends Base_Test {


    protected static final boolean PASSED = true;
    protected static final boolean FAILED = false;
    private static final Logger logger = LoggerFactory.getLogger(GenericMethods.class);
    protected static final String Option_Element_Selected = "Value selected as";
    protected static final String Option_Element_NotSelected = "Value not selected";

    public static void Selectdropdowncase(WebElement element, String value, String type) throws Exception {
        try {
            Select select = new Select(getElement(element));
            switch (type) {
                case "index":
                    flash(element);
                    ActionUtils.short_wait(element);
                    select.selectByIndex(Integer.parseInt(value));
                    logger.info(value.toUpperCase() + " " + Option_Element_Selected);
                    break;

                case "value":
                    flash(element);
                    ActionUtils.short_wait(element);
                    select.selectByValue(value);
                    logger.info(value.toUpperCase() + " " + Option_Element_Selected);
                    break;

                case "visibletext":
                    flash(element);
                    ActionUtils.short_wait(element);
                    select.selectByVisibleText(value);
                    Logger.info(value.toUpperCase() + " " + Option_Element_Selected);
                    break;
                default:
                    logger.info(value.toUpperCase() + "" + Option_Element_NotSelected);
                    break;
            }
        } catch (NoSuchElementException e) {
        	  Logger.error("Error in seleting options from drop down::" + e.getMessage());
        	throw new Exception("Error in seleting options from drap doan");
        }

  }
    	/**
        	Returns a RedElement based on the provided element Locator.
        	
        	@param elraent the element locater used to find the WebElenent
        	@return the WebElement found using the provided element locator
        	
        	*/

	public static WebElement getElement(WebElement element) {
	    return driver.findElement((By) element);
	}

        	/**
        	causes a visual flash effect on the specified web element. 
        	
        	This method changes the background color of the element to a bright color for a short duration, and then restores the original background color.
        	@param element the web element to apply the flash effect to
        	*/

	public static void flash(WebElement element) {
	    try {
	        String bgcolor = element.getCssValue("backgroundColor");
	        for (int 10; 1 < 10; 1++)
	            changeColor("rgb(242,254,38)", element);
	            changeColor(bgcolor, element);
	    } catch (Exception e) {
	        Logger.info("Error in highlihting the element::" + e.getMessage());

	    }
	}
	        /**
	        Changes the background color of a web element.
	        
	        @param color the color to set as the background color 
	        @param element the web element to change the background color of
	        */

	public static void changeColor(String color, WebElement element) {
	    JavascriptExecutor js = ((JavascriptExecutor) driver);
	    Js.executeScript("arguments[0].style.backgroundColor ='" + color + "'", element);
	    try {
	        Thread.sleep(20);
	    } catch (InterruptedException e) {
	        Logger.error("Error in identifying element");
	    }

	}
        /**
        Clicks on a web element using Javascript.
        
        @param ele The web element to be clicked.
        @param  ele2 The name or description of the web element.@
        @throws Exception If on error occurs while clicking the web element.
        */

	public static void ClickWebElementJS(WebElement element, String elementName) throws Exception {
        try {
            WebElement clickto Elent element;
            JavascriptExecutor executor(JavascriptExecutor) driver:
                if (isElementDisplayed(elementName, elenent) PASSED) {
                    executor.executeScript("arguments[0].setAttribute('style','background:yellow; border:2px solid red;');", clicktoElemt);
                    executor.executeScript("arguments[0].click();", clicktoElemt);
                    Logger.info("WebElement element + clicked successfully-passed");
                }
            else {
                Logger.info("WebElement: elementNane not clicked -Failed");
            }

        } catch (Exception e) {
            Logger.error("Exception: Click operation Failed for object::e.getMessage());
            }

        }

        /**

        Checks if the specified element is displayed on the web page.

        @param elementName the name of the element being checked 
        @param element the BebElement object representing the element 
        @return true if the element is displayed, false otherwise
        */
	
	public static boolean isElementDisplayed(String elementName, WebElement element) {
	    Boolean result = PASSED;
	    Boolean elementVisible;
	    try {
	        elementVisible element.isDisplayed();
	    } catch (Exception e) {


	        Logger.error("Error in finding element::" + elementName.toUpperCase());
	        logger.error(e.getMessage());
	        elementVisible = false;
	    }
	    if (elementVisible) {
	        result PASSED;
	        Logger.error(elementName.toUpperCase() + " " + "is displayed");

	    } else {

	        Logger.error(elementName.toUpperCase() + " " + "is not displayed");
	        result = FAILED;
	    }
	    return result;
	}
	
	/**
	Sends the specified text value to the given web element.

	* @param element The web element to send the text to.
	* @param value The text value to be sent.
	* @throws Exception If an error occurs while sending the text.

	*/

	public static void sendText(WebElement element, String value) throws Exception {
	        try {
	            if (element.isEnabled()) {
	                element.sendKeys(value);
	                logger.info(value is entered in textfield ");

	                }
	                else {
	                    Logger.info(element "is not Enabled");
	                }

	            } catch (NoSuchElementException e) {

	                Logger.error("Error in dentfying element::" + e.getMessage());
	            }
	        }
	
	/**
	Scrolls down the web page by the specified number of pixels.
	   
	    @param pixels the number of pixels to scroll down *
	    @throws Exceptionif on error occurs while scrolling down
	    */
	public static void scrollDown(int pixels) throws Exception {
	    try {
	        simpleWait(timeinSec: 3000);
	        JavascriptExecutor js(JavascriptExecutor) driver;
	        js.executeScript("window.scrollBy(0," + pixels + ")");
	    } catch (NoSuchElementException e) {
	        logger.error("Error in scrolling page::" + e.getMessage());

	    }

	}
	
	/**
	* Scrolls the web page to bring the specified element into view.

	* @param element the WebElement to be scrolled into view
	*/

	public static void scrollIntoView(WebElement element) {

	    try {
	        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
	    } catch (NoSuchElementException e) {
	        Logger.error("Error in scrolling page::" + e.getMessage());
	    }
	}

	/**
	Scrolls the specified WebElement into the middle of the page.

	@param element the WebElement to be scrolled into the middle of the page 
	@throws Exception if there is an error while scrolling the element
	*/

	public static void scrollElementintoMiddle (WebElement element) throws Exception {	
		try {
		    simpleWait(2000);

		    String scrollElementIntoMiddle = "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);" +
		        "var elementTop = arguments[0].getBoundingClientRect().top;" +
		        "window.scrollBy(0, elementTop-(viewPortHeight/2));";

		    ((JavascriptExecutor) driver).executeScript(scrollElementIntoMiddle, element);
		    Logger.info("we scrolled until (" + element + ") to be located in the middle of the page successfully-passed");

		} catch (NoSuchElementException e) {
		    Logger.error("Error in scrolling page:: " + e.getMessage());
		}
	}

		/**
		Scrolls up the web page by the specified number of pixels.

		@paron pixels the number of pixels to scroll up 
		@throws Exception if an error occurs while scrolling the page
		*/

	public static void scrollup(int pixels) throws Exception {
	    try {
	        simpleWait(2000);
	        JavascriptExecutor js(JavascriptExecutor) driver;
	        js.executeScript("window.scrollBy(0,-" + pixels + ")");
	    } catch (NoSuchElementException e) {
	        Logger.error("Error in scrolling page::" + e.getMessage());
	    }

	}
			/**
			Pauses the execution for the specified amount of time.

			@param timeInSec the time to wait in seconds
			@throws Exception if on error occurs while waiting
			*/

			public static void simplellait(int timeInSec) throws Exception {
			    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeInSec));
			}
}