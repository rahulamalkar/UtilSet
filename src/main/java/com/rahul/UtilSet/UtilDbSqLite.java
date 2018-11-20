package com.rahul.UtilSet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;

/**
 *
 * @author 
 */
public class UtilDbSqLite{

	static final Logger LOGGER = Logger.getLogger(UtilDbSqLite.class.getName());
	
	public static Connection conn = null;

	private String gDbFilePath = "";

	public UtilDbSqLite(String lDbFile){
		gDbFilePath = lDbFile;
	}

	public UtilDbSqLite()
	{
		String gDbFilePath = System.getenv("SQLITE_DB_FILE_PATH");

		if( 0 == gDbFilePath.length())
		{
			System.out.println("Missing - SQLITE_DB_FILE_PATH env!");
			System.exit(1);
		}

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

		// db parameters
		String url = "jdbc:sqlite:" + gDbFilePath;// + ";Version=3;Pooling=True;Max Pool Size=100";
		// create a connection to the database
		try{
			conn = DriverManager.getConnection(url);
		}catch(Exception e){
			System.out.println("DB connection Exception:" + e);
		}
	}

	public boolean ExecuteUpdate(String lSql) throws Exception
	{
		connect();
		Statement stmt = null;
		try{
			stmt = conn.createStatement();
			stmt.executeUpdate(lSql);
			stmt.close();
			return true;
		}catch(Exception e){
			if(e.getMessage().contains("[SQLITE_BUSY]"))
			{
				try{ Thread.sleep(10); } catch(InterruptedException ex) { Thread.currentThread().interrupt(); }
				return this.ExecuteUpdate(lSql);
			}
			else
			{
				System.out.println("ExecuteUpdate-> lSql:" + lSql);
				e.printStackTrace();
				throw e;
			}
		}
	}

	public ResultSet ExecuteQuery(String lQuery) throws Exception
	{
		connect();
		Statement stmt = null;
		ResultSet rs = null;
		try{
			stmt = conn.createStatement();
			rs = stmt.executeQuery( lQuery );
			return rs;
		}catch(Exception e){
			if(e.getMessage().contains("[SQLITE_BUSY]"))
			{
				try{ Thread.sleep(10); } catch(InterruptedException ex) { Thread.currentThread().interrupt(); }
				return this.ExecuteQuery(lQuery);
			}
			else
			{
				System.out.println("ExecuteQuery-> lQuery:" + lQuery);
				e.printStackTrace();
				throw e;
			}
		}
	}

	public boolean IsTablePresent(String lTableName) 
	{
		connect();

		String lSql = "SELECT name FROM sqlite_master WHERE type='table' AND name='" + lTableName + "';";

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
			System.out.println("IsTablePresent Exception:" + e);
			return false;
		}
	}
	public boolean DropTable(String lTableName) 
	{
		connect();

		if(!IsTablePresent(lTableName))return true;

		String lSql = "DROP TABLE '" + lTableName + "';";

		try{
			ExecuteUpdate(lSql);
		}catch(Exception e){
			System.out.println("DropTable-> lTableName:" + lTableName);
			e.printStackTrace();
		}
		return true;
	}
	public boolean DeleteTableData(String lTableName) 
	{
		connect();

		String lSql = "DELETE FROM '" + lTableName + "';";

		try{
			ExecuteUpdate(lSql);
		}catch(Exception e){
			System.out.println("DeleteTableData-> lTableName:" + lTableName);
			e.printStackTrace();
		}
		return true;
	}
}
