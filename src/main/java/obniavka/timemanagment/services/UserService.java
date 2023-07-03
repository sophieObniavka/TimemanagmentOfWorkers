package obniavka.timemanagment.services;

import io.micrometer.core.instrument.util.StringUtils;
import obniavka.timemanagment.data.User;
import obniavka.timemanagment.domain.UserDto;
import obniavka.timemanagment.helper.AmountOfDays;
import obniavka.timemanagment.repository.UserRepository;
import obniavka.timemanagment.utils.ReportMapper;
import obniavka.timemanagment.utils.UserMapper;
import org.mapstruct.factory.Mappers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.*;



@Service
public class UserService  implements UserDetailsService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    AmountOfDays amountOfDays = new AmountOfDays();

    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }

    UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    public UserDto findUserById(final Long userId){
        Optional<User> user = userRepository.findById(userId);
        UserDto userDto = userMapper.map(user.orElse(null));
        if(Objects.requireNonNull(userDto.getImageUrl()).length != 0) {
            userDto.setConvertedImage(Base64.getEncoder().encodeToString(userDto.getImageUrl()));
        }
        return userDto;
    }
    public UserDto findUserByIdForProfile(final Long userId){
        Optional<User> user = userRepository.findById(userId);
        UserDto userDto = userMapper.map(user.orElse(null));
        if(Objects.requireNonNull(userDto.getImageUrl()).length != 0) {
            userDto.setConvertedImage(Base64.getEncoder().encodeToString(userDto.getImageUrl()));
        }

        userDto.setTakenSickLeaves(userDto.getSickLeaves().stream().filter(s-> s.getConfirmed()!= null && s.getConfirmed())
                .mapToInt(s -> amountOfDays.getWorkingDaysBetweenTwoDates(s.getBegin(), s.getEnd()))
                .sum());

        userDto.setTakenVacations(userDto.getVacations().stream().filter(v-> v.getConfirmed()!= null && v.getConfirmed())
                .mapToInt(v -> amountOfDays.getWorkingDaysBetweenTwoDates(v.getBegin(), v.getEnd()))
                .sum());

        userDto.setVacationsAtOwnExpense(userDto.getVacations().stream().filter(v-> v.getConfirmed()!= null && v.getConfirmed() && v.isAtOwnExpense())
                .mapToInt(v -> amountOfDays.getWorkingDaysBetweenTwoDates(v.getBegin(), v.getEnd()))
                .sum());

        userDto.setSickLeavesAtOwnExpense(userDto.getSickLeaves().stream().filter(s-> s.getConfirmed()!= null && s.getConfirmed() && s.isAtOwnExpense())
                .mapToInt(s -> amountOfDays.getWorkingDaysBetweenTwoDates(s.getBegin(), s.getEnd()))
                .sum());
        return userDto;
    }

    public UserDto persistUserInDb(final UserDto userDto){
        User saved = userRepository.findById(userDto.getId()).orElse(null);
        User user = userMapper.map(userDto);


        if (user.getId() != null) {

            if (saved != null) {
                user.setTasks(saved.getTasks());
                user.setReports(saved.getReports());
                user.setVacations(saved.getVacations());
                user.setSickLeaves(saved.getSickLeaves());
                user.setAssignments(saved.getAssignments());

                if (StringUtils.isBlank(userDto.getPassword())) {
                    user.setPassword(saved.getPassword());
                } else {
                    user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
                }
            }
        } else {
            user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        }


        return userMapper.map(userRepository.save(user));
    }

    public void dropUserFromDb(final Long userId){
        userRepository.deleteById(userId);
    }



    public UserDto findUserByEmail(String email){
        return userMapper.map(userRepository.findUserByEmail(email).orElse(null));
    }

    public List<UserDto> fetchAllUsersFromDb(){
        List<UserDto> users = userMapper.map(userRepository.findAll());
        users.stream().filter(u-> u.getImageUrl()!=null).forEach(user -> user.setConvertedImage(Base64.getEncoder().encodeToString(user.getImageUrl())));
        return users;
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        return findUserByEmail(s);
    }

    @Transactional
    public Page<UserDto> fetchAllUserFromDb(String name, Pageable pageable){
        Page<UserDto> users;

        if (name == null || name.isEmpty()) {
            users=userRepository.findAll(pageable).map(userMapper::map);
        } else {
            users=userRepository.findUserByKeyword(name, pageable).map(userMapper::map);

        }
        users.filter(u-> Objects.requireNonNull(u.getImageUrl()).length != 0).forEach(user -> user.setConvertedImage(Base64.getEncoder().encodeToString(user.getImageUrl())));
        return users;
    }

    public Page<UserDto> fetchAllUserWithUnprocessedExpenses(Pageable pageable){
        return userRepository.findUsersWithExpensesToProcess(pageable).map(userMapper::map);
    }

    public boolean checkIfEmailAlreadyExist(UserDto user, String email){
        UserDto found =  findUserByEmail(email);

        return found != null && !(Objects.equals(found.getId(), user.getId())) ;
    }

    public List<UserDto> getAllUsersByIds(String userIds){
        Set<Long> ids = new HashSet<>();
        String[] strArr = userIds.split(",");
        for (String s : strArr) {
            ids.add(Long.parseLong(s.trim()));
        }
        return userMapper.map(userRepository.findAllById(ids));
    }
}
