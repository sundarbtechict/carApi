package com.carpool.buisness;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;


public class BuisnessLogic {
	

	public static Map<String, Object> findDistance(String from,String to) 
	{
		Map<String ,Object> map=new HashMap<String ,Object>();
		try {
			// TODO Auto-generated method stub
				
			URL url = new URL("https://maps.googleapis.com/maps/api/distancematrix/json?"
					+ "origins=" + from 
					+ "&destinations="+to
					+ "&key=AIzaSyDfPVS6v3zMZcNEAAIwsfuMVWNgZcykKA4");
			HttpURLConnection conn;
			
				conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("GET");
			String line, outputString = "";
			BufferedReader reader = new BufferedReader(
			new InputStreamReader(conn.getInputStream()));
			while ((line = reader.readLine()) != null) {
			     outputString += line;
			}
			//String outputString="{   \"destination_addresses\" : [      \"Thanjavur New Bus Stand, New Bus Stand Rd, AVP Azhagammal Nagar, Thanjavur, Tamil Nadu 613004, India\"   ],   \"origin_addresses\" : [      \"Tifac Core, Sastra University Road, Tirumalaisamudram, Tamil Nadu 613401, India\"   ],   \"rows\" : [      {         \"elements\" : [            {               \"distance\" : {                  \"text\" : \"12.8 km\",                  \"value\" : 12801               },               \"duration\" : {                  \"text\" : \"17 mins\",                  \"value\" : 1019               },               \"status\" : \"OK\"            }         ]      }   ],   \"status\" : \"OK\"}";
			Object obj=JSONValue.parse(outputString);  
		    JSONObject jsonObject = (JSONObject) obj; 
			
			     System.out.println(outputString);
			     
			     JSONArray array=(JSONArray) jsonObject.get("rows");
			     if(!array.isEmpty())
			     {
			    	 jsonObject= (JSONObject) array.get(0);
				     array=(JSONArray) jsonObject.get("elements");
				     jsonObject= (JSONObject) array.get(0);
				     JSONObject jsonObject2=(JSONObject)jsonObject.get("duration");
				     jsonObject=(JSONObject)jsonObject.get("distance");
				     String km=(String) jsonObject.get("text");
				     double distance = Double.parseDouble(km.replace("km", ""));
				     System.out.println(distance);
				     long time=(long) jsonObject2.get("value");
				     System.out.println(time);
				     
				     map.put("distance", distance);
				     map.put("duration", time);
				     System.out.println(map);
			     }
			     else
			     {
			    	 System.out.println(0);
			    	 throw new Exception("Error in API KEY");
			     }
			
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return map;
		}

	

}
