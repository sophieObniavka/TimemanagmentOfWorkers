package obniavka.timemanagment.controller;

import lombok.RequiredArgsConstructor;
import obniavka.timemanagment.domain.TaskDto;
import obniavka.timemanagment.domain.UserDto;
import obniavka.timemanagment.services.TaskService;
import obniavka.timemanagment.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static obniavka.timemanagment.utils.Constants.*;
import static obniavka.timemanagment.controller.helper.ModelConstants.*;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public class TaskController {
    private final TaskService taskService;
    private final UserService userService;

    @GetMapping("/tasks")
    public String allTasks(final Model model, @RequestParam("page") Optional<Integer> page, @RequestParam(required = false) String keyword,@ModelAttribute("current") UserDto user){
        UserDto userDto = userService.findUserByEmail(user.getEmail());

        Page<TaskDto> tasks = taskService.fetchAllTasksFromDBByUser(userDto,keyword, PageRequest.of(page.orElse(1) - 1, 10));
        paginationModel(model, TASKS, page, tasks, keyword);
        return TASKS;
    }

    @PostMapping("/tasks/{id}")
    public String updateTask(@PathVariable String id, @RequestParam("status") String status) {
        TaskDto task = taskService.findTaskById(Long.parseLong(id));
        if (status.equals("false")) {
            task.setIsDone(false);
        } else if (status.equals("true")) {
            task.setIsDone(true);
        } else {
            task.setIsDone(null);
        }

        taskService.persistTaskInDb(task);

        return "redirect:/tasks";
    }
}
