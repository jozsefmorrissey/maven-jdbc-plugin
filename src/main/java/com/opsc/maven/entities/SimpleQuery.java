package com.opsc.maven.entities;

import java.util.Properties;

import com.opsc.maven.interfaces.Query;

import lombok.Getter;

@Getter
public class SimpleQuery implements Query {
	private Properties connectionProps = new Properties();
	private String connUrl;
	
	private String userName;    
	private String password;
	private String cmd;
	private String QId;

	public SimpleQuery(String userName, String password, String cmd,
			String dbUrl, String QId) {
		this.userName = userName;
		this.password = password;
		this.cmd = cmd;
		this.QId = QId;

		this.connectionProps.put("user", this.userName);
		this.connectionProps.put("password", this.password);
		this.connUrl = dbUrl;
	}
}
