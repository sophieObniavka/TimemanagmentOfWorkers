package obniavka.timemanagment.utils;

import obniavka.timemanagment.data.User;
import obniavka.timemanagment.domain.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    UserDto map(final User user);
    User map(UserDto userDto);
    List<UserDto> map(final List<User> users);
}
