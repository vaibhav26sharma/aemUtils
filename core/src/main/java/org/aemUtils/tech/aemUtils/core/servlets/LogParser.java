package org.aemUtils.tech.aemUtils.core.servlets;

public interface LogParser {

	public void parseLogger(String sourcePath, String destinationPath);

	public boolean ifErrors();

	public long totalErrors();

}
