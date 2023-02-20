package obniavka.timemanagment.utils;

import obniavka.timemanagment.data.Vacation;
import obniavka.timemanagment.domain.UserDto;
import obniavka.timemanagment.domain.VacationDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.time.Month;

@ExtendWith(MockitoExtension.class)
public class VacationMapperTest {

    @InjectMocks
    VacationMapper vacationMapper = Mappers.getMapper(VacationMapper.class);

    @Test
    @DisplayName("Test mapping DTO")
    void testMappingDto(){
        VacationDto vacationDto = new VacationDto();
        UserDto userDto = new UserDto();

        userDto.setId(1L);

        vacationDto.setId(1L);
        vacationDto.setBegin(LocalDate.of(2023, Month.OCTOBER, 11));
        vacationDto.setEnd(LocalDate.of(2023, Month.DECEMBER, 23));
        vacationDto.setArchived(false);
        vacationDto.setConfirmed(false);
        vacationDto.setUser(userDto);
        vacationDto.setAtOwnExpense(false);
        Vacation vacation = vacationMapper.map(vacationDto);

        assertNotNull(vacation);
        assertNotNull(vacation.getUser());

    }
}
