package com.jaroso.apijwt.controller;

import com.jaroso.apijwt.entity.Registro;
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

    // TODAS LAS PLANTACIONES
    @GetMapping("/plantaciones")
    public ResponseEntity<List<Plantacion>> findAll() {
        List<Plantacion> plantaciones = this.plantacionService.findAll();
        if (plantaciones.isEmpty())
            return ResponseEntity.notFound().build();  //Devuelve 404 si no hay nada

        return ResponseEntity.ok( plantaciones );
    }

    // UNA PLANTACION POR ID
    @GetMapping("/plantaciones/{id}")
    public ResponseEntity<Plantacion> findById(@PathVariable Long id) {
        return this.plantacionService.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // BORRAR POR ID
    @DeleteMapping("/plantaciones/{id}")
    public ResponseEntity<Plantacion> deleteById(@PathVariable Long id) {
        Plantacion plantacion = this.plantacionService.findById(id).orElse(null);

        if(plantacion == null) {
            return ResponseEntity.notFound().build();
        }

        this.plantacionService.deleteById(id);

        return ResponseEntity.ok(plantacion);

    }

    // CREAR PLANTACION
    @PostMapping("/plantaciones")
    public ResponseEntity<Plantacion> create(@RequestBody Plantacion plantacion) {
        if(plantacion.getId() != null) {
            return ResponseEntity.badRequest().build();
        }

        this.plantacionService.save(plantacion);
        return ResponseEntity.ok(plantacion);
    }

    // MODIFICAR PLANTACION
    @PutMapping("/plantaciones")
    public ResponseEntity<Plantacion> update(@RequestBody Plantacion plantacion) {
        if(plantacion.getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        this.plantacionService.save(plantacion);
        return ResponseEntity.ok(plantacion);
    }

    // REGISTROS DE UNA PLANTACACION
    @GetMapping("/plantacion/{id}")
    public ResponseEntity<List<Registro>> registros(@PathVariable Long id) {
        List<Registro> registros = this.plantacionService.registros(id);
        if(registros.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(registros);
    }
}
