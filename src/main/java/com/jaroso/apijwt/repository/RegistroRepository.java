package com.jaroso.apijwt.repository;

import com.jaroso.apijwt.entity.Registro;
import com.jaroso.apijwt.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RegistroRepository extends JpaRepository<Registro,Long> {
    List<Registro> findBySensorAndFechaBetween(Sensor sensor, LocalDate fechaIni, LocalDate fechaFin);
}
