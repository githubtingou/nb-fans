package com.ting.nbfans.mapper;

import com.ting.nbfans.dao.Vup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VupMapper extends JpaRepository<Vup, String> {
}