package ru.skillbox.homework4.commentary.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name = "commentaries")
@NoArgsConstructor
@AllArgsConstructor
public class Commentary {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

}
