package com.jaroso.apijwt.controller;

import com.jaroso.apijwt.dto.TemperaturaHumedadDTO;
import com.jaroso.apijwt.entity.Plantacion;
import com.jaroso.apijwt.entity.Sensor;
import com.jaroso.apijwt.service.PlantacionService;
import com.jaroso.apijwt.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SensorController {
    @Autowired
    private SensorService sensorService;
    @Autowired
    private PlantacionService plantacionService;

    // TODOS LOS SENSORES
    @GetMapping("/sensores")
    public ResponseEntity<List<Sensor>> findAll() {
        List<Sensor> sensores = this.sensorService.findAll();

        if(sensores.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(sensores);
    }



    // UN SENSOR POR ID
    @GetMapping("/sensores/{id}")
    public ResponseEntity<Sensor> findById(@PathVariable Long id) {
        return this.sensorService.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // BORRAR POR ID
    @DeleteMapping("/sensores/{id}")
    public ResponseEntity<Sensor> deleteById(@PathVariable Long id) {
        Sensor sensor = this.sensorService.findById(id).orElse(null);

        if(sensor == null) {
            return ResponseEntity.notFound().build();
        }

        this.sensorService.deleteById(id);
        return ResponseEntity.ok(sensor);
    }

    // CREAR SENSOR CON ID PLANTACION EN URL
    @PostMapping("/sensores/plantacion/{idPlantacion}")
    public ResponseEntity<Sensor> create(@RequestBody Sensor sensor, @PathVariable Long idPlantacion) {
        Plantacion plantacion = this.plantacionService.findById(idPlantacion).orElse(null);

        if(plantacion == null) {
            return ResponseEntity.notFound().build();
        }

        if(sensor.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        sensor.setPlantacion(plantacion);
        this.sensorService.save(sensor);
        return ResponseEntity.ok(sensor);
    }

    // MODIFICAR UN SENSOR PERO NO SU PLANTACION
    @PutMapping("/sensores")
    public ResponseEntity<Sensor> update(@RequestBody Sensor sensor) {
        if (sensor.getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        // cogemos la plantacion asocioada para que no la pierda
        Sensor sensorViejo = this.sensorService.findById(sensor.getId()).orElse(null);

        if(sensorViejo == null) {
            return ResponseEntity.notFound().build();
        }

        sensor.setPlantacion(sensorViejo.getPlantacion());
        this.sensorService.save(sensor);
        return ResponseEntity.ok(sensor);
    }

    // MODIFICAR PLANTACION DE UN SENSOR
    @PutMapping("/sensores/{id}/plantacion/{idPlantacion}")
    public ResponseEntity<Sensor> UpdatePlantacion(@PathVariable Long id, @PathVariable Long idPlantacion) {
        var sensor = this.sensorService.findById(id).orElse(null);
        var plantacion = this.plantacionService.findById(idPlantacion).orElse(null);

        if(sensor == null || plantacion == null) {
            return ResponseEntity.notFound().build();
        }

        sensor.setPlantacion(plantacion);
        this.sensorService.save(sensor);
        return ResponseEntity.ok(sensor);
    }

    // TEMPERATURA HUMEDAD PROMEDIO ENTRE FECHAS
    @GetMapping("/informes/sensor/{id}/fecha-inicio/{fechaIni}/fecha-fin/{fechaFin}")
    public ResponseEntity<TemperaturaHumedadDTO> promedioTempHum(@PathVariable Long id, @PathVariable LocalDate fechaIni, @PathVariable LocalDate fechaFin) {
        var sensor = this.sensorService.findById(id).orElse(null);

        if(sensor == null) {
            return ResponseEntity.notFound().build();
        }

        TemperaturaHumedadDTO mensaje = this.sensorService.temperaturaHumedad(sensor,fechaIni,fechaFin);

        return ResponseEntity.ok(mensaje);
    }
}
