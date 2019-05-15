import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Date; 
import java.text.ParseException; 


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import java.text.ParseException;

public class Main {

	public static void main(String[] args) throws Exception {
//		try {
//		URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?"
//				+ "latlng=10.7285151,79.0184082" 
//				+ "&key=AIzaSyAAUI-JrA5a9-oL9f4uFiTjTbx23x-6gCQ");
//		HttpURLConnection conn;
//		
//			conn = (HttpURLConnection) url.openConnection();
//		
//		conn.setRequestMethod("GET");
//		String line, outputString = "";
//		BufferedReader reader = new BufferedReader(
//		new InputStreamReader(conn.getInputStream()));
//		while ((line = reader.readLine()) != null) {
//		     outputString += line;
//		}
//		   // String outputString="{   \"destination_addresses\" : [      \"Thanjavur New Bus Stand, New Bus Stand Rd, AVP Azhagammal Nagar, Thanjavur, Tamil Nadu 613004, India\"   ],   \"origin_addresses\" : [      \"Tifac Core, Sastra University Road, Tirumalaisamudram, Tamil Nadu 613401, India\"   ],   \"rows\" : [      {         \"elements\" : [            {               \"distance\" : {                  \"text\" : \"12.8 km\",                  \"value\" : 12801               },               \"duration\" : {                  \"text\" : \"17 mins\",                  \"value\" : 1019               },               \"status\" : \"OK\"            }         ]      }   ],   \"status\" : \"OK\"}";
////				Object obj=JSONValue.parse(outputString);  
////			    JSONObject jsonObject = (JSONObject) obj; 
//				
//				     System.out.println(outputString);
//				     
////				     JSONArray array=(JSONArray) jsonObject.get("rows");
////				     if(!array.isEmpty())
////				     {
////				     jsonObject= (JSONObject) array.get(0);
////				     array=(JSONArray) jsonObject.get("elements");
////				     jsonObject= (JSONObject) array.get(0);
////				     JSONObject jsonObject2=(JSONObject)jsonObject.get("duration");
////				     jsonObject=(JSONObject)jsonObject.get("distance");
////				     String km=(String) jsonObject.get("text");
////				     double distance = Double.parseDouble(km.replace("km", ""));
////				     System.out.println(distance);
////				     long time=(long) jsonObject2.get("value");
////				     System.out.println(time);
////				     Map<String ,Object> map=new HashMap<String ,Object>();
////				     map.put("distance", distance);
////				     map.put("duration", time);
////				     System.out.println(map);
////				     System.out.println(Math.round(11.4));
////				     }
////				     else
////				     {
////				    	 System.out.println(0);
////				    	 throw new Exception("Error in API KEY");
////				     }
//		
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();




		// Create SimpleDateFormat object 
		SimpleDateFormat 
			sdfo 
			= new SimpleDateFormat("yyyy-M-dd HH:mm:ss"); 

		// Get the two dates to be compared 
		Date d1 = sdfo.parse("2019-05-15 10:10:00.0");
      
		Date d2=new Date(); 
	 final long HOUR = 3600*1000;
       d1 = new Date(d1.getTime() + 2* HOUR);
		// Print the dates 
		System.out.println("ride : " + sdfo.format(d1)); 
		System.out.println("current date : " + sdfo.format(d2)); 
      System.out.println(d2); 

		// Compare the dates using compareTo() 
		if (d1.compareTo(d2) > 0) { 

			// When Date d1 > Date d2 
			System.out.println("Date1 is after Date2"); 
		} 

		else if (d1.compareTo(d2) < 0) { 

			// When Date d1 < Date d2 
			System.out.println("Date1 is before Date2"); 
		} 

		else if (d1.compareTo(d2) == 0) { 

			// When Date d1 = Date d2 
			System.out.println("Date1 is equal to Date2"); 
		} 
	} 

//		}
}


