package obniavka.timemanagment.utils;

import obniavka.timemanagment.data.Report;
import obniavka.timemanagment.domain.ReportDto;
import obniavka.timemanagment.domain.TaskDto;
import obniavka.timemanagment.domain.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

@ExtendWith(MockitoExtension.class)
public class ReportMapperTest {

    @InjectMocks
    ReportMapper reportMapper = Mappers.getMapper(ReportMapper.class);

    @Test
    @DisplayName("Test mapping DTO")
    void testMappingDto(){
        ReportDto reportDto = new ReportDto();
        UserDto userDto = new UserDto();
        TaskDto taskDto = new TaskDto();

        userDto.setId(1L);

        taskDto.setId(1L);

        reportDto.setId(1L);
        reportDto.setBegin(LocalTime.of(9, 0));
        reportDto.setEnd(LocalTime.of(18,0));
        reportDto.setPause(LocalTime.of(1,0));
        reportDto.setWorkDay(LocalDate.of(2023, Month.JANUARY,12));
        reportDto.setDescription("Hello");
        reportDto.setUser(userDto);
        reportDto.setTask(taskDto);
        Report report = reportMapper.map(reportDto);

        assertNotNull(report);
        assertNotNull(report.getUser());
        assertNotNull(report.getTask());

        assertEquals(reportDto.getId(), report.getId());
        assertEquals(reportDto.getWorkDay(), report.getWorkDay());
        assertEquals(reportDto.getEnd(), report.getEnd());
        assertEquals(reportDto.getPause(), report.getPause());
        assertEquals(reportDto.getDescription(), report.getDescription());

        Assertions.assertEquals(LocalTime.of(18,0), report.getEnd());
    }
}
