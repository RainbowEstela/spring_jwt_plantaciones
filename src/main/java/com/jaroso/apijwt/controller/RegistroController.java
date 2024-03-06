package com.jaroso.apijwt.controller;

import com.jaroso.apijwt.entity.Registro;
import com.jaroso.apijwt.entity.Sensor;
import com.jaroso.apijwt.service.RegistroService;
import com.jaroso.apijwt.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RegistroController {
    @Autowired
    private RegistroService registroService;
    @Autowired
    private SensorService sensorService;

    // TODOS LOS REGISTROS
    @GetMapping("/registros")
    public ResponseEntity<List<Registro>> findAll() {
        List<Registro> registros = this.registroService.findAll();

        if(registros.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(registros);
    }

    // UN REGISTRO POR ID
    @GetMapping("/registros/{id}")
    public ResponseEntity<Registro> findById(@PathVariable Long id) {
        return this.registroService.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // BORRAR POR ID
    @DeleteMapping("/registros/{id}")
    public ResponseEntity<Registro> deleteById(@PathVariable Long id) {
        Registro registro = this.registroService.findById(id).orElse(null);

        if(registro == null) {
            return ResponseEntity.notFound().build();
        }

        this.registroService.deleteById(id);
        return ResponseEntity.ok(registro);
    }

    // CREAR UN REGISTRO CON ID SENSOR EN URL
    @PostMapping("/registros/sensor/{idSensor}")
    public ResponseEntity<Registro> create(@RequestBody Registro registro, @PathVariable Long idSensor) {
        Sensor sensor = this.sensorService.findById(idSensor).orElse(null);

        if(sensor == null) {
            return ResponseEntity.notFound().build();
        }

        if (registro.getId() != null) {
            return ResponseEntity.badRequest().build();
        }

        registro.setSensor(sensor);
        this.registroService.save(registro);
        return ResponseEntity.ok(registro);
    }

    // MODIFICAR UN REGISTRO PERO NO SU SENSOR
    @PutMapping("/registros")
    public ResponseEntity<Registro> update(@RequestBody Registro registro) {
        if(registro.getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        // cogemos el antiguo sensor para no perderlo
        Registro registroViejo = this.registroService.findById(registro.getId()).orElse(null);

        if(registroViejo == null) {
            return ResponseEntity.notFound().build();
        }

        registro.setSensor(registroViejo.getSensor());
        this.registroService.save(registro);
        return ResponseEntity.ok(registro);

    }

    // MODIFICAR SENSOR DE UN REGISTRO
    @PutMapping("/registros/{id}/sensor/{idSensor}")
    public ResponseEntity<Registro> UpdateSensor(@PathVariable Long id, @PathVariable Long idSensor) {
        var registro = this.registroService.findById(id).orElse(null);
        var sensor = this.sensorService.findById(idSensor).orElse(null);

        if(registro == null || sensor == null) {
            return ResponseEntity.notFound().build();
        }

        registro.setSensor(sensor);
        this.registroService.save(registro);
        return ResponseEntity.ok(registro);
    }
}
