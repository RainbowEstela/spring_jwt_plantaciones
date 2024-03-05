package com.jaroso.apijwt.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sensor {
    @Id //Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //Autoincrement
    private Long id;

    @Column(unique = true)
    private String identificador;

    private Double latitud;

    private Double longitud;

    private LocalDate fechaInstalcion;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_plantacion"))
    private Plantacion plantacion;

    @OneToMany(mappedBy = "sensor",fetch = FetchType.EAGER)
    private List<Registro> registros;
}
