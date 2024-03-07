package com.jaroso.apijwt.service;

import com.jaroso.apijwt.dto.TemperaturaHumedadDTO;
import com.jaroso.apijwt.entity.Plantacion;
import com.jaroso.apijwt.entity.Registro;
import com.jaroso.apijwt.entity.Sensor;
import com.jaroso.apijwt.repository.PlantacionRepository;
import com.jaroso.apijwt.repository.RegistroRepository;
import com.jaroso.apijwt.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlantacionService {

    @Autowired
    private final PlantacionRepository repository;
    @Autowired
    private SensorRepository sensorRepository;
    @Autowired
    private RegistroRepository registroRepository;

    public PlantacionService(PlantacionRepository repository) {
        this.repository = repository;
    }

    // OPERACIONES CRUD
    public List<Plantacion> findAll() {
        return this.repository.findAll();
    }

    public Optional<Plantacion> findById(Long id) {
        return this.repository.findById(id);
    }

    public Plantacion save(Plantacion plantacion) {
        return this.repository.save(plantacion);
    }

    public void deleteById(Long id) {
        this.repository.deleteById(id);
    }

    // REGISTROS PLANTACION
    public List<Registro> registros(Long id) {
        Plantacion plantacion = this.repository.findById(id).orElse(null);

        if(plantacion == null) {// si no existe devuelvo un array vacio
            return new ArrayList<>();
        }

        List<Registro> registros = plantacion.getSensores().stream()
                .map(sensor -> sensor.getRegistros())
                .flatMap(List::stream)
                .toList();

        return  registros;
    }

    // REGISTROS PLANTACION FECHA
    public List<Registro> registrosFecha(Long id, LocalDate fecha) {
        Plantacion plantacion = this.repository.findById(id).orElse(null);

        if(plantacion == null) {// si no existe devuelvo un array vacio
            return new ArrayList<>();
        }

        List<Registro> registros = plantacion.getSensores().stream()
                .map(sensor -> this.registroRepository.findBySensorAndFecha(sensor,fecha))
                .flatMap(List::stream)
                .toList();

        return  registros;
    }

    // TEMPERATURA Y HUMEDAD PROMEDIO PLANTACION EN UNA FECHA
    public TemperaturaHumedadDTO promedioFecha(Long id, LocalDate fecha) {
        Plantacion plantacion = this.repository.findById(id).orElse(null);

        if(plantacion == null) {
            return null;
        }

        List<Registro> registros = plantacion.getSensores().stream()
                .map(sensor -> this.registroRepository.findBySensorAndFecha(sensor,fecha))
                .flatMap(List::stream)
                .toList();

        if(registros.isEmpty()) {
            return null;
        }

        Double humedad = registros.stream()
                .mapToDouble(Registro::getHumedad)
                .average().orElse(0);

        Double temperatura = registros.stream()
                .mapToDouble(Registro::getTemperatura)
                .average().orElse(0);

        return new TemperaturaHumedadDTO(temperatura,humedad);
    }
}
