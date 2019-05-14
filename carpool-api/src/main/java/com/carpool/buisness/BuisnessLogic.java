package com.carpool.buisness;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Timestamp;

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
					+ "&key=AIzaSyAAUI-JrA5a9-oL9f4uFiTjTbx23x-6gCQ");
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
				     double distance = 0;
				     if(km.contains("km"))
				    	 distance= Double.parseDouble(km.replace("km", ""));
				     else if(km.contains("m")) {
				    	 distance= Double.parseDouble(km.replace("m", ""));
				    	 distance=distance/1000;
				     }
				     System.out.println(distance);
				     long time=(long) jsonObject2.get("value");
				     System.out.println(time);
				     
				     map.put("distance", distance);
				     map.put("duration", time);
				     System.out.println(map);
			     }
			     else
			     {
			    	 System.out.println(array);
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

	public static boolean checkSameDistance(String from,String to, 
			String waypoint1, String waypoint2, double distance) {
		
		Map<String, Object> map1=BuisnessLogic.findDistance(from, waypoint1);
		Map<String, Object> map2=BuisnessLogic.findDistance(waypoint1, waypoint2);
		Map<String, Object> map3=BuisnessLogic.findDistance(waypoint2, to);
		double distance1=(double) map1.get("distance");
		double distance2=(double) map2.get("distance");
		double distance3=(double) map3.get("distance");
		double total=distance1+distance2+distance3;
		System.out.println(" total="+total);
		System.out.println(" driverDistance="+(distance+1));
		if(total<(distance+1))
			return true;
		else
			return false;
	}
	
	public static Timestamp getDateAndTime(String date_time) throws Exception
	{
		System.out.println("in "+date_time);
		 SimpleDateFormat formatter1 = new SimpleDateFormat("MM/dd/yy hh:mm:ss");  
		 Date date = formatter1.parse(date_time);  
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
		 String strDate = formatter.format(date);
		 //System.out.println("Date Format with yyyy-M-dd hh:mm:ss : "+strDate);
		 Timestamp timestamp = Timestamp.valueOf(strDate) ;
		 System.out.println("Date "+timestamp);
		return timestamp;		
	}

}
