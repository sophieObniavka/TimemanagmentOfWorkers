package obniavka.timemanagment.services;

import obniavka.timemanagment.domain.TaskDto;
import obniavka.timemanagment.domain.UserDto;
import obniavka.timemanagment.repository.TaskRepository;
import obniavka.timemanagment.utils.TaskMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
}
