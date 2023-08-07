package obniavka.timemanagment.controller;

import obniavka.timemanagment.domain.ReportDto;
import obniavka.timemanagment.domain.TaskDto;
import obniavka.timemanagment.domain.UserDto;
import obniavka.timemanagment.domain.VacationDto;
import obniavka.timemanagment.services.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@Import(UserDetailsTestConfig.class)
@WebMvcTest(AdminController.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TaskService taskService;
    @MockBean
    private UserService userService;
    @MockBean
    private VacationService vacationService;
    @MockBean
    private SickLeavesService sickLeavesService;
    @MockBean
    private ReportService reportService;
    @MockBean
    private AssignmentService assignmentService;
    @MockBean
    private InvoiceService invoiceService;
    @MockBean
    private ExpenseService expenseService;
    @MockBean
    private ReceiptService receiptService;


    private TaskDto taskDto;
    private ReportDto reportDto;
    private UserDto userDto;
    private VacationDto vacationDto;
    private Page<ReportDto> reportDtoPage;
    private Page<UserDto> usersDtoPage;
    private Page<TaskDto> tasksDtoPage;
    private Page<VacationDto> vacationsPage;

    @BeforeEach
    void setUp(){
        userDto = UserDto.builder()
                .email("hallo@gmai.oc")
                .firstName("First")
                .lastName("Last")
                .email("email")
                .imageUrl(new byte[]{1,2,33,3})
                .position("Developer")
                .country("Ukraine")
                .city("Lviv")
                .street("street")
                .houseNumber("12f")
                .postCode("1113q1")
                .build();

        taskDto = TaskDto.builder()
                .name("finish")
                .deadline(LocalDate.parse("2023-05-22"))
                .description("work")
                .isDone(false)
                .build();

        reportDto = ReportDto.builder()
                .workDay(LocalDate.of(2023, Month.JANUARY,12))
                .begin(LocalTime.of(9,0))
                .end(LocalTime.of(18,0))
                .description("Tested")
                .user(UserDto.builder().firstName("First").lastName("Last").build())
                .build();

        vacationDto = VacationDto.builder()
                .begin(LocalDate.of(2023, Month.AUGUST,2))
                .end(LocalDate.of(2023, Month.AUGUST, 24))
                .created(LocalDateTime.of(2023,Month.JULY,27, 12,10))
                .user(userDto)
                .atOwnExpense(false).build();

        reportDtoPage = new PageImpl<>(List.of(reportDto));
        usersDtoPage = new PageImpl<>(List.of(userDto));
        tasksDtoPage = new PageImpl<>(List.of(taskDto));
        vacationsPage = new PageImpl<>(List.of(vacationDto));

        when(userService.findUserByEmail(anyString())).thenReturn(userDto);
    }

    @Test
    @DisplayName("Save task")
    @WithUserDetails("admin")
    void persistTask() throws Exception {
     mockMvc.perform(post("/admin/tasks")
                .param("name", taskDto.getName())
                .param("description", taskDto.getDescription())
                .param("deadline", taskDto.getDeadline()==null?"":taskDto.getDeadline().toString())
                .param("isDone", taskDto.getIsDone().toString())
                .with(SecurityMockMvcRequestPostProcessors.csrf())
               ).
                andExpect(status().isFound());
    }

    @Test
    @DisplayName("Get reports from all users")
    @WithUserDetails("admin")
    void getReports() throws Exception {
        when(reportService.fetchAllReportsFromDBByDate(anyString(), any(Pageable.class))).thenReturn(reportDtoPage);

        mockMvc.perform(get("/admin/report").with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("current"))
                .andExpect(model().attribute("current", userDto));
    }

    @Test
    @DisplayName("Get reports from all users with selected month")
    @WithUserDetails("admin")
    void getReportsWithSelectedMonth() throws Exception {


        when(reportService.fetchAllReportsFromDBByDate(anyString(), any(Pageable.class))).thenReturn(reportDtoPage);

        mockMvc.perform(get("/admin/report/selectedDate/2023-05").with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("current"))
                .andExpect(model().attribute("current", userDto))
                .andExpect(model().attribute("selected", "2023-05"))
                .andExpect(model().attribute("reports", List.of(reportDto)));
    }

//    @Test
//    @DisplayName("Get all users")
//    @WithUserDetails("admin")
//    void getAllUsers() throws Exception {
//
//        when(userService.fetchAllUserFromDb(anyString(), any(Pageable.class))).thenReturn(usersDtoPage);
//
//        mockMvc.perform(get("/admin/users").with(SecurityMockMvcRequestPostProcessors.csrf()))
//                .andExpect(status().isOk())
//                .andExpect(model().attributeExists("current"))
//                .andExpect(model().attribute("current", userDto))
//                .andExpect(model().attribute("users", List.of(userDto)));
//    }

//    @Test
//    @DisplayName("Get all tasks")
//    @WithUserDetails("admin")
//    void getAllTasks() throws Exception {
//
//        when(taskService.fetchAllTasksFromDB(anyString(), any(Pageable.class))).thenReturn(tasksDtoPage);
//
//        mockMvc.perform(get("/admin/tasks").with(SecurityMockMvcRequestPostProcessors.csrf()))
//                .andExpect(status().isOk())
//                .andExpect(model().attributeExists("current"))
//                .andExpect(model().attribute("current", userDto))
//                .andExpect(model().attribute("users", List.of(userDto)));
//    }

    @Test
    @DisplayName("Get all active vacations")
    @WithUserDetails("admin")
    void getAllActiveVacations() throws Exception {

        when(vacationService.selectAllUnconfirmedVacations(any(Pageable.class))).thenReturn(vacationsPage);

        MvcResult result = mockMvc.perform(get("/admin/vacations/active").with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("current"))
                .andExpect(model().attribute("current", userDto))
                .andExpect(model().attribute("vacations", List.of(vacationDto))).andReturn();
        String expectedString = "2023-07-27 12:10";

        String responseContent = result.getResponse().getContentAsString();

        Assertions.assertTrue(responseContent.contains(expectedString));
    }
}
