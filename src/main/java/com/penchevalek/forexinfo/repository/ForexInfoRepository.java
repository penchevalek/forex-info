package com.penchevalek.forexinfo.repository;

import com.penchevalek.forexinfo.model.ForexInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ForexInfoRepository extends JpaRepository<ForexInfo, ForexInfo.ForexInfoId> {

    @Query(value = "SELECT * FROM forex_info WHERE base = ?1 ORDER BY timestamp DESC LIMIT 1", nativeQuery = true)
    Optional<ForexInfo> findLatestBy(String base);

    List<ForexInfo> findAllByForexInfoIdGreaterThanEqual(ForexInfo.ForexInfoId forexInfoId);
}
