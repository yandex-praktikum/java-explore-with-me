package ru.practikum.explorewithme.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "categories", schema = "public")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String name;
}
