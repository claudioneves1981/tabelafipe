package br.com.springboot.tabelafipe.service;

import br.com.springboot.tabelafipe.dto.UserDTO;
import br.com.springboot.tabelafipe.entity.UserEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    UserDTO getUserById(Long id);

    void saveUser(UserDTO userDTO);

    void updateUser(UserDTO userDTO);

    void deleteUser(Long id) throws Exception;

    Page<UserDTO> getUserListPaginated(int selectedPage, int pageSize);

    List<UserDTO> getUserList();
}
