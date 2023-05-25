package obniavka.timemanagment.utils;

import obniavka.timemanagment.data.User;
import obniavka.timemanagment.domain.*;
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
        SickLeaveDto sickLeaveDto = new SickLeaveDto();
        TaskDto taskDto = new TaskDto();
        AssignmentDto assignmentDto = new AssignmentDto();
        InvoiceDto invoiceDto = new InvoiceDto();

        invoiceDto.setId(1L);
        invoiceDto.setPdfData(new byte[]{1,2,44,63,2});
        invoiceDto.setUser(userDto);

        vacationDto.setId(1L);
        vacationDto.setBegin(LocalDate.of(2022, Month.AUGUST, 24));
        vacationDto.setCreated(LocalDateTime.now());
        vacationDto.setConfirmed(false);
        vacationDto.setEnd(LocalDate.now());
        vacationDto.setUser(userDto);

        reportDto.setId(1L);
        reportDto.setBegin(LocalTime.of(9, 30, 0));
        reportDto.setEnd(LocalTime.of(18, 0, 0));
        reportDto.setWorkDay(LocalDate.of(2023,Month.AUGUST, 11));
        reportDto.setPause(LocalTime.of(1,0));
        reportDto.setDescription("Work hard");
        reportDto.setUser(userDto);

        sickLeaveDto.setId(1L);
        sickLeaveDto.setBegin(LocalDate.of(2022, Month.AUGUST, 24));
        sickLeaveDto.setCreated(LocalDateTime.now());
        sickLeaveDto.setConfirmed(false);
        sickLeaveDto.setEnd(LocalDate.now());
        sickLeaveDto.setDescription("I am very sick");
        sickLeaveDto.setUser(userDto);

        taskDto.setId(1L);
        taskDto.setName("Create project");
        taskDto.setDescription("Start Intellij Idea");
        taskDto.setDeadline(LocalDate.of(2023, Month.AUGUST, 30));
        taskDto.setUser(userDto);

        assignmentDto.setId(1L);
        assignmentDto.setBegin(LocalDate.of(2023, Month.JUNE, 11));
        assignmentDto.setEnd(LocalDate.of(2023, Month.JUNE, 21));
        assignmentDto.setCountry("Ukraine");
        assignmentDto.setCity("Lviv");
        assignmentDto.setDescription("Project where you have to be placed");
        assignmentDto.setUsers(Set.of(userDto));

        userDto.setId(2L);
        userDto.setCity("New York");
        userDto.setCountry("USA");
        userDto.setEmail("blala@example.com");
        userDto.setFirstName("John");
        userDto.setHouseNumber("3a");
        userDto.setImageUrl(new byte[]{1,55,3});
        userDto.setLastName("Smith");
        userDto.setPassword("ienbf781;/");
        userDto.setPhoneNumber("1234567890");
        userDto.setPostCode("231f2");
        userDto.setRole("ROLE_ADMIN");
        userDto.setStreet("Sunny");
        userDto.setVacationDays(30);
        userDto.setSickleaveDays(30);
        userDto.setSalaryPerHour(20);
        userDto.setPosition("Developer");
        userDto.setPatronymic("Bohdanovych");
        userDto.setTaxNumber("1234133");
        userDto.setAccount("3243314342114153");
        userDto.setBeneficiaryBank("PRIVAT");
        userDto.setSwiftCode("XX83YU");
        userDto.setCurrency("EUR");
        userDto.setBirth(LocalDate.of(1999, Month.AUGUST, 22));
        userDto.setHired(LocalDate.of(2022, Month.APRIL, 10));
        userDto.setVacations(Set.of(vacationDto));
        userDto.setReports(Set.of(reportDto));
        userDto.setSickLeaves(Set.of(sickLeaveDto));
        userDto.setTasks(Set.of(taskDto));
        userDto.setAssignments(Set.of(assignmentDto));
        userDto.setInvoices(Set.of(invoiceDto));


        User user = UserMapper.MAPPER.map(userDto);

        Assertions.assertNotNull(user);
        Assertions.assertEquals(1, user.getVacations().size());
        Assertions.assertEquals(1, user.getReports().size());
        Assertions.assertEquals(1, user.getSickLeaves().size());
        Assertions.assertEquals(1, user.getTasks().size());
        Assertions.assertEquals(1, user.getAssignments().size());
        Assertions.assertEquals(1, user.getInvoices().size());
        Assertions.assertEquals("ROLE_ADMIN", user.getRole());
        Assertions.assertEquals(userDto.getPosition(), user.getPosition());
    }
}
