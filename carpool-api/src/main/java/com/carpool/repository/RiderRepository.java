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

import com.carpool.model.Rider;

public interface RiderRepository extends CrudRepository<Rider , Integer> ,JpaRepository<Rider, Integer>
{
	
	@Query("SELECT u FROM Rider u WHERE u.email = :email AND u.phone = :mobile")
	Rider getByEmailAndMobile(@Param("email") String email , @Param("mobile") String mobile);
	
	@Modifying
	@Query(value="INSERT INTO `riderschedule`(`scheduleID`, `duration`, `from`, `to`, `distance`, `date_time`)"
			+ "VALUES (:id, SEC_TO_TIME(:duration), :from, :to, :distance, :date_time)", nativeQuery = true)
	@Transactional
	public void insertRiderSchedule(@Param("id") int id, @Param("duration") long duration, @Param("from") String from,
			@Param("to") String to, @Param("distance") double distance,  @Param("date_time") Timestamp date_time);
	
	@Modifying
	@Query(value="INSERT INTO `driverschedule`(`driverscheduleID`, `duration`, `from`, `to`, `distance`, `seats_left`, `seats_total`, `date_time`)"
			+ "VALUES (:id, SEC_TO_TIME(:duration), :from, :to, :distance, :seats, :seats, :date_time)", nativeQuery = true)
	@Transactional
	public void insertDriverSchedule(@Param("id") int id, @Param("duration") long duration, @Param("from") String from,
			@Param("to") String to, @Param("distance") double distance, @Param("seats") int seats,  @Param("date_time") Timestamp date_time);
	
	@Query(value="SELECT driverschedule.driverscheduleID, riderschedule.scheduleID,driverschedule.from ,riderschedule.from as waypoints, "
			+ "driverschedule.to, driverschedule.distance,driverschedule.seats_left FROM driverschedule "
			+ "INNER JOIN riderschedule ON riderschedule.scheduleID=:id "
			//+ "AND TIMEDIFF(CURRENT_TIMESTAMP(),riderschedule.dateTime) <= MAKETIME(1,0,0) "
			+ "AND TIMEDIFF(riderschedule.dateTime,driverschedule.dateTime) <= MAKETIME(0,5,0) "
			+ "AND TIMEDIFF(riderschedule.dateTime,driverschedule.dateTime) >= MAKETIME(0,0,0) "
			+ "AND driverschedule.to=riderschedule.to", nativeQuery = true)
	@Transactional
	public List<Map<String,Object>> getRiderScheduleByTimeAndDestination(@Param("id") int id);
	
}
