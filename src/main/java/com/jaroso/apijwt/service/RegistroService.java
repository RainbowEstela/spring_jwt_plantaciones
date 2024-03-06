package com.jaroso.apijwt.service;

import com.jaroso.apijwt.entity.Registro;
import com.jaroso.apijwt.repository.RegistroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegistroService {

    @Autowired
    private final RegistroRepository registroRepository;

    public RegistroService(RegistroRepository registroRepository) {
        this.registroRepository = registroRepository;
    }

    // OPERACIONES CRUD
    public List<Registro> findAll() {
        return this.registroRepository.findAll();
    }

    public Optional<Registro> findById(Long id) {
        return this.registroRepository.findById(id);
    }

    public Registro save(Registro registro) {
        return this.registroRepository.save(registro);
    }

    public void deleteById(Long id) {
        this.registroRepository.deleteById(id);
    }
}
