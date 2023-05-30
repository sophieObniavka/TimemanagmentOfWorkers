package obniavka.timemanagment.utils;

import obniavka.timemanagment.data.Task;
import obniavka.timemanagment.data.User;
import obniavka.timemanagment.domain.TaskDto;
import obniavka.timemanagment.domain.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskMapper MAPPER = Mappers.getMapper(TaskMapper.class);

    TaskDto map(final Task task);

    Task map(final TaskDto taskDto);

    List<TaskDto> map(final List<Task> tasks);

    @Mapping(target = "vacations", ignore = true)
    @Mapping(target = "reports", ignore = true)
    @Mapping(target = "sickLeaves", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "assignments", ignore = true)
    @Mapping(target = "invoices", ignore = true)
    @Mapping(target = "expenses", ignore = true)
    UserDto map(final User user);

    @Mapping(target = "vacations", ignore = true)
    @Mapping(target = "reports", ignore = true)
    @Mapping(target = "sickLeaves", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "assignments", ignore = true)
    @Mapping(target = "invoices", ignore = true)
    @Mapping(target = "expenses", ignore = true)
    User map(UserDto userDto);
}
