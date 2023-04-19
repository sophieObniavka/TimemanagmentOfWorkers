package obniavka.timemanagment.services;

import obniavka.timemanagment.data.User;
import obniavka.timemanagment.domain.UserDto;
import obniavka.timemanagment.repository.UserRepository;
import obniavka.timemanagment.utils.UserMapper;
import org.mapstruct.factory.Mappers;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;


@Service
public class UserService  implements UserDetailsService{


    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }

    UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    public UserDto findUserById(final Long userId){
        Optional<User> user = userRepository.findById(userId);
        UserDto userDto = userMapper.map(user.orElse(null));
        userDto.setConvertedImage(Base64.getEncoder().encodeToString(userDto.getImageUrl()));
        return userDto;
    }

    public UserDto persistUserInDb(final UserDto userDto){

        User user = userMapper.map(userDto);
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        user.setPasswordExpires(LocalDate.of(2024, 12, 11));

        return userMapper.map(userRepository.save(userMapper.map(userDto)));
    }

    public void dropUserFromDb(final Long userId){
        userRepository.deleteById(userId);
    }

    public void updatePassword(final String password, final Long id){

        String res = bCryptPasswordEncoder.encode(password);
        userRepository.updateUserPassword(res, id);
    }
    public UserDto findUserByEmail(String email){
        return userMapper.map(userRepository.findUserByEmail(email).orElse(null));
    }

    public List<UserDto> fetchAllUsersFromDb(){
        List<UserDto> users = userMapper.map(userRepository.findAll());
        users.stream().forEach(user -> user.setConvertedImage(Base64.getEncoder().encodeToString(user.getImageUrl())));
        return users;
    }

    public void updateUser(UserDto user){
        userRepository.updateUser(
                user.getId(),user.getFirstName(),user.getLastName(),user.getBirth(),user.getHired(),user.getPhoneNumber(),user.getEmployeeId(),user.getRole(),user.getCountry(),user.getCity(),user.getStreet(),user.getHouseNumber(), user.getPostCode(),user.getEmail(), user.getImageUrl(), user.getVacationDays(), user.getSickleaveDays(), user.getVacationDays());
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        return findUserByEmail(s);
    }

}
