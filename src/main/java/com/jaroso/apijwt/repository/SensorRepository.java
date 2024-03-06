package com.jaroso.apijwt.repository;

import com.jaroso.apijwt.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorRepository extends JpaRepository<Sensor,Long> {
}
