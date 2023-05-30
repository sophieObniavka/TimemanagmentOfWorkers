package obniavka.timemanagment.services;

import obniavka.timemanagment.data.Task;
import obniavka.timemanagment.data.User;
import obniavka.timemanagment.domain.TaskDto;
import obniavka.timemanagment.domain.UserDto;
import obniavka.timemanagment.repository.TaskRepository;
import obniavka.timemanagment.utils.TaskMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    TaskMapper taskMapper = Mappers.getMapper(TaskMapper.class);

    public TaskDto findTaskById(final Long taskId){
        return taskMapper.map(taskRepository.findById(taskId).orElse(null));
    }

    public TaskDto persistTaskInDb(final TaskDto taskDto){
        return taskMapper.map(taskRepository.save(taskMapper.map(taskDto)));
    }

    public Page<TaskDto> fetchAllTasksFromDB(String keyword, Pageable pageable){
        if(keyword == null || keyword.isEmpty()){
            return taskRepository.findAll(pageable).map(taskMapper::map);
        }

        return taskRepository.findTaskByKeyword(keyword, pageable).map(taskMapper::map);
    }

    public Page<TaskDto> fetchAllTasksFromDBByUser(final UserDto userDto,String keyword, Pageable pageable){
        if(keyword == null || keyword.isEmpty()){
            return taskRepository.findByUser(taskMapper.map(userDto),pageable).map(taskMapper::map);
        }

        return taskRepository.findByUser(taskMapper.map(userDto),keyword, pageable).map(taskMapper::map);
    }

    public List<TaskDto> fetchAllTasksBetweenYesterdayAndTomorrow(UserDto userDto){
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        LocalDate yesterday = LocalDate.now().minusDays(1);
        return taskMapper.map(taskRepository.findByDeadlineBetweenAndUser(yesterday,tomorrow,taskMapper.map(userDto)));
    }

    public void removeTaskById(Long id){
        Task task = taskRepository.findById(id).get();
        User user = task.getUser();
        user.getTasks().remove(task);
        taskRepository.deleteById(id);
    }
}
