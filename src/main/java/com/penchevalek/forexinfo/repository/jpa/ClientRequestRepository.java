package com.penchevalek.forexinfo.repository.jpa;

import com.penchevalek.forexinfo.model.ClientRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRequestRepository extends JpaRepository<ClientRequest, String> {
}
