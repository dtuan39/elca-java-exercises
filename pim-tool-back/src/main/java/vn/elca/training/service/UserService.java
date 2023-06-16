package vn.elca.training.service;

import vn.elca.training.model.entity.User;

import java.util.List;

/**
 * @author gtn
 */
public interface UserService {
    User findOne(Long id);

    User findOne(String username);

    User addTasksToUser(List<Long> taskIds, String username);

    User update(User user);
}
