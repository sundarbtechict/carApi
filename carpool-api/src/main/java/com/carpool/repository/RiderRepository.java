package com.carpool.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.carpool.model.User;

public interface RiderRepository extends CrudRepository<User , Integer> ,JpaRepository<User, Integer>
{
	
	@Query("SELECT u FROM User u WHERE u.email = :email AND u.phone = :mobile")
	User getByEmailAndMobile(@Param("email") String email , @Param("mobile") String mobile);
	
	@Modifying
	@Query(value="INSERT INTO `riderschedule`(`userID`, `duration`, `from`, `to`, `distance`, `date_time`, `source`, `destination`)"
			+ "VALUES (:id, SEC_TO_TIME(:duration), :from, :to, :distance, :date_time, :source ,:destination)", nativeQuery = true)
	@Transactional
	public void insertRiderSchedule(@Param("id") int id, @Param("duration") long duration, @Param("from") String from, @Param("to") String to,
			@Param("distance") double distance,  @Param("date_time") Timestamp date_time, @Param("source") String source, @Param("destination") String destination);
	
	@Modifying
	@Query(value="INSERT INTO `driverschedule`(`userID`, `duration`, `from`, `to`, `distance`, `seats_left`, `seats_total`, `date_time`, `source`, `destination`)"
			+ "VALUES (:id, SEC_TO_TIME(:duration), :from, :to, :distance, :seats, :seats, :date_time, :source ,:destination)", nativeQuery = true)
	@Transactional
	public void insertDriverSchedule(@Param("id") int id, @Param("duration") long duration, @Param("from") String from, @Param("to") String to, 
			@Param("distance") double distance, @Param("seats") int seats,  @Param("date_time") Timestamp date_time, @Param("source") String source, @Param("destination") String destination);
	
	@Query(value="SELECT driverScheduleID FROM driverschedule WHERE userID=:id AND date_time=:date_time", nativeQuery = true)
	@Transactional
	public int getDriverScheduleID(@Param("id") int id,  @Param("date_time") Timestamp date_time);
	
	@Query(value="SELECT riderScheduleID FROM riderschedule WHERE userID=:id AND date_time=:date_time", nativeQuery = true)
	@Transactional
	public int getRiderScheduleID(@Param("id") int id,  @Param("date_time") Timestamp date_time);
	
	@Query(value="SELECT * FROM riderschedule WHERE userID=:id ORDER BY date_time DESC", nativeQuery = true)
	@Transactional
	public List<Map<String,Object>> getRiderScheduleID(@Param("id") int id);
	
	@Query(value="SELECT * FROM driverschedule WHERE userID=:id ORDER BY date_time DESC", nativeQuery = true)
	@Transactional
	public List<Map<String,Object>> getDriverScheduleID(@Param("id") int id);
	
	@Query(value="SELECT driverschedule.driverscheduleID, riderschedule.riderScheduleID, driverschedule.userID as driverId, riderschedule.userID as riderId,"
			+ " driverschedule.source, driverschedule.destination, riderschedule.from as waypoint1, riderschedule.to as waypoint2,"
			+ " driverschedule.from, driverschedule.to, driverschedule.distance, driverschedule.seats_left"
			+ " FROM driverschedule INNER JOIN riderschedule ON riderschedule.riderScheduleID=:id "
			+ "AND TIMEDIFF(CURRENT_TIMESTAMP(),riderschedule.date_time) < MAKETIME(0,0,0) "
			+ "AND driverschedule.seats_left > '0' "
			+ "AND TIMEDIFF(riderschedule.date_time,driverschedule.date_time) <= MAKETIME(0,5,0) "
			+ "AND TIMEDIFF(riderschedule.date_time,driverschedule.date_time) >= MAKETIME(0,0,0) " ,nativeQuery = true)
	@Transactional
	public List<Map<String,Object>> getDriverScheduleByTimeAndDestination(@Param("id") int id);
	
	@Query(value="SELECT * FROM riderschedule WHERE rideProvider=:id ORDER BY distance DESC", nativeQuery = true)
	@Transactional
	public List<Map<String,Object>> getRiderScheduleByRideProvider(@Param("id") int id);
	
	@Query(value="SELECT * FROM riderschedule WHERE rideProvider=:id AND confirmed = true ORDER BY distance DESC", nativeQuery = true)
	@Transactional
	public List<Map<String,Object>> getRiderScheduleByConfirmed(@Param("id") int id);
	
	@Modifying
	@Query(value="UPDATE riderschedule SET rideProvider=:driverScheduleId WHERE riderScheduleID=:riderScheduleId ", nativeQuery = true)
	@Transactional
	void updateRideProvider(@Param("driverScheduleId") int driverScheduleId, @Param("riderScheduleId") int riderScheduleId);
	
	@Modifying
	@Query(value="UPDATE riderschedule, driverschedule SET riderschedule.confirmed = true, driverschedule.seats_left = driverschedule.seats_left-1"  
			+ " WHERE riderschedule.riderScheduleID=:riderScheduleId AND driverschedule.driverScheduleID=:driverScheduleId", nativeQuery = true)
	@Transactional
	void updateConfirmed(@Param("riderScheduleId") int riderScheduleId, @Param("driverScheduleId") int driverScheduleId);
	
}
