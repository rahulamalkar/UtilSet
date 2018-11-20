package com.rahul.UtilSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class UtilJSONReader {
	public UtilJSONReader()
	{
	}

	public JSONObject JsonParse(String lInJsonString) throws Exception
	{
		JSONObject jsonObject = null;

		if(0 == lInJsonString.length())
		{
			System.out.println("Json string empty!");
			return jsonObject;
			//System.exit(1);
		}

		JSONParser parser = new JSONParser();

		Object obj = parser.parse(lInJsonString);

		jsonObject = (JSONObject) obj;

		return jsonObject;
	}
	public JSONArray GetJsonArray(JSONObject lInJsonObj, String lJsonTag)
	{
		if(null == lInJsonObj || 0 == lJsonTag.length())
		{
			return null;
		}
		return (JSONArray) lInJsonObj.get(lJsonTag);
	}
	public String GetJsonString(JSONObject lInJsonObj, String lJsonTag)
	{
		if(null == lInJsonObj || 0 == lJsonTag.length())
		{
			return "-";
		}
		if(null == (String) lInJsonObj.get(lJsonTag))
			return "-";
		else
			return ((String) lInJsonObj.get(lJsonTag));
	}
}
