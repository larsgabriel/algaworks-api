package com.example.algamoney.api.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "permition")
@SequenceGenerator(name = "permition_sequence", allocationSize = 1)
public class Permition implements Serializable {

    private static final long serialVersionUID = 5336068292492583872L;
    @Id
    @Column(name = "id", length = 20)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "description", length = 50)
    private String description;

}