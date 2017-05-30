package com.vannhan.GoogleCloudDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnector {
	
	/*public static Connection getConnection() throws SQLException, ClassNotFoundException {
		String hostName = "localhost";
		String dbName = "google_cloud_group2";
		String username = "root";
		String password = "983537";
		
		String url = "jdbc:mysql://" + hostName + ":3306/" + dbName +"?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		Connection conn = DriverManager.getConnection(url, username, password);
		
		return conn;
	}*/
	
	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		String databaseName = "sql_group2";
		String instanceConnectionName = "ccassignmentmidterm-166523:asia-northeast1:hhhhh";
		
		String jdbcUrl = String.format(
			    "jdbc:mysql://google/%s?cloudSqlInstance=%s&socketFactory=com.google.cloud.sql.mysql.SocketFactory",
			    databaseName,
			    instanceConnectionName);
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		Connection conn = DriverManager.getConnection(jdbcUrl, "root", "root1234");
		
		return conn;
	}
}
