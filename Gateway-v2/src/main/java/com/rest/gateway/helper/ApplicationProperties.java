package com.rest.gateway.helper;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
public class ApplicationProperties {
	
	private String jksFileName;
	private String passCode;
	private String pathPattern;
	private String fabUrl;
	
	public String getJksFileName() {
		return jksFileName;
	}
	public void setJksFileName(String jksFileName) {
		this.jksFileName = jksFileName;
	}
	public String getPassCode() {
		return passCode;
	}
	public void setPassCode(String passCode) {
		this.passCode = passCode;
	}
	public String getPathPattern() {
		return pathPattern;
	}
	public void setPathPattern(String pathPattern) {
		this.pathPattern = pathPattern;
	}
	public String getFabUrl() {
		return fabUrl;
	}
	public void setFabUrl(String fabUrl) {
		this.fabUrl = fabUrl;
	}
	
	@Override
	public String toString() {
		return "ApplicationProperties [jksFileName=" + jksFileName + ", passCode=" + passCode + ", pathPattern="
				+ pathPattern + ", fabUrl=" + fabUrl + "]";
	}	
	

}
