package br.com.springboot.tabelafipe.dto;

import br.com.springboot.tabelafipe.entity.YearEntity;
import br.com.springboot.tabelafipe.entity.CharacteristicEntity;
import br.com.springboot.tabelafipe.entity.ModelEntity;
import br.com.springboot.tabelafipe.status.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VehicleDTO {

    private Long id;

    private YearDTO yearDTO;

    private CharacteristicDTO characteristicDTO;

    @NotEmpty(message = "Cor is mandatory")
    private String color;

    @NotEmpty(message = "Date cannot empty, please inform!")
    private String subscriptionDate;

    private ModelDTO modelDTO;

    @NotEmpty(message = "Placa is mandatory")
    private String licensePlate;

    //@NotEmpty(message = "Renavam is mandatory")
    //@Size(min = 11, max = 11, message = "Renavam contains 11 digits")
    //private String renavam;

    private String statusClass;

    private String status;

    //private boolean activeRelay;

    //private String relay;

    private List<BudgetDTO> budgets;

    private Double totalValue;

}
