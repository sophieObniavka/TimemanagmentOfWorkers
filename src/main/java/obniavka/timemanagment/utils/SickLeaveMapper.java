package obniavka.timemanagment.utils;

import obniavka.timemanagment.data.SickLeave;
import obniavka.timemanagment.data.User;
import obniavka.timemanagment.domain.SickLeaveDto;
import obniavka.timemanagment.domain.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SickLeaveMapper {
    SickLeaveMapper MAPPER = Mappers.getMapper(SickLeaveMapper.class);

    SickLeaveDto map(final SickLeave sickLeave);

    SickLeave map(final SickLeaveDto sickLeaveDto);

    List<SickLeaveDto> map(final List<SickLeave> sickLeaves);

    @Mapping(target = "vacations", ignore = true)
    @Mapping(target = "reports", ignore = true)
    @Mapping(target = "sickLeaves", ignore = true)
    UserDto map(final User user);

    @Mapping(target = "vacations", ignore = true)
    @Mapping(target = "reports", ignore = true)
    @Mapping(target = "sickLeaves", ignore = true)
    User map(UserDto userDto);
}
