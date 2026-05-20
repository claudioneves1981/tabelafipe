package br.com.springboot.tabelafipe.adapter;

import br.com.springboot.tabelafipe.convert.InstantConvert;
import br.com.springboot.tabelafipe.convert.StatusConvert;
import br.com.springboot.tabelafipe.dto.CharacteristicDTO;
import br.com.springboot.tabelafipe.dto.VehicleDTO;
import br.com.springboot.tabelafipe.entity.*;
import br.com.springboot.tabelafipe.status.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

import static br.com.springboot.tabelafipe.utils.VehicleUtils.getActiveRelayTemp;

@Component
public class VehicleEntityAdapter {

    private final InstantConvert instantConvert = new InstantConvert();

    private final StatusConvert statusConvert = new StatusConvert();

    public VehicleEntity toModel(VehicleDTO vehicleDTO) {

        //int activeRelayTemp = getActiveRelayTemp(vehicleDTO.getLicensePlate());

        return VehicleEntity.builder()
                .id(vehicleDTO.getId())
                .licensePlate(vehicleDTO.getLicensePlate())
                .subscriptionDate(instantConvert.convertStringToInstant(vehicleDTO.getSubscriptionDate()))
                .modelEntity(ModelEntity.builder()
                        .id(vehicleDTO.getModelDTO().getId())
                        .brandEntity(BrandEntity.builder()
                                .code(vehicleDTO.getModelDTO().getBrandDTO().getCode())
                                .name(vehicleDTO.getModelDTO().getBrandDTO().getName())
                                .build())
                        .code(vehicleDTO.getModelDTO().getCode())
                        .name(vehicleDTO.getModelDTO().getName())
                        .build())
                .yearEntity(YearEntity.builder()
                        .code(vehicleDTO.getYearDTO().getCode())
                        .name(vehicleDTO.getYearDTO().getName())
                        .build())
                .characteristicEntity(CharacteristicEntity.builder()
                        .brand(vehicleDTO.getCharacteristicDTO().getBrand())
                        .model(vehicleDTO.getCharacteristicDTO().getModel())
                        .price(vehicleDTO.getCharacteristicDTO().getPrice())
                        .modelYear(vehicleDTO.getCharacteristicDTO().getModelYear())
                        .fuel(vehicleDTO.getCharacteristicDTO().getFuel())
                        .build())
                .color(vehicleDTO.getColor())
                .status(statusConvert.convertStatus(vehicleDTO.getStatus()))
                .build();
    }

}
