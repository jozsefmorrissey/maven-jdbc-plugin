package com.opsc.maven.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.opsc.maven.entities.DerbyQuery;
import com.opsc.maven.entities.MySqlQuery;
import com.opsc.maven.entities.OracleQuery;
import com.opsc.maven.entities.SimpleQuery;
import com.opsc.maven.entities.Table;
import com.opsc.maven.interfaces.Query;

public class QueryToTable {
	public static Table runQuery(Query query) throws SQLException {
        Connection conn = DriverManager.getConnection(query.getConnUrl(), query.getConnectionProps());
        System.out.println("Connected to database");
		conn.setAutoCommit(false);
        
		ResultSet res = conn.prepareStatement(query.getCmd()).executeQuery();
		Table table = new Table();
		int rowCount = 0;
        while (res.next()) {
        	int index = 1;
        	try {
        		System.out.println("rowCount: " + rowCount + " - " + index);
				while (res.getObject(index) != null) {
					String colName = res.getMetaData().getColumnName(index);
					String value = res.getObject(index).toString();
					index++;
					System.out.println("index: " + index);
					table.update(colName, rowCount, value);
	        	}
        	} catch (Exception e) {
				System.out.println(e);
        	}
			rowCount++;
        }

		return table;
	}
	
    public static Query getQuery(String userName,
			String password,
			String dbms,
			String port,
			String serverOdbName,
			String sid,
			String qId,
			String cmd,
			String dbUrl) throws SQLException {
    	System.out.println("sn: " + serverOdbName);

    	Properties connectionProps = new Properties();
        connectionProps.put("user", userName);
        connectionProps.put("password", password);
        Query query = null;
		System.out.println(dbUrl);
        
        if ("mysql".equals(dbms)) {
        	query = new MySqlQuery(userName, password, dbms, port, serverOdbName, sid, qId, cmd);
        } else if ("derby".equals(dbms)) {
        	query = new DerbyQuery(userName, password, dbms, port, serverOdbName, sid, qId, cmd);
        } else if ("oracle".equals(dbms)) {
        	query = new OracleQuery(userName, password, dbms, port, serverOdbName, sid, qId, cmd);
        } else if (dbUrl != null) {
        	query = new SimpleQuery(userName, password, cmd, dbUrl, qId);
        }
        
        return query;
    }
} 
