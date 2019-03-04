package com.opsc.maven.plugin;

import java.io.BufferedWriter;
import java.io.File;
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

@Mojo( name = "DbToJson")
public class DbToJson extends AbstractMojo {	
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
	
	public static void main(String...args) throws IOException {
		File f = new File(new DbToJson().getFileLocation());
		f.mkdirs();
		f.createNewFile();
	}
	
	public void execute() throws MojoExecutionException
    {
		File f = new File(new DbToJson().getFileLocation());
		f.mkdirs();
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(getFileLocation()))) {
			Query query = QueryToTable.getQuery(userName, password, dbms, port, serverOdbName, sid, qId, cmd, dbUrl);
			System.out.println(query.getConnectionProps().getProperty("user"));
			System.out.println("password:" + query.getConnectionProps().getProperty("password"));
			Table table = QueryToTable.runQuery(query);
	        writer.write(table.toJson());
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
	
	private String getFileLocation() {
		if (filename == null) {
			return "./src/main/resources/opsc/dbJson/" + qId + ".json";
		}
		return filename;
	}
    
}
