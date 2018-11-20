package com.rahul.UtilSet;

import java.util.Properties;

public class UtilCfgPool{
	private static UtilCfgPool instance = null;

	private Properties gPropertyPool = new Properties();

	public static UtilCfgPool getInstance() {
		if(null == instance) {
			simulateRandomActivity();
			if(null == instance) {
				instance = new UtilCfgPool();
			}
		}
		return instance;
	}

	private static void simulateRandomActivity() {
		try {
			if(null == instance) {
				Thread.sleep(5);
			}
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void SetProperty(String lKey, String lValue){
		gPropertyPool.setProperty(lKey, lValue);
	}

	public String GetProperty(String lKey){
		return gPropertyPool.getProperty(lKey);
	}

	public static void main(String[] args)
	{
		UtilCfgPool gCfgPool = UtilCfgPool.getInstance();

		gCfgPool.SetProperty("a", "a");
		System.out.println("Prop a:" + gCfgPool.GetProperty("a"));
		System.out.println("Prop b:" + gCfgPool.GetProperty("b"));
	}
}
