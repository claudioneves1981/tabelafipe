package br.com.springboot.tabelafipe.service;

import br.com.springboot.tabelafipe.dto.UserDTO;
import br.com.springboot.tabelafipe.dto.VehicleDTO;
import br.com.springboot.tabelafipe.entity.VehicleEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface VehicleService {

    void updateVehicle(VehicleDTO vehicleDTO);

    void saveVehicle(String cpf, VehicleDTO vehicle);

    void deleteVehicle(String cpf, Long id) throws Exception;

    Page<VehicleDTO> getVehicleListPaginated(int selectedPage, int pageSize, String cpf);

    List<String> getStatus();

   VehicleDTO getVehicleById(Long id);

   List<VehicleDTO> getVehicleList(String cpf);
}
