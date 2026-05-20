package br.com.springboot.tabelafipe.service.impl;

import br.com.springboot.tabelafipe.adapter.VehicleDTOAdapter;
import br.com.springboot.tabelafipe.adapter.VehicleEntityAdapter;
import br.com.springboot.tabelafipe.convert.InstantConvert;
import br.com.springboot.tabelafipe.convert.StatusConvert;
import br.com.springboot.tabelafipe.dto.*;
import br.com.springboot.tabelafipe.entity.*;
import br.com.springboot.tabelafipe.exceptions.UserNotFoundException;
import br.com.springboot.tabelafipe.exceptions.VehicleNotFoundException;
import br.com.springboot.tabelafipe.repository.UserRepository;
import br.com.springboot.tabelafipe.repository.VehicleRepository;
import br.com.springboot.tabelafipe.service.FipeService;
import br.com.springboot.tabelafipe.service.VehicleService;
import br.com.springboot.tabelafipe.status.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    private final UserRepository userRepository;

    private final StatusConvert statusConvert;

    private final InstantConvert instantConvert;

    private final VehicleDTOAdapter vehicleDTOAdapter;

    private final VehicleEntityAdapter vehicleEntityAdapter;

    public VehicleServiceImpl(final StatusConvert statusConvert, final VehicleDTOAdapter vehicleDTOAdapter, final VehicleEntityAdapter vehicleEntityAdapter, final VehicleRepository vehicleRepository, final UserRepository userRepository, InstantConvert instantConvert) {

        this.statusConvert = statusConvert;
        this.vehicleDTOAdapter = vehicleDTOAdapter;
        this.vehicleEntityAdapter = vehicleEntityAdapter;
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
        this.instantConvert = instantConvert;
    }


    @Override
    public VehicleDTO getVehicleById(Long id) {
        final Optional<VehicleEntity> optionalTaskEntity = vehicleRepository.findById(id);
        if(optionalTaskEntity.isPresent()){
            return vehicleDTOAdapter.toDTO(optionalTaskEntity.get());
        }else{
            throw new VehicleNotFoundException("Vehicle with id"+id+" not found");
        }
    }

    @Override
    public void updateVehicle(VehicleDTO vehicleDTO) {

        try {

            final Optional<VehicleEntity> optionalVehicleEntity = vehicleRepository.findById(vehicleDTO.getId());
            if (optionalVehicleEntity.isPresent()) {
                VehicleEntity vehicleEntity = optionalVehicleEntity.get();
                        vehicleEntity.setLicensePlate(vehicleDTO.getLicensePlate());
                        vehicleEntity.setSubscriptionDate(instantConvert.convertStringToInstant(vehicleDTO.getSubscriptionDate()));
                        vehicleEntity.setModelEntity(ModelEntity.builder()
                                .id(vehicleDTO.getModelDTO().getId())
                                .brandEntity(BrandEntity.builder()
                                        .code(vehicleDTO.getModelDTO().getBrandDTO().getCode())
                                        .name(vehicleDTO.getModelDTO().getBrandDTO().getName())
                                        .build())
                                .code(vehicleDTO.getModelDTO().getCode())
                                .name(vehicleDTO.getModelDTO().getName())
                                .build());
                        vehicleEntity.setYearEntity(YearEntity.builder()
                                .code(vehicleDTO.getYearDTO().getCode())
                                .name(vehicleDTO.getYearDTO().getName())
                                .build());
                        vehicleEntity.setCharacteristicEntity(CharacteristicEntity.builder()
                                .brand(vehicleDTO.getCharacteristicDTO().getBrand())
                                .model(vehicleDTO.getCharacteristicDTO().getModel())
                                .price(vehicleDTO.getCharacteristicDTO().getPrice())
                                .modelYear(vehicleDTO.getCharacteristicDTO().getModelYear())
                                .fuel(vehicleDTO.getCharacteristicDTO().getFuel())
                                .build());
                        vehicleEntity.setColor(vehicleDTO.getColor());
                        vehicleEntity.setStatus(statusConvert.convertStatus(vehicleDTO.getStatus()));
                vehicleRepository.save(vehicleEntity);
            } else {
                throw new VehicleNotFoundException("Vehicle with id"+vehicleDTO.getId()+" not found");
            }
        }catch(final RuntimeException re) {
            throw re;
        }
    }

    @Override
    public void saveVehicle(String cpf, VehicleDTO vehicle) {

        try{

        UserEntity userEntity = userRepository.findByCpf(cpf);
        List<VehicleEntity> vehicleEntityList = userEntity.getVehicles();
        VehicleEntity vehicleEntity = vehicleEntityAdapter.toModel(vehicle);
        vehicleEntityList.add(vehicleEntity);
        userEntity.setVehicles(vehicleEntityList);
        userRepository.save(userEntity);

        } catch(UserNotFoundException re){
            throw re;
        }
    }

    @Override
    public void deleteVehicle(String cpf, Long id) throws Exception {


        UserEntity userEntity = userRepository.findByCpf(cpf);
        VehicleEntity vehicleEntity = vehicleRepository.findById(id).
                orElseThrow(()-> new Exception("Vehicle Not Found"));
        userEntity.getVehicles().remove(vehicleEntity);
        vehicleRepository.delete(vehicleEntity);
        userRepository.save(userEntity);
    }


    @Override
    public List<String> getStatus(){
        return Arrays.asList(Status.SUCCESS.getDescription(),Status.FAILURE.getDescription(),Status.PENDING.getDescription(),Status.VEHICLE_ALREADY_EXISTS.getDescription());
    }

    public Page<VehicleDTO> getVehicleListPaginated(int pageNo, int pageSize, String cpf){

        List<VehicleDTO> vehicleDTOList;
        Page<VehicleDTO> page;
        vehicleDTOList = getVehicleList(cpf);
        pageNo = 1;



        if(!vehicleDTOList.isEmpty()){
            if(vehicleDTOList.size() < pageSize){
                pageSize = vehicleDTOList.size();
            }
            Pageable pageable = PageRequest.of(0, pageSize);
            int start = (int)pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), vehicleDTOList.size());
            List<VehicleDTO> subList = vehicleDTOList.subList(start, end);
            page = new PageImpl<>(subList, pageable, vehicleDTOList.size());
        }else{
            page = Page.empty();
        }

        return page;
    }

    @Override
    public List<VehicleDTO> getVehicleList(String cpf){

        UserEntity userEntity = userRepository.findByCpf(cpf);
        List<VehicleEntity> vehicleEntityList = userEntity.getVehicles();

        return vehicleEntityList
                .stream()
                .map(vehicleDTOAdapter::toDTO)
                .collect(Collectors.toList());
    }


}
