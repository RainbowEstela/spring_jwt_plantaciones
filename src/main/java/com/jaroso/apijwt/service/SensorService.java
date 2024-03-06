package com.jaroso.apijwt.service;

import com.jaroso.apijwt.dto.TemperaturaHumedadDTO;
import com.jaroso.apijwt.entity.Registro;
import com.jaroso.apijwt.entity.Sensor;
import com.jaroso.apijwt.repository.RegistroRepository;
import com.jaroso.apijwt.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SensorService {
    @Autowired
    private SensorRepository sensorRepository;
    @Autowired
    private RegistroRepository registroRepository;

    // OPERACIONES CRUD
    public List<Sensor> findAll() {
        return this.sensorRepository.findAll();
    }

    public Optional<Sensor> findById(Long id) {
        return this.sensorRepository.findById(id);
    }

    public Sensor save(Sensor sensor) {
        return this.sensorRepository.save(sensor);
    }

    public void deleteById(Long id) {
        this.sensorRepository.deleteById(id);
    }

    public TemperaturaHumedadDTO temperaturaHumedad(Sensor sensor, LocalDate fechaIni,LocalDate fechaFin) {
        var registros = this.registroRepository.findBySensorAndFechaBetween(sensor,fechaIni,fechaFin);
        Double tempTotal = 0.0;
        Double humTotal = 0.0;

        for(Registro registro : registros) {
            tempTotal += registro.getTemperatura();
            humTotal += registro.getHumedad();
        }

        return new TemperaturaHumedadDTO((tempTotal/registros.size()) , (humTotal/registros.size()));

    }
}
