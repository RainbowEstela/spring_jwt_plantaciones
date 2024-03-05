package com.jaroso.apijwt.repository;

import com.jaroso.apijwt.entity.Plantacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantacionRepository extends JpaRepository<Plantacion, Long> {
}
