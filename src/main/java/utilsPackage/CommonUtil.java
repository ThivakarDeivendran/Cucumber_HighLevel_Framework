package utilsPackage;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.io.FileReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.nio.ch.Secrets;



public class CommonUtil {
	private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);
	
	/**
	Retrives the username based on the environment and skin
	
	@param environment The environment ("QA", "STAGING","PRODUCTION")
	@param prop The properties object containing the vault Secrets.
	@param skin The Skin ("AML","RM")
	@param The username corresponding to the environment and skin. 
	@param RuntimeException IF no username is found for the given environment and skin.
	*/
	
	public static String getUserName(String environment, Properties prop, String skin) {
		switch(skin) {
		case "AML" :
			switch(environment) {
			case "QA":
				return getVaultsecret("QA_AML_USERNAME",prop);
			case "STAGING":
				return getVaultsecret("STAGING_AML_USERNAME",prop);
			default:
				throw new RuntimeException("No user was found for given environment :::::"+environment+" please check the userName entered");
			}
		case "RM":
			switch(environment) {
			case "QA":
				return getVaultsecret("QA_RM_USERNAME",prop);
			case "STAGING":
				return getVaultsecret("STAGING_RM_USERNAME",prop);
			default:
				throw new RuntimeException("No user was found for given environment :::::"+environment+" please check the userName entered");
			}
		default:
			throw new RuntimeException("no SKIN was given in maven command  ? " );
		}
	}
	
	/**
	Retrives the password based on the environment, skin and properties
	
	@param environment The environment ("QA", "STAGING","PRODUCTION")
	@param prop The properties object containing the necessary information
	@param skin The Skin ("AML","RM")
	@param The password associated with the given environment,properties and skin. 
	@param RuntimeException IF no password is found for the given userID.
	*/
	
	public static String getPassword(String environment, Properties prop, String skin) {
		switch(skin) {
		case "AML" :
			switch(environment) {
			case "QA":
				return getVaultsecret("QA_AML_PASSWORD",prop);
			case "STAGING":
				return getVaultsecret("STAGING_AML_PASSWORD",prop);
			default:
				throw new RuntimeException("No password found for user ID, Please check userID");
			}
		case "RM":
			switch(environment) {
			case "QA":
				return getVaultsecret("QA_RM_PASSWORD",prop);
			case "STAGING":
				return getVaultsecret("STAGING_RM_PASSWORD",prop);
			default:
				throw new RuntimeException("No password found for user ID, Please check userID");
			}
		default:
			throw new RuntimeException("no SKIN was given in maven command  ? " );
		}
	}
	
	/**
	Retrives the URL based on the environment, skin and properties
	
	@param environment The environment ("QA", "STAGING","PRODUCTION")
	@param prop The properties object containing the URL information
	@param skin The Skin ("AML","RM")
	@param The URL associated with the given environment,properties and skin. 
	@param RuntimeException IF no URL is found for the given environement and skin.
	*/
	
	public static String getUrl(String environment, Properties prop, String skin) {
		switch(skin) {
		case "AML" :
			switch(environment) {
			case "QA":
				return getVaultsecret("QA_AML_URL",prop);
			case "STAGING":
				return getVaultsecret("STAGING_AML_URL",prop);
			default:
				throw new RuntimeException("No password found for user ID, Please check userID");
			}
		case "RM":
			switch(environment) {
			case "QA":
				return getVaultsecret("QA_RM_URL",prop);
			case "STAGING":
				return getVaultsecret("STAGING_RM_URL",prop);
			default:
				throw new RuntimeException("No password found for user ID, Please check userID");
			}
		default:
			throw new RuntimeException("no SKIN was given in maven command  ? " );
		}
	}
	/**
	Retrives a secret value froma  vault based on the provided secret name and properties.
	 
	@param secret_name the naem of thesecret to retrieve.
	@param The URL associated with the given environment,properties and skin. 
	@return The secret value retrieved from the vault.
	*/
	
	public static String getVaultsecret(String secret_name, Properties prop) {
		String vault_token=System.getProperty("VAULT_TOKEN");
		if(vault_token.isEmpty()) {
			vault_token = prop.getProperty("VAULT_TOKEN");
		}
		String secret_value="";
		String vault_host = prop.getProperty("VAULT_HOST");
		String vault_namespace= prop.getProperty("VAULT_NAMESPACE");
		vault u = new vault();
		try {
			secret_value=u.get_data_from_vault(secret_name,vault_token,vault_host,vault_namespace);
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("problem while fetching secret from vault", e);
		}		
	return 	secret_value;
	}
	
	public static void getJsonLocators(String testcaseID, String search_name, String jsonName) throws Exception{
		try {
			common_search_page common = new Common_search_page();
			SharedConstants shared = new SharedConstants();
			DataBaseUtils db = new DataBaseUtils();
			
			Map<String, Object> testData = db.get_test_data_from_database(testcaseID);
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(new FileReader("JSON FILE LOCATION"));
			JSONObject jsonObject =(JSONObject)obj;
			JSONArray person = (JSONArray) jsonObject.get(search_name);
			Iterator<Object> iterator = person.iterator();
			while(iterator.hasNext()) {
				Object str = iterator.next();
				System.out.println("From json:::  " + str);
				String jsonstr = str.toString();
				if(testData.containsKey(jsonstr)) {
					String value =testData.get(jsonstr).toString();
					switch(jsonStr) {
					case "LEX_ID":
						common.inpt_lexID(value);
						shared.setSharedVariable("LEX_ID",value);
						break;
					case "FirstName":
						common.inpt_firstName(value);
						shared.setSharedVariable("FIRSTNAME",value);
						break;
					case "LastName":
						common.inpt_lastName(value);
						shared.setSharedVariable("LASTNAME",value);
						break;
					case "StreetAddress":
						common.inpt_streetAddress(value);
						shared.setSharedVariable("STREET",value);
						break;
					case "city":
						common.inpt_city(value);
						shared.setSharedVariable("CITY",value);
						break;
					case "State":
						common.dropdown(value);
						shared.setSharedVariable("STREET",value);
						break;
					case "Zip_Code":
						common.inpt_zipcode(value);
						shared.setSharedVariable("ZIP",value);
						break;
					case "Phone_number":
						common.inpt_phone.sendKeys(value);
						shared.setSharedVariable("PHONE_NUMBER",value);
						break;
					default:
						break;
					}
				}
			}
			common.search_Btn.click();
			
		}catch(Exception e) {
			logger.error("Error in populating search fields");
			throw new Exception("Error in populsting search fields");
		}
	}
}
