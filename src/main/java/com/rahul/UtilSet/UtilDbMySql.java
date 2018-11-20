package com.rahul.UtilSet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author 
 */
public class UtilDbMySql{

	public static Connection conn = null;

	public UtilDbMySql()
	{
	}

	/**
	 * Connect to a sample database
	 */
	public void connect() 
	{
		if(null != conn)
		{
			return;
		}

		// create a connection to the database
		try{
			Class.forName("com.mysql.jdbc.Driver");  
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Engine?autoReconnect=true","Engine","Engine");
		}catch(Exception e){
			System.out.println("connect->");
			e.printStackTrace();
		}
	}
	public boolean ExecuteUpdate(String lSql) 
	{
		connect();
		Statement stmt = null;
		try{
			stmt = conn.createStatement();
			stmt.executeUpdate(lSql);
			stmt.close();
			return true;
		}catch(Exception e){
			System.out.println("ExecuteUpdate-> lSql:" + lSql);
			e.printStackTrace();
			return false;
		}
	}

	public ResultSet ExecuteQuery(String lQuery) 
	{
		connect();
		Statement stmt = null;
		ResultSet rs = null;
		try{
			stmt = conn.createStatement();
			rs = stmt.executeQuery( lQuery );
			return rs;
		}catch(Exception e){
			System.out.println("ExecuteQuery-> lQuery:" + lQuery);
			e.printStackTrace();
			return rs;
		}
	}

	public boolean IsTablePresent(String lTableName) 
	{
		connect();

		String lSql = "SELECT * FROM information_schema.tables WHERE table_schema = 'Engine'  AND table_name = '" + lTableName + "'";

		ResultSet lrs = null;
		try{
			lrs = ExecuteQuery(lSql);

			if(lrs.next())
			{
				lrs.close();
				return true;
			}
			else
			{
				lrs.close();
				return false;
			}
		}catch(Exception e){
			System.out.println("IsTablePresent-> lTableName:" + lTableName);
			e.printStackTrace();
			return false;
		}
	}
	public boolean DropTable(String lTableName) 
	{
		connect();

		if(!IsTablePresent(lTableName))return true;

		String lSql = "DROP TABLE " + lTableName ;

		if(ExecuteUpdate(lSql))
			return true;
		else
			return false;
	}
	public boolean DeleteTableData(String lTableName) 
	{
		connect();

		String lSql = "DELETE FROM " + lTableName ;

		if(ExecuteUpdate(lSql))
			return true;
		else
			return false;
	}
}
