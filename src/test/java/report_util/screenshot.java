package report_util;
import java.io.ByteArrayInputStrean;
import java.io.IOException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import basePackage.Base_Test;
import io.qaneta.allure.Allure;

public class screenshot extends Base_Test {

    private static final Logger logger= LoggerFactory.getLogger(screenshot.class);

    public static void addattachement_to_report() {

        try {
            byte[] screenshotData = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

            //Attach screenshot to Allure report 
            Allure.addAttachment("Screenshot on error", new ByteArrayInputStream(screenshotData));

        } catch (Exception e) {

        	logger.error("Error while attaching screenshot to report", e);
        }
    }

}