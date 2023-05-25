package obniavka.timemanagment.utils;

import obniavka.timemanagment.data.Assignment;
import obniavka.timemanagment.domain.AssignmentDto;
import obniavka.timemanagment.domain.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class AssignmentMapperTest {

    @InjectMocks
    AssignmentMapper assignmentMapper = Mappers.getMapper(AssignmentMapper.class);

    @Test
    @DisplayName("Test mapping DTO")
    void testMappingDto(){
        AssignmentDto assignmentDto = new AssignmentDto();
        UserDto userDto = new UserDto();

        userDto.setId(1L);
        userDto.setFirstName("Lala");
        userDto.setLastName("Last");

        assignmentDto.setId(1L);
        assignmentDto.setName("Business Trip to Ukraine");
        assignmentDto.setBegin(LocalDate.of(2023, Month.JUNE, 11));
        assignmentDto.setEnd(LocalDate.of(2023, Month.JUNE, 21));
        assignmentDto.setCountry("Ukraine");
        assignmentDto.setCity("Lviv");
        assignmentDto.setDescription("Project where you have to be placed");
        assignmentDto.setUsers(Set.of(userDto));

        Assignment assignment = assignmentMapper.map(assignmentDto);

        assertNotNull(assignment);
        assertEquals(1, assignment.getUsers().size());
        assertEquals("Lala", assignment.getUsers().iterator().next().getFirstName());
    }
}
