package ru.practikum.explorewithme.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "location", schema = "public")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private double lat;

    private double lon;
}
