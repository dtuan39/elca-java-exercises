package vn.elca.training.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import vn.elca.training.model.dto.UserDto;
import vn.elca.training.model.entity.User;
import vn.elca.training.service.UserService;

import java.util.List;

/**
 * @author gtn
 */
@RestController
@RequestMapping("/users")
public class UserController extends AbstractApplicationController {

    @Autowired
    UserService userService;

    @GetMapping("/id/{id}")
    public User findOne(@PathVariable Long id) {
        return userService.findOne(id);
    }

    @GetMapping("/{username}")
    public UserDto findOne(@PathVariable String username) {
        User user = userService.findOne(username);
        return mapper.userToUserDto(user);
    }

    @PostMapping("/{username}/addTasks")
    public UserDto addTasks(@RequestBody List<Long> taskIds, @PathVariable String username) {
        if (CollectionUtils.isEmpty(taskIds)) {
            throw new IllegalArgumentException("Invalid request! List taskIds is empty");
        } else if (StringUtils.isBlank(username)) {
            throw new IllegalArgumentException("Invalid request! Username is blank");
        }

        User user = userService.addTasksToUser(taskIds, username);
        return mapper.userToUserDto(user);
    }

    @PutMapping({"/update"})
    public User update(@RequestBody User user) {
        if (user == null) {
            throw new IllegalArgumentException("Invalid request! User not found");
        }
        return userService.update(user);
    }

}
