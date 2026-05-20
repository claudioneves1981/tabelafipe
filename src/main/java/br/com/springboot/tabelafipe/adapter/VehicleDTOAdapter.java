package br.com.springboot.tabelafipe.adapter;

import br.com.springboot.tabelafipe.convert.InstantConvert;
import br.com.springboot.tabelafipe.dto.*;
import br.com.springboot.tabelafipe.entity.CharacteristicEntity;
import br.com.springboot.tabelafipe.entity.VehicleEntity;
import br.com.springboot.tabelafipe.status.Status;
import br.com.springboot.tabelafipe.utils.VehicleUtils;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@Component
public class VehicleDTOAdapter {

    private final InstantConvert instantConvert = new InstantConvert();

    public VehicleDTO toDTO(VehicleEntity vehicle){

        return VehicleDTO.builder()
                .yearDTO(YearDTO.builder()
                        .name(vehicle.getYearEntity().getName())
                        .code(vehicle.getYearEntity().getCode())
                        .build())
                .id(vehicle.getId())
                .color(vehicle.getColor())
                .licensePlate(vehicle.getLicensePlate())
                .subscriptionDate(instantConvert.convertInstantToString(vehicle.getSubscriptionDate()))
                .modelDTO(ModelDTO.builder()
                        .id(vehicle.getModelEntity().getId())
                        .name(vehicle.getModelEntity().getName())
                        .code(vehicle.getModelEntity().getCode())
                        .brandDTO(BrandDTO.builder()
                                .id(vehicle.getModelEntity().getBrandEntity().getId())
                                .code(vehicle.getModelEntity().getBrandEntity().getCode())
                                .name(vehicle.getModelEntity().getBrandEntity().getName())
                                .build())
                        .build())
                .characteristicDTO(CharacteristicDTO.builder()
                        .brand(vehicle.getCharacteristicEntity().getBrand())
                        .model(vehicle.getCharacteristicEntity().getModel())
                        .price(vehicle.getCharacteristicEntity().getPrice())
                        .modelYear(vehicle.getCharacteristicEntity().getModelYear())
                        .fuel(vehicle.getCharacteristicEntity().getFuel())
                        .build())
                .statusClass(getStatusClass(vehicle.getStatus()))
                .status(vehicle.getStatus().getDescription())
                .build();

    }

    private String getStatusClass(Status status) {
        return switch (status) {
            case PENDING -> "badge badge-primary";
            case SUCCESS -> "badge badge-success";
            case FAILURE -> "badge badge-danger";
            default -> "badge badge-secondary";
        };
    }




}
