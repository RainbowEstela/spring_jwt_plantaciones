package com.jaroso.apijwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.jaroso.apijwt.service.PlantacionService;
import com.jaroso.apijwt.entity.Plantacion;

import java.util.List;

@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "http://localhost:9000")
public class PlantacionController {

    @Autowired
    private PlantacionService plantacionService;

    @GetMapping("/plantaciones")
    public ResponseEntity<List<Plantacion>> findAll() {
        List<Plantacion> plantaciones = this.plantacionService.findAll();
        if (plantaciones.isEmpty())
            return ResponseEntity.notFound().build();  //Devuelve 404 si no hay nada

        return ResponseEntity.ok( plantaciones );
    }

    @GetMapping("/plantaciones/{id}")
    public ResponseEntity<Plantacion> findById(@PathVariable Long id) {
        //this.service.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        return this.plantacionService.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/plantaciones")
    public ResponseEntity<Plantacion> create(@RequestBody Plantacion plantacion) {
        if(plantacion.getId() != null) {
            return ResponseEntity.badRequest().build();
        }

        this.plantacionService.save(plantacion);
        return ResponseEntity.ok(plantacion);
    }

    @PutMapping("/plantaciones")
    public ResponseEntity<Plantacion> update(@RequestBody Plantacion plantacion) {
        if(plantacion.getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        this.plantacionService.save(plantacion);
        return ResponseEntity.ok(plantacion);
    }
}
