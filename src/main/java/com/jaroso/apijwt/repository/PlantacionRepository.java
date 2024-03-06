package com.jaroso.apijwt.repository;

import com.jaroso.apijwt.entity.Plantacion;
import com.jaroso.apijwt.entity.Registro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantacionRepository extends JpaRepository<Plantacion, Long> {

}
