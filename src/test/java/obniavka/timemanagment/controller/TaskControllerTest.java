package obniavka.timemanagment.controller;

import obniavka.timemanagment.domain.TaskDto;
import obniavka.timemanagment.services.TaskService;
import obniavka.timemanagment.services.UserService;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import java.util.List;

@Import(UserDetailsTestConfig.class)
@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @MockBean
    private TaskService taskService;
    @MockBean
    private UserService userService;

    private List<TaskDto> tasks;

}
