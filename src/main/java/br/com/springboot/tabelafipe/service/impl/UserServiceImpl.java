package br.com.springboot.tabelafipe.service.impl;

import br.com.springboot.tabelafipe.adapter.*;
import br.com.springboot.tabelafipe.adapter.UserDTOAdapter;
import br.com.springboot.tabelafipe.adapter.UserEntityAdapter;
import br.com.springboot.tabelafipe.convert.StatusConvert;
import br.com.springboot.tabelafipe.dto.UserDTO;
import br.com.springboot.tabelafipe.dto.UserDTO;
import br.com.springboot.tabelafipe.entity.UserEntity;
import br.com.springboot.tabelafipe.entity.UserEntity;
import br.com.springboot.tabelafipe.entity.VehicleEntity;
import br.com.springboot.tabelafipe.exceptions.UserNotFoundException;
import br.com.springboot.tabelafipe.repository.UserRepository;
import br.com.springboot.tabelafipe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserDTOAdapter userDTOAdapter;

    private final UserEntityAdapter userEntityAdapter;


    @Autowired
    public UserServiceImpl(final UserRepository userRepository, final UserDTOAdapter userDTOAdapter, final UserEntityAdapter userEntityAdapter){
        this.userRepository = userRepository;
        this.userDTOAdapter = userDTOAdapter;
        this.userEntityAdapter = userEntityAdapter;
    }

    @Override
    public UserDTO getUserById(Long id) {
        final Optional<UserEntity> optionalTaskEntity = userRepository.findById(id);
        if(optionalTaskEntity.isPresent()){
            return userDTOAdapter.toDTO(optionalTaskEntity.get());
        }else{
            throw new UserNotFoundException("Task with id "+id+" not found");
        }
    }


    @Override
    public void saveUser(UserDTO userDTO) {
        try {
            final UserEntity userEntity = userEntityAdapter.toModel(userDTO);
            userRepository.save(userEntity);
        } catch(RuntimeException re){
            throw re;
        }
    }

    @Override
    public void updateUser(UserDTO userDTO) {
        
        try {
            final Optional<UserEntity> optionalUserEntity = userRepository.findById(userDTO.getId());
            if (optionalUserEntity.isPresent()) {
                UserEntity userEntity = optionalUserEntity.get();
                userEntity.setName(userDTO.getName());
                userEntity.setCpf(userDTO.getCpf());
                userEntity.setEmail(userDTO.getEmail());
                userRepository.save(userEntity);
            } else {
                throw new UserNotFoundException("User with id"+userDTO.getId()+" not found");
            }
        }catch(final RuntimeException re) {
            throw re;
        }
    }

    @Override
    public void deleteUser(Long id) throws Exception {
        UserEntity userEntity = userRepository.findById(id).
                orElseThrow(()-> new Exception("Vehicle Not Found"));
        userRepository.delete(userEntity);
    }

    @Override
    public List<UserDTO> getUserList(){
        return userRepository.findAll()
                .stream()
                .map(userDTOAdapter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<UserDTO> getUserListPaginated(int pageNo, int pageSize) {
        List<UserDTO> userDTOList;
        Page<UserDTO> page;
        userDTOList = getUserList();
        pageNo = 1;



        if(!userDTOList.isEmpty()){
            if(userDTOList.size() < pageSize){
                pageSize = userDTOList.size();
            }
            Pageable pageable = PageRequest.of(pageNo -1, pageSize);
            int start = (int)pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), userDTOList.size());
            List<UserDTO> subList = userDTOList.subList(start, end);
            page = new PageImpl<>(subList, pageable, userDTOList.size());
        }else{
            page = Page.empty();
        }

        return page;
    }
}
