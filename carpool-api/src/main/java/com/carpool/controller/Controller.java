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

import com.carpool.model.Rider;
import com.carpool.service.RiderService;

@RestController
public class Controller {
	
	@Autowired
	RiderService service;

	@RequestMapping("/riders/{id}")
	public Optional<Rider> getRider(@PathVariable int id)
	{
		return service.getRider(id);
	}
	
	@RequestMapping(method=RequestMethod.POST , value="/riders")
	public void addRider(@RequestBody Rider r)
	{
	service.addRider(r);
	}
	
	
	
	@RequestMapping(method=RequestMethod.POST , value="/riders/get")
	public Rider get(@RequestBody Map<String ,String> login )
	{
		 String mobile= login.get("phone");
		 String email= login.get("email");
		 return service.getWithMobileAndEmail(mobile, email);
	}
	
	@RequestMapping(method=RequestMethod.POST , value="/riderSchedule")
	public void addRider(@RequestBody Map<String ,Object> map )
	{
		int id=(int) map.get("id");
		String from=(String) map.get("from");
		String to=(String) map.get("to");
		String date_time=(String)map.get("date_time");
		try {
			service.insertRiderSchedule(id, from, to, date_time);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(method=RequestMethod.POST , value="/driverSchedule")
	public void addDriver(@RequestBody Map<String ,Object> map )
	{
		int id=(int) map.get("id");
		String from=(String) map.get("from");
		String to=(String) map.get("to");
		int seats= Integer.parseInt( (String) map.get("seats"));
		String date_time=(String)map.get("date_time");
		try {
			service.insertDriverSchedule(id, from, to, seats, date_time);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/getRiderSchedule/{id}")
	public List<Map<String, Object>> getSchedule(@PathVariable int id)
	{
		return service.getRiderSchedule(id);
	}
}
