package br.com.springboot.tabelafipe.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class BudgetDTO {

    Long id;

    String parts;

    Integer quantity;

    Double price;

    String observation;
}
