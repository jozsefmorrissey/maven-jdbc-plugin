package com.opsc.maven.entities;

import java.util.Properties;

import com.opsc.maven.interfaces.Query;

import lombok.Getter;

@Getter
public class DerbyQuery implements Query {
	private Properties connectionProps = new Properties();
	private String connUrl;
	
	private String userName;    
	private String password;
	private String dbms;
	private String port;
	private String dbName;
	private String sid;
	private String qId;
	private String cmd;
	
	public DerbyQuery(String userName,
			String password,
			String dbms,
			String port,
			String dbName,
			String sid,
			String qId,
			String cmd) {
		this.userName = userName;
		this.password = password;
		this.dbms = dbms;
		this.port = port;
		this.dbName = dbName;
		this.sid = sid;
		this.qId = qId;
		this.cmd = cmd;
		
        this.connectionProps.put("user", this.userName);
        this.connectionProps.put("password", this.password);
        this.connUrl = "jdbc:" + this.dbms + ":" +
                this.dbName + ";create=true";
	}
}
