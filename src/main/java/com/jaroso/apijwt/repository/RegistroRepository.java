package com.jaroso.apijwt.repository;

import com.jaroso.apijwt.entity.Registro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistroRepository extends JpaRepository<Registro,Long> {
}
