package com.carpool.service;

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
	
	public void insertRiderSchedule(int id, String from, String to)
	{
		Map<String, Object> map=BuisnessLogic.findDistance(from, to);
		repository.insertRiderSchedule(id, (long)map.get("duration"), from, to, (double)map.get("distance"));
	}
	
	public void insertDriverSchedule(int id, String from, String to, int seats)
	{
		Map<String, Object> map=BuisnessLogic.findDistance(from, to);
		repository.insertDriverSchedule(id, (long)map.get("duration"), from, to, (double)map.get("distance"), seats);
	}

	public Rider getWithMobileAndEmail(String mobile, String email)
	{
		return repository.getByEmailAndMobile(email, mobile);
	}
	
	public List<Map<String, Object>> getRiderScheduleByTimeAndDestination(int id)
	{
		return repository.getRiderScheduleByTimeAndDestination(id);
	}
}	
