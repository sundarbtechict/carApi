package com.carpool.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carpool.buisness.BuisnessLogic;
import com.carpool.model.User;
import com.carpool.repository.RiderRepository;

@Service
public class RiderService {
	
	@Autowired
	public RiderRepository repository;
	
	public Optional<User> getRider(int id)
	{
		return repository.findById(id);
	}
	
	public void addRider(User r)
	{
		repository.save(r);
	}
	
	public int insertRiderSchedule(int id, String from, String to, String date_time, String source, String destination) throws Exception
	{
		Timestamp timestamp= BuisnessLogic.getDateAndTime(date_time);
		Map<String, Object> map=BuisnessLogic.findDistance(from, to);
		repository.insertRiderSchedule(id, (long)map.get("duration"), from, to, (double)map.get("distance"), timestamp, source, destination);
		int scheduleId=repository.getRiderScheduleID(id, timestamp);
		return scheduleId;
	}
	
	public int insertDriverSchedule(int id, String from, String to, int seats, String date_time, String source, String destination) throws Exception
	{
		Timestamp timestamp= BuisnessLogic.getDateAndTime(date_time);
		Map<String, Object> map=BuisnessLogic.findDistance(from, to);
		repository.insertDriverSchedule(id, (long)map.get("duration"), from, to, (double)map.get("distance"), seats, timestamp, source, destination);
		int scheduleId=repository.getDriverScheduleID(id, timestamp);
		return scheduleId;
	}

	public User getWithMobileAndEmail(String mobile, String email)
	{
		return repository.getByEmailAndMobile(email, mobile);
	}
	
	public List<Map<String, Object>> getDriverSchedule(int id)
	{
		List<Map<String, Object>> list1= repository.getDriverScheduleByTimeAndDestination(id);
		List<Map<String, Object>> list2=new ArrayList<Map<String, Object>>();
		for(Map<String, Object> map: list1 ) {
			System.out.println((String)map.get("from")+(String)map.get("to"));
			if(BuisnessLogic.checkSameDistance(
					(String)map.get("from"),
					(String)map.get("to"),
					(String)map.get("waypoint1"),
					(String)map.get("waypoint2"),
					(double)map.get("distance")))
			{
				System.out.println("hi da");
				Optional<User> optional=repository.findById((int)map.get("driverId"));
				User rider=optional.get();
				Map<String, Object> map2=new HashMap<String, Object>();
				map2.putAll(map);
				map2.put("name",rider.getFirstName());
				map2.put("mobile",rider.getPhone());
				list2.add(map2);
			}
		}
		return list2;
	}
	
	public List<Map<String, Object>> getRiderScheduleByRideProvider(int id){
		List<Map<String, Object>> list1= repository.getRiderScheduleByRideProvider(id);
		List<Map<String, Object>> list2=new ArrayList<Map<String, Object>>();
		for(Map<String, Object> map: list1 ) {
			Optional<User> optional=repository.findById((int)map.get("userID"));
			User rider=optional.get();
			Map<String, Object> map2=new HashMap<String, Object>();
			map2.putAll(map);
			map2.put("name",rider.getFirstName());
			map2.put("mobile",rider.getPhone());
			list2.add(map2);
		}
		return list2;
	}
	
	public List<Map<String, Object>> getRiderSchedules(int id){
		List<Map<String, Object>> list= repository.getRiderScheduleID(id);
		List<Map<String, Object>> list2=new ArrayList<Map<String, Object>>();
		for(Map<String, Object> map: list ) {
			String s=map.get("date_time").toString();
			Map<String, Object> map2=new HashMap<String, Object>();
			map2.putAll(map);
			map2.put("dateTime", s);
			list2.add(map2);
		}
		return list2;
		
	}
	
	public List<Map<String, Object>> getDriverSchedules(int id){
		List<Map<String, Object>> list= repository.getDriverScheduleID(id);
		List<Map<String, Object>> list2=new ArrayList<Map<String, Object>>();
		for(Map<String, Object> map: list ) {
			String s=map.get("date_time").toString();
			Map<String, Object> map2=new HashMap<String, Object>();
			map2.putAll(map);
			map2.put("dateTime", s);
			list2.add(map2);
		}
		return list2;
		
	}

	public void updateRideProvider(int driverScheduleId, int riderScheduleId) {
		repository.updateRideProvider(driverScheduleId, riderScheduleId);
	}
	
	public void updateConfirmed(int riderScheduleId) {
		repository.updateConfirmed(riderScheduleId);
	}
}	
