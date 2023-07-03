package obniavka.timemanagment.utils;

import obniavka.timemanagment.data.SickLeave;
import obniavka.timemanagment.domain.SickLeaveDto;
import obniavka.timemanagment.domain.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertNotNull;
@ExtendWith(MockitoExtension.class)
public class SickLeaveMapperTest {
    @InjectMocks
    SickLeaveMapper sickLeaveMapper = Mappers.getMapper(SickLeaveMapper.class);

    @Test
    @DisplayName("Test mapping DTO")
    void testMappingDto(){
        SickLeaveDto sickLeaveDto = new SickLeaveDto();
        UserDto userDto = new UserDto();

        userDto.setId(1L);

        sickLeaveDto.setId(1L);

        sickLeaveDto.setBegin(LocalDate.of(2023, Month.OCTOBER, 11));
        sickLeaveDto.setEnd(LocalDate.of(2023, Month.DECEMBER, 23));
        sickLeaveDto.setConfirmed(false);
        sickLeaveDto.setUser(userDto);
        sickLeaveDto.setAtOwnExpense(false);
        sickLeaveDto.setCreated(LocalDateTime.now());
        sickLeaveDto.setDescription("I'm having a headache");
        sickLeaveDto.setCheckedByUser("User User");
        sickLeaveDto.setCertificate_url(new byte[]{1,22,55});

        SickLeave sickLeave = sickLeaveMapper.map(sickLeaveDto);

        assertNotNull(sickLeave);
        assertNotNull(sickLeave.getUser());
    }
}
