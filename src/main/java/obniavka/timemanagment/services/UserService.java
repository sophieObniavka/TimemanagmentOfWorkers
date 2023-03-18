package obniavka.timemanagment.services;

import obniavka.timemanagment.data.User;
import obniavka.timemanagment.domain.UserDto;
import obniavka.timemanagment.repository.UserRepository;
import obniavka.timemanagment.utils.UserMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.repository.query.Param;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class UserService implements UserDetailsService{


    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    public UserDto findUserById(final Long userId){
        Optional<User> user = userRepository.findById(userId);
        return userMapper.map(user.orElse(null));
    }

    public UserDto persistUserInDb(final UserDto userDto){

        User user = userMapper.map(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setPasswordExpires(LocalDate.of(2024, 12, 11));

        return userMapper.map(userRepository.save(userMapper.map(userDto)));
    }

    public void dropUserFromDb(final Long userId){
        userRepository.deleteById(userId);
    }

    public void updatePassword(final String password, final Long id){

        String res = passwordEncoder.encode(password);
        userRepository.updateUserPassword(res, id);
    }
    public User findUserByEmail(String email){
        Optional<User> user = userRepository.findUserByEmail(email);
        if(user.isPresent()){
            user.get().setPassword(passwordEncoder.encode(user.get().getPassword()));
            return user.get();

        }
        return null;
    }

    public List<UserDto> fetchAllUsersFromDb(){
        return userMapper.map(userRepository.findAll());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByEmail(email);


        System.out.println("Pyzda");
        System.out.println(user.get().getPassword());
        System.out.println("Does it match? " + user.get().getPassword().matches("sofiia123"));
        System.out.println("Does it equal? " +  user.get().getPassword().equals("sofiia123"));

        if(user.isEmpty()) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        return new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getPassword(), mapRolesToAuthorities(List.of(user.get())));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<User> roles){
        return roles.stream().map(x-> new SimpleGrantedAuthority(x.getRole())).collect(Collectors.toList());
    }

    public void updateUser(Long id, String firstName, String lastName, LocalDate birth, LocalDate hired, String phoneNumber, String employeeId, String role, String country, String city, String street, String houseNumber, String postCode, String email, byte imageUrl[]){
        userRepository.updateUser(id,firstName,lastName,birth,hired,phoneNumber,employeeId,role,country,city,street,houseNumber, postCode,email, imageUrl);
    }


}
