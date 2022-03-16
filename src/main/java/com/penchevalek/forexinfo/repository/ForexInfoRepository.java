package com.penchevalek.forexinfo.repository;

import com.penchevalek.forexinfo.model.ForexInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForexInfoRepository extends JpaRepository<ForexInfo, Long> {
}
