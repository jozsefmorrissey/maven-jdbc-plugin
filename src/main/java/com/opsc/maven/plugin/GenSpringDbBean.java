package com.opsc.maven.plugin;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import com.opsc.maven.Service.QueryToTable;
import com.opsc.maven.entities.Table;
import com.opsc.maven.interfaces.Query;

@Mojo( name = "GenSpringDbBean")
public class GenSpringDbBean extends AbstractMojo {	
	@Parameter
	private String userName;    
	@Parameter
	private String password;
	@Parameter
	private String dbms;
	@Parameter
	private String port;
	@Parameter
	private String serverOdbName;
	@Parameter
	private String sid;
	@Parameter
	private String qId;
	@Parameter
	private String cmd;
	@Parameter
	private String filename;
	@Parameter
	private String dbUrl;
	
	
	
	public void execute() throws MojoExecutionException
    {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
			Query query = QueryToTable.getQuery(userName, password, dbms, port, serverOdbName, sid, qId, cmd, dbUrl);
			Table table = QueryToTable.runQuery(query);
	        writer.write(table.toJson());
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
    
}
