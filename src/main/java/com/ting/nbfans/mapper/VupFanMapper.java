package com.ting.nbfans.mapper;

import com.ting.nbfans.dao.VupFan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VupFanMapper extends JpaRepository<VupFan, Integer> {

    List<VupFan> findByRecordTime(String recordTime);

    @Query(value = "select * from vup_fans where record_time =?1 and uid=?2 limit 1", nativeQuery = true)
    VupFan getByRecordTimeAndAndUid(String recordTime, String uid);
}