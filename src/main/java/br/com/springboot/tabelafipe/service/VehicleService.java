package br.com.springboot.tabelafipe.service;

import br.com.springboot.tabelafipe.dto.VehicleDTO;
import br.com.springboot.tabelafipe.entity.VehicleEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface VehicleService {

    Iterable<VehicleEntity> findAll();

    void saveVehicle(VehicleDTO vehicle);

    void updateVehicle(VehicleEntity vehicle);

    void deleteVehicle(Long id);

    VehicleEntity findByRenavam(String renavam);

    Page<VehicleDTO> getVehicleListPaginated(int selectedPage, int pageSize, String globalStatus);

    List<String> getStatus();

    List<String> getBrands();

    List<String> getModels(VehicleDTO vehicle);

    List<String> getYears(VehicleDTO vehicleDTO);

    String getFuel(VehicleDTO vehicle);
}
