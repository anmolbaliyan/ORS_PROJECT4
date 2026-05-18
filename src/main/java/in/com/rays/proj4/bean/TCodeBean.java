package in.com.rays.proj4.bean;

public class TCodeBean extends BaseBean {

	private String language;
	private String codeSnippet;
	private String executionTime;
	private String outputStatus;

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCodeSnippet() {
		return codeSnippet;
	}

	public void setCodeSnippet(String codeSnippet) {
		this.codeSnippet = codeSnippet;
	}

	public String getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(String executionTime) {
		this.executionTime = executionTime;
	}

	public String getOutputStatus() {
		return outputStatus;
	}

	public void setOutputStatus(String outputStatus) {
		this.outputStatus = outputStatus;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}

}
