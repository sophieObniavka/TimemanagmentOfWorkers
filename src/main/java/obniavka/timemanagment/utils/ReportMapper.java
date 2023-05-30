package obniavka.timemanagment.utils;

import obniavka.timemanagment.data.Report;
import obniavka.timemanagment.data.User;
import obniavka.timemanagment.domain.ReportDto;
import obniavka.timemanagment.domain.TaskDto;
import obniavka.timemanagment.domain.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.scheduling.config.Task;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReportMapper {

    ReportMapper MAPPER = Mappers.getMapper(ReportMapper.class);
    ReportDto map(final Report report);

    Report map(final ReportDto reportDto);

    List<ReportDto> map(final List<Report> reports);

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

    TaskDto map(final Task task);

    Task map(final TaskDto taskDto);
}
