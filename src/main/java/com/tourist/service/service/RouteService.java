package com.tourist.service.service;

import com.tourist.service.domain.TouristRoute;
import com.tourist.service.repo.TouristRouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RouteService {

    private final TouristRouteRepository repository;

    public List<TouristRoute> listAll() {
        return repository.findAllByOrderById();
    }

    public TouristRoute get(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("线路不存在"));
    }

    @Transactional
    public TouristRoute save(String name, String spots, String duration, BigDecimal price, String description) {
        TouristRoute r = TouristRoute.builder()
                .name(name).spots(spots).duration(duration).price(price).description(description)
                .build();
        return repository.save(r);
    }

    @Transactional
    public TouristRoute update(Long id, String name, String spots, String duration,
                               BigDecimal price, String description) {
        TouristRoute r = get(id);
        r.setName(name);
        r.setSpots(spots);
        r.setDuration(duration);
        r.setPrice(price);
        r.setDescription(description);
        return repository.save(r);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}