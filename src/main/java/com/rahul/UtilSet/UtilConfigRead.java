package com.rahul.UtilSet;

import java.io.FileReader;
import java.util.Properties;

public class UtilConfigRead {
	private Properties configFile;

	public UtilConfigRead(String lFileName) throws Exception
	{
		if(0 == lFileName.length())
		{
			throw new Exception ("Cfg File  Name can't be Empty!");
		}

		configFile = new java.util.Properties();

		String lConfigPath = "";
		try{
			lConfigPath = System.getenv("CONFIG_FILE_PATH");
			if(0 == lConfigPath.length()) { throw new Exception ("CONFIG_FILE_PATH env not found!!"); }
		}catch(Exception e){throw new Exception ("CONFIG_FILE_PATH env not found!!");}

		String lCfgFile = lConfigPath + lFileName;

		FileReader reader = new FileReader(lCfgFile);
		configFile.load(reader);
	}

	public String getProperty(String key)
	{
		String value = this.configFile.getProperty(key);
		return value;
	}

	public static void main  (String[] args)
	{
		System.out.println("");
		System.out.println("****************************************");
		System.out.println(">> ConfigReadUtil Stub Test...");
		System.out.println("****************************************");


/*
		System.out.println(">> Creating ConfigReadUtil object with no parameter!");
		try{
			UtilConfigRead obj = new UtilConfigRead("");
		}catch(Exception e){e.printStackTrace();}
		System.out.println(">> Done");

		System.out.println(">> Creating ConfigReadUtil object with String \"abc\" parameter!");
		try{
			UtilConfigRead obj = new UtilConfigRead("abc");
		}catch(Exception e){e.printStackTrace();}
		System.out.println(">> Done");
		*/
	}
}
