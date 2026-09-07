package com.bank.userservice.service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bank.userservice.dto.UserDto;
import com.bank.userservice.entity.User;
import com.bank.userservice.exception.ResourceNotFoundException;
import com.bank.userservice.exception.UserAleradyRegisteredException;
import com.bank.userservice.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService{

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public List<UserDto> getAllUserList(){

        List<User> userList = userRepository.findAll();
        return 
        userList
        .stream()
        .map(user -> convertEntityToDto(user))
        .collect(Collectors.toList());
    }

    public UserDto getUser(Long id){
        return convertEntityToDto(
            userRepository
            .findById(id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Resource not found for id: "+id)));
    }

    public UserDto registerUser(UserDto userDto){

        if(userRepository.existsByEmail(userDto.getEmail())){
                throw new UserAleradyRegisteredException(
                    "This User is Alerady Registered with Email id: "+userDto.getEmail());
        }
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User SavedUser = userRepository.save(convertDtoToEntity(userDto));
        return convertEntityToDto(SavedUser);
    }

    public void removeUser(Long id){
        if(userRepository.existsById(id)) {
            userRepository.deleteById(id);
        }else{
            throw new ResourceNotFoundException("Resource not found for id: "+id);
        }
    }

    public UserDto updateAllDetailsUser(Long id, UserDto userDto){
        if(!userRepository.existsById(id)){
            throw new ResourceNotFoundException("user not found with user id: "+userDto.getId());
        }
        userDto.setId(id);
        User SavedUser = userRepository.save(convertDtoToEntity(userDto));
        return convertEntityToDto(SavedUser);
    }

    public UserDto updatePartialDetailsUser(Long id, Map<String, Object> details){

        User user = 
        userRepository
        .findById(id)
        .orElseThrow(
            () -> new ResourceNotFoundException("user not found with id: "+id));
        details.forEach((key, value) ->{
            Field fieldToBeUpdated = ReflectionUtils.getRequiredField(User.class, key);
            fieldToBeUpdated.setAccessible(true);
            ReflectionUtils.setField(fieldToBeUpdated, user, value);
        });
        return convertEntityToDto(userRepository.save(user));
    }

    private UserDto convertEntityToDto(User user){
        return 
        modelMapper
        .map(user, UserDto.class);
    }

    private User convertDtoToEntity(UserDto userDto){
        return 
        modelMapper
        .map(userDto, User.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
       return 
       userRepository
       .findByEmail(username)
       .orElseThrow(
        () -> new BadCredentialsException("User with email "+ username +" not found"));
    }

    public User getUserById(Long id){
        return 
            userRepository
            .findById(id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Resource not found for id: "+id));
    }

}

