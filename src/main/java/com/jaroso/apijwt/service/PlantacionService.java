package com.jaroso.apijwt.service;

import com.jaroso.apijwt.entity.Plantacion;
import com.jaroso.apijwt.repository.PlantacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlantacionService {

    @Autowired
    private final PlantacionRepository repository;

    public PlantacionService(PlantacionRepository repository) {
        this.repository = repository;
    }

    public List<Plantacion> findAll() {
        return this.repository.findAll();
    }

    public Plantacion save(Plantacion plantacion) {
        return this.repository.save(plantacion);
    };

}
