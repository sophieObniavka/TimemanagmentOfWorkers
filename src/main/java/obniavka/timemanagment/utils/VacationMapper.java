package obniavka.timemanagment.utils;

import obniavka.timemanagment.data.User;
import obniavka.timemanagment.data.Vacation;
import obniavka.timemanagment.domain.UserDto;
import obniavka.timemanagment.domain.VacationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VacationMapper {

    VacationDto map(final Vacation vacation);

    Vacation map(VacationDto vacationDto);

    List<VacationDto> map(final List<Vacation> vacations);

    @Mapping(target = "vacations", ignore = true)
    UserDto map(final User user);

    @Mapping(target = "vacations", ignore = true)
    User map(UserDto userDto);
}
