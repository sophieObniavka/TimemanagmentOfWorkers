package obniavka.timemanagment.utils;

import obniavka.timemanagment.data.User;
import obniavka.timemanagment.domain.ReportDto;
import obniavka.timemanagment.domain.UserDto;
import obniavka.timemanagment.domain.VacationDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Set;

public class UserMapperTest {
    @Test
    @DisplayName("Test mapping DTO")
    void testMappingDto(){
        UserDto userDto = new UserDto();
        VacationDto vacationDto = new VacationDto();
        ReportDto reportDto = new ReportDto();

        vacationDto.setId(1L);
        vacationDto.setBegin(LocalDate.of(2022, Month.AUGUST, 24));
        vacationDto.setCreated(LocalDateTime.now());
        vacationDto.setConfirmed(false);
        vacationDto.setArchived(true);
        vacationDto.setEnd(LocalDate.now());
        vacationDto.setUser(userDto);

        reportDto.setId(1L);
        reportDto.setBegin(LocalTime.of(9, 30, 0));
        reportDto.setEnd(LocalTime.of(18, 0, 0));
        reportDto.setWorkDay(LocalDate.of(2023,Month.AUGUST, 11));
        reportDto.setPause(LocalTime.of(1,0));
        reportDto.setDescription("Work hard");
        reportDto.setUser(userDto);

        userDto.setId(2L);
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
        userDto.setVacationDays(30);
        userDto.setPasswordExpired(false);
        userDto.setBirth(LocalDate.of(1999, Month.AUGUST, 22));
        userDto.setHired(LocalDate.of(2022, Month.APRIL, 10));
        userDto.setVacations(Set.of(vacationDto));
        userDto.setReports(Set.of(reportDto));


        User user = UserMapper.MAPPER.map(userDto);

        Assertions.assertNotNull(user);
        Assertions.assertEquals(1, user.getVacations().size());
        Assertions.assertEquals(1, user.getReports().size());
    }
}
