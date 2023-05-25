package obniavka.timemanagment.utils;

import obniavka.timemanagment.data.Task;
import obniavka.timemanagment.domain.TaskDto;
import obniavka.timemanagment.domain.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TaskMapperTest {

    @InjectMocks
    TaskMapper taskMapper = Mappers.getMapper(TaskMapper.class);

    @Test
    @DisplayName("Test mapping DTO")
    void testMappingDto(){
        TaskDto taskDto = new TaskDto();
        UserDto userDto = new UserDto();

        userDto.setId(1L);

        taskDto.setId(1L);
        taskDto.setName("Go on vacation");
        taskDto.setDescription("Relax");
        taskDto.setDeadline(LocalDate.of(2023, Month.AUGUST,24));
        taskDto.setIsDone(false);
        taskDto.setUser(userDto);

        Task task = taskMapper.map(taskDto);

        assertNotNull(task);
        assertNotNull(task.getUser());

        assertEquals(taskDto.getId(), task.getId());
        assertEquals(taskDto.getName(), task.getName());
        assertEquals(taskDto.getDeadline(), task.getDeadline());
        assertEquals(taskDto.getDescription(), task.getDescription());
        assertEquals(taskDto.getIsDone(), task.getIsDone());


    }
}
