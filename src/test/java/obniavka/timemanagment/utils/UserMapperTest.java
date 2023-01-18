package obniavka.timemanagment.utils;

import obniavka.timemanagment.data.User;
import obniavka.timemanagment.domain.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserMapperTest {
    @Test
    @DisplayName("Test mapping DTO")
    void testMappingDto(){
        UserDto userDto = new UserDto();

        userDto.setId(2L);
        userDto.setAbout("About");
        userDto.setCity("New York");
        userDto.setCountry("USA");
        userDto.setEmail("blala@example.com");
        userDto.setEmployeeId("TT27Uk");
        userDto.setFirstName("John");
        userDto.setHouseNumber("3a");
        userDto.setImageUrl(new byte[]{1,55,3});
        userDto.setLastName("Smith");
        userDto.setPassword("ienbf781;/");
        userDto.setPhoneNumber("1234567890");
        userDto.setPostCode("231f2");
        userDto.setRole("designer");
        userDto.setStreet("Sunny");

        User user = UserMapper.MAPPER.map(userDto);

        Assertions.assertNotNull(user);
    }
}
