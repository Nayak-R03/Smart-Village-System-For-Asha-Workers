//package com.svhrs.repository;
//
//import com.svhrs.model.WorkSchedule;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface WorkScheduleRepository extends JpaRepository<WorkSchedule, Long> {
//    List<WorkSchedule> findByAshaId(Long ashaId);
//}


package com.svhrs.repository; 
import com.svhrs.model.WorkSchedule; 
import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository; 
import java.util.List; 

@Repository 
public interface WorkScheduleRepository extends JpaRepository<WorkSchedule, Long> { 
	List<WorkSchedule> findByAshaId(Long ashaId); 
	}