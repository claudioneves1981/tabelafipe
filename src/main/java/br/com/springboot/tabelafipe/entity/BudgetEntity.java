package br.com.springboot.tabelafipe.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@SequenceGenerator(name = "seq_budget" , sequenceName = "seq_budget", allocationSize = 1)
public class BudgetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_budget")
    @Column(name = "BUDGET_ID")
    Long id;

    String parts;

    Integer quantity;

    Double price;

}
