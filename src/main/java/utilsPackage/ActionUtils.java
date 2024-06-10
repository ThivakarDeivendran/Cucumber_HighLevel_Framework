package utilsPackage;

public class ActionUtils {
	private static final Logger log = LoggerFactory.getLogger(ActionUtils.class);
	
	public static void dropDown(WebElement element, String value) {
		try {
			boolean isSelected = false;
			Select select = new Select(element);
			List<WebElement> allOptions = select.getOptions();
			for(WebElement each:allOptions) {
				if(each.getText().contains(value)) {
					select.selectByVisibleText(value);
					isSelected = true;
					System.out.println("Option with Text " + value +" is selected  ");
					break;
				}
			}
			if(!isSelected) {
				System.out.println("Option with text  "+value+"is not available to Select");
			}
		}catch(Exception e) {
			logger.error("error", e.getMessage());
		}
	}

}
