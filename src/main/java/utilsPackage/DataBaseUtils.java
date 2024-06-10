package utilsPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.thirdparty.publicsuffix.PublicSuffixPatterns;
import io.cucumber.plugin.event.Result;

import java.util.List;




public class DataBaseUtils {
	
 private static final Logger logger =  LoggerFactory.getLogger(DataBaseUtils.class);
 
 public static Connection connect_to_testData_db() {
	 logger.info("testing the ");
	 String  connectionString ="jdbc:sqlserver://ALAWDSQL301.risk.regn.net:50113;"
	 +"databaseName=AccurintTestData;"
	 +"user=accurint_automation;"
	 +"password=;"
	 +"encrypt=false;"
	 +"trustServerCertificate=true;";
	 Connection conn = null;
	 try {
		 conn = DriverManager.getConnection(connectionString);
		 
	 }catch(SQLException e) {
		 e.printStackTrace();
	 }
	 return conn;
 }
 
 public static ResultSet run_query(Connection conn, String query) {
	 ResultSet  resultSet = null;
	try {
		Statement statement = conn.createStatement();
		resultSet = statement.executeQuery(query);
	}catch(Exception e) {e.printStackTrace();}
	return resultSet;
 }
 
 public static List<Map<String, Obejct>> extract_data(ResultSet resultSet){
	 List<Map<String, Object>> rows = new ArrayList<>();
	 try {
		 ResultSetMetaData metaData = resultSet.getMetaData();
		 int columnCount = metaData.getColumnCount();
		 while(resultSet.next()) {
			 Map<String, Object>  row = new LinkedHashMap<>();
			 for(int i=1;i<= columnCount;i++) {
				 String columnName = metaData.getCatalogName(i);
				 Object columnValue = resultSet.getObject(i);
				 row.put(columnName, columnValue);
			 }
			 rows.add(row);
		 }
		 
				 }catch(SQLException e) {e.printStackTrace();} return rows;
 }
 public static String get_Multiple_test_data_from_dataBase(String columnName, String TestCaseName) throws Exception{
	 connection conn = connect_to_testData_db();
	 ResultSet db_result = run_query(conn,"select * from table_Name");
	 List<Map<String, Object>>  extracted_Date = extract_data(db_result);
	 Object columnValue = null;
	 
	 String testcaseIDToMatch = TestCaseName // replace with your test case ID
	 Map<String, Object> matchingRow = null;
	 
	 for(Map<String, Object> row : extracted_data) {
		 try {
			 if(row.containsKey("TEST_CASE_ID") && row.get("TEST_CASE_ID").toString().equals(testcaseIDToMatch)) {
				 matchingRow = row;
				 break;
			 }
			 
		 }catch(NullPointerException e) {
			 logger.error("Requested column TEST_CASE_ID is not available in the database.please check if the data is added", e);
			 continue;
		 }
	 }
	  if(matchingRow == null) {
		  throw new RuntimeException("No row found with TEST_CASE_ID: " + testcaseIDToMatch)
	  } 
	  for(Map.Entry<Sting ,Object> column : matchingRow.entrySet()) {
		  String columnName = column.getKey();
		  try {
			  if(columnName.equals(ColumnName)) {
				  columnValue = column.getValue();
			  }
		  }catch(Exception e) {
			  logger.error("Requested column" +columnValue +"is not available in the database. Please check if the data is added", e);
		  }  
	  }
	  String c = columnValue.toString();
	  return c;
	 
  }
 public static String get_test_data_from_database(String c, String TestCaseName) throws Exception {
	 Connection conn = connect_to_testData_db();
	 ResultSet db_result =run_query(conn,"select * from tablename where TEST_CASE_ID = '"+TestCaseName+"'");
	 List<Map<String, Object>> extracted_Data = extract_data(db_result);
	 Object columnValue = null;
	 
	 if(extracted_Data.isEmpty()){
		 throw new RuntimeException("No data Found");
}
	 
	 Map<String, Object>  row = extracted_Data.get(0);
	 if(!row.containsKey("TEST_CASE_ID") || row.get("TEST_CASE_ID") ==  null) {
		 throw new RunTimeException("TEST_CASE_ID is not present or null");
	 }
	 if(!row.containsKey(c)) {
		 throw new RunTimeException("Column  : " + c +  " is not present");
	 }
	 columnValue = row.get(c);
	 String ColumnValue = columnValue.toString();
	 System.out.println("Data from DB::: " + ColumnValue);
	 return ColumnValue;
 }
 
 
 public static Map<String, Object> get_test_data_from_database(String TestCaseName) throws Exception{
	 Connection conn = connect_to_testData_db();
	 ResultSet db_result = run_query(conn,"select * from tablename where TEST_CASE_ID = '"+TestCaseName+"'");
	 List<Map<String, Object>> extracted_Data = extract_data(db_result);
	 
	 if(extracted_Data.isEmpty()) {
		 throw new RunTimeException("No data found");
	 }
	 
	 Map<String, Object> row = extracted_Data.get(0);
	 if(!row.containsKey("TEST_CASE_ID") || row.get("TEST_CASE_ID") ==  null) {
		 throw new RunTimeException("TEST_CASE_ID is not present or null");
	 }
	 Map<String, Object> nonEmptyValues = new HashMap<>();
	  for(Map.Entry<String, Object> entry : row.entrySet()) {
		  if(entry.getValue() != null && !entry.getValue().toString().isEmpty()) {
			  nonEmptyValues.put(entry.getKey(), entry.getValue());
		  }
	  }
	  System.out.println("Data from BD::: " +nonEmptyValues );
	  return nonEmptyValues;
 }
 
 public static void main(String[] args) {
	String data = get_test_data_from_database("FirstName", "Business_001");
	System.out.println(data);
}
 
 public static String get_data_from_db(String  testcaseID, String columnName) throws SQLException
 {
	 String extracted_data = null;
	 Connection conn = connect_to_testData_db();
	 ResultSet db_result = run_query(conn, "slect"+columnName+" from People_Category WHERE TEST_CASE_ID = '"+ testcaseID+"'");
	 //make sure the Result set is not null and has a result
	 if(db_result != null && db_result.next()) {
		 extracted_data = db_result.getString(1);
	 }
	 return extracted_data;
  } 
 
 

}
