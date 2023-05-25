package obniavka.timemanagment.utils;

import obniavka.timemanagment.data.Report;
import obniavka.timemanagment.data.User;
import obniavka.timemanagment.data.Vacation;
import obniavka.timemanagment.domain.ReportDto;
import obniavka.timemanagment.domain.UserDto;
import obniavka.timemanagment.domain.VacationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VacationMapper {
    VacationMapper MAPPER = Mappers.getMapper(VacationMapper.class);

    VacationDto map(final Vacation vacation);

    Vacation map(VacationDto vacationDto);

    List<VacationDto> map(final List<Vacation> vacations);

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
    User map(UserDto userDto);
}
