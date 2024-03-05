package com.jaroso.apijwt.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Registro {
    @Id //Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //Autoincrement
    private Long id;

    private LocalDate fecha;

    private LocalTime hora;

    private Double temperatura;

    private Double humedad;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_sensor"))
    @JsonIgnore
    private Sensor sensor;

}
