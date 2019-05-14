package com.carpool.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.carpool.model.User;
import com.carpool.service.RiderService;

@RestController
public class Controller {
	
	@Autowired
	RiderService service;

	@RequestMapping("/riders/{id}")
	public Optional<User> getRider(@PathVariable int id)
	{
		return service.getRider(id);
	}
	
	@RequestMapping(method=RequestMethod.POST , value="/riders")
	public void addRider(@RequestBody User r)
	{
	service.addRider(r);
	}
	
	
	
	@RequestMapping(method=RequestMethod.POST , value="/riders/get")
	public User get(@RequestBody Map<String ,String> login )
	{
		 String mobile= login.get("phone");
		 String email= login.get("email");
		 return service.getWithMobileAndEmail(mobile, email);
	}
	
	@RequestMapping(method=RequestMethod.POST , value="/riderSchedule")
	public int addRiderSchedule(@RequestBody Map<String ,Object> map )
	{
		int id=(int) map.get("id");
		String from=(String) map.get("from");
		String to=(String) map.get("to");
		String date_time=(String)map.get("date_time");
		String source=(String)map.get("source");
		String destination=(String)map.get("destination");
		try {
			return service.insertRiderSchedule(id, from, to, date_time, source, destination);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@RequestMapping(method=RequestMethod.POST , value="/driverSchedule")
	public int addDriverSchedule(@RequestBody Map<String ,Object> map )
	{
		int id=(int) map.get("id");
		String from=(String) map.get("from");
		String to=(String) map.get("to");
		int seats= Integer.parseInt( (String) map.get("seats"));
		String date_time=(String)map.get("date_time");
		String source=(String)map.get("source");
		String destination=(String)map.get("destination");
		try {
			return service.insertDriverSchedule(id, from, to, seats, date_time, source, destination);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@RequestMapping("/getDriverSchedule/{id}")
	public List<Map<String, Object>> getDriverSchedule(@PathVariable int id)
	{
		return service.getDriverSchedule(id);
	}
	
	@RequestMapping("/getRiderSchedule/{id}")
	public List<Map<String, Object>> getRiderScheduleByRideProvider(@PathVariable int id){
		return service.getRiderScheduleByRideProvider(id);
	}
	
	@RequestMapping("/getRiderSchedules/{id}")
	public List<Map<String, Object>> getRiderSchedules(@PathVariable int id)
	{
		return service.getRiderSchedules(id);
	}
	
	@RequestMapping("/getDriverSchedules/{id}")
	public List<Map<String, Object>> getDriverSchedules(@PathVariable int id)
	{
		return service.getDriverSchedules(id);
	}
	

	@RequestMapping(method=RequestMethod.PUT , value="/updaterideProvider")
	public void updaterideProvider(@RequestBody Map<String ,Object> ids )
	{
		 int driverScheduleId= (int)ids.get("driverScheduleId");
		 int riderScheduleId= (int)ids.get("riderScheduleId");
		 service.updateRideProvider(driverScheduleId, riderScheduleId);
	}
	
	@RequestMapping(method=RequestMethod.PUT , value="/updateConfirmed")
	public void updateConfirmed(@RequestBody Map<String ,Object> id)
	{
		System.out.println("hi");
		int riderScheduleId= (int)id.get("riderScheduleId");
		 service.updateConfirmed(riderScheduleId);
	}
}
