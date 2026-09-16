package com.tourist.service.repo;

import com.tourist.service.domain.Transport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransportRepository extends JpaRepository<Transport, Long> {
    List<Transport> findAllByOrderById();
    List<Transport> findByNameContainingIgnoreCaseOrTypeContainingIgnoreCaseOrRouteContainingIgnoreCase(
            String name, String type, String route);
}
