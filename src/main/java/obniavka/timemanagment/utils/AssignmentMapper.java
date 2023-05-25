package obniavka.timemanagment.utils;

import obniavka.timemanagment.data.Assignment;
import obniavka.timemanagment.data.User;
import obniavka.timemanagment.domain.AssignmentDto;
import obniavka.timemanagment.domain.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AssignmentMapper {

    AssignmentMapper MAPPER = Mappers.getMapper(AssignmentMapper.class);

    AssignmentDto map(final Assignment assignment);
    Assignment map(final AssignmentDto assignmentDto);
    List<AssignmentDto> map(final List<Assignment> assignments);

    @Mapping(target = "vacations", ignore = true)
    @Mapping(target = "reports", ignore = true)
    @Mapping(target = "sickLeaves", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "assignments", ignore = true)
    @Mapping(target = "invoices", ignore = true)
    UserDto map(final User user);

    @Mapping(target = "vacations", ignore = true)
    @Mapping(target = "reports", ignore = true)
    @Mapping(target = "sickLeaves", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "assignments", ignore = true)
    @Mapping(target = "invoices", ignore = true)
    User map(final UserDto userDto);
}
