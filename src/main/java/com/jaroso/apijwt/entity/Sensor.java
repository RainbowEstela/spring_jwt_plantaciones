package com.jaroso.apijwt.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    private LocalDate fechaInstalacion;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_plantacion"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Plantacion plantacion;

    @OneToMany(mappedBy = "sensor",fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Registro> registros;
}
