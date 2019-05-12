package com.carpool.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carpool.buisness.BuisnessLogic;
import com.carpool.model.Rider;
import com.carpool.repository.RiderRepository;

@Service
public class RiderService {
	
	@Autowired
	public RiderRepository repository;
	
	public Optional<Rider> getRider(int id)
	{
		return repository.findById(id);
	}
	
	public void addRider(Rider r)
	{
		repository.save(r);
	}
	
	public void insertRiderSchedule(int id, String from, String to, String date_time) throws Exception
	{
		Timestamp timestamp= BuisnessLogic.getDateAndTime(date_time);
		Map<String, Object> map=BuisnessLogic.findDistance(from, to);
		repository.insertRiderSchedule(id, (long)map.get("duration"), from, to, (double)map.get("distance"), timestamp);
	}
	
	public void insertDriverSchedule(int id, String from, String to, int seats, String date_time) throws Exception
	{
		Timestamp timestamp= BuisnessLogic.getDateAndTime(date_time);
		Map<String, Object> map=BuisnessLogic.findDistance(from, to);
		repository.insertDriverSchedule(id, (long)map.get("duration"), from, to, (double)map.get("distance"), seats, timestamp);
	}

	public Rider getWithMobileAndEmail(String mobile, String email)
	{
		return repository.getByEmailAndMobile(email, mobile);
	}
	
	public List<Map<String, Object>> getRiderSchedule(int id)
	{
		List<Map<String, Object>> list= repository.getRiderScheduleByTimeAndDestination(id);
		List<Map<String, Object>> list2=new ArrayList<Map<String, Object>>();
		for(Map<String, Object> map: list ) {
			System.out.println((String)map.get("from")+(String)map.get("to")+(String)map.get("waypoints")+
					(double)map.get("distance"));
			if(BuisnessLogic.checkSameDistance(
					(String)map.get("from"),
					(String)map.get("to"),
					(String)map.get("waypoints"),
					(double)map.get("distance")))
			{
				System.out.println("hi da");
				Optional<Rider> optional=repository.findById((int)map.get("driverscheduleID"));
				Rider rider=optional.get();
				Map<String, Object> map2=new HashMap<String, Object>();
				map2.put("name",rider.getFirstName());
				map2.put("id",rider.getId());
				map2.put("mobile",rider.getPhone());
				list2.add(map2);
			}
		}
		return list2;
	}
}	
