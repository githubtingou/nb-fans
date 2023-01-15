package com.ting.nbfans.mapper;

import com.ting.nbfans.dao.VupFans;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VupFansMapper extends CrudRepository<VupFans, Integer> {
    
    List<VupFans> findByRecordTime(String recordTime);
}
