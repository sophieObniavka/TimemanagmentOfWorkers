package obniavka.timemanagment.services;

import obniavka.timemanagment.data.Assignment;
import obniavka.timemanagment.data.User;
import obniavka.timemanagment.domain.AssignmentDto;
import obniavka.timemanagment.domain.UserDto;
import obniavka.timemanagment.repository.AssignmentRepository;
import obniavka.timemanagment.repository.UserRepository;
import obniavka.timemanagment.utils.AssignmentMapper;
import obniavka.timemanagment.utils.UserMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AssignmentService {
    final AssignmentRepository assignmentRepository;
    final UserRepository userRepository;
    private AssignmentMapper mapper = Mappers.getMapper(AssignmentMapper.class);
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    public AssignmentService(AssignmentRepository assignmentRepository, UserRepository userRepository) {
        this.assignmentRepository = assignmentRepository;
        this.userRepository = userRepository;
    }

    public Page<AssignmentDto> fetchAllAssignmentsByUser(final UserDto userDto, Pageable pageable){
       return assignmentRepository.findAssignmentByUsersContains(mapper.map(userDto), pageable).map(mapper::map);
    }

    public Page<AssignmentDto> fetchAllAssignmentsFromDB(String keyword, Pageable pageable){
        if(keyword == null || keyword.isEmpty()){
            return assignmentRepository.findAll(pageable).map(mapper::map);
        }

        return assignmentRepository.findAssignmentByKeyword(keyword,pageable).map(mapper::map);
    }

    @Transactional
    public AssignmentDto persistAsignment(final AssignmentDto assignmentDto, final List<UserDto> userDtos){
            Assignment assignment = mapper.map(assignmentDto);

            Set<User> users = new HashSet<>();

            for (UserDto userDto : userDtos) {
                User user = userMapper.map(userDto);
                user.setReports(user.getReports());
                user.setVacations(user.getVacations());
                user.setSickLeaves(user.getSickLeaves());
                user.setAssignments(user.getAssignments());
                user.setTasks(user.getTasks());
                users.add(user);
            }

            assignment.setUsers(users);


            assignmentRepository.save(assignment);

            return mapper.map(assignment);

    }

    public void dropAssignmentFromDB(final Long id){
        assignmentRepository.deleteById(id);
    }
}
