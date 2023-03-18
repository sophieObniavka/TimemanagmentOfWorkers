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
public interface UserMapper {

    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    UserDto map(final User user);
    User map(UserDto userDto);
    List<UserDto> map(final List<User> users);

    @Mapping(target = "user", ignore = true)
    VacationDto map(final Vacation vacation);

    @Mapping(target = "user", ignore = true)
    Vacation map(final VacationDto vacation);

    @Mapping(target = "user", ignore = true)
    ReportDto map(final Report report);

    @Mapping(target = "user", ignore = true)
    Report map(final ReportDto report);
}
