package utilsPackage;

public class AllureUtils {
	
	public void runAllureCommand(String command) throws Exception{
		ProcessBuilder processBuilder;
		if(System.getProperty("os.name").toLowerCase().contains("win")) {
			processBuilder = new ProcessBuilder("cmd.exe","/c",command);
		} else {
			processBuilder = new ProcessBuilder("/bin/bash","-c",command);
		}
		Process process = processBuilder.start();
		process.waitFor();
	}
	
	
	public void runAllureReportCommand(String command) throws Exception{
		ProcessBuilder processBuilder;
		if(System.getProperty("os.name").toLowerCase().contains("win")) {
			processBuilder = new ProcessBuilder("cmd.exe","/c",command);
		}else {
			processBuilder = new ProcessBuilder("/bin/bash","-c",command);
		}
		Process process= processBuilder.start();
	}

}
