package obniavka.timemanagment.services;

import obniavka.timemanagment.data.User;
import obniavka.timemanagment.domain.UserDto;
import obniavka.timemanagment.repository.UserRepository;
import obniavka.timemanagment.utils.UserMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    public UserDto findUserById(final Long userId){
        Optional<User> user = userRepository.findById(userId);
        return userMapper.map(user.isPresent()?user.get() : null);
    }

    public UserDto persistUserInDb(final UserDto userDto){
        User user = userRepository.save(userMapper.map(userDto));

        return userMapper.map(user);
    }

    public void dropUserFromDb(final Long userId){
        userRepository.deleteById(userId);
    }

    public List<UserDto> fetchAllUsersFromDb(){
        return userMapper.map(userRepository.findAll());
    }
}
