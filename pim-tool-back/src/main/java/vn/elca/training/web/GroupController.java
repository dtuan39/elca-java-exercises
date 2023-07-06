package vn.elca.training.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.elca.training.model.dto.GroupDto;
import vn.elca.training.service.GroupService;

import java.util.List;

/**
 * @author thomas.dang
 */
@RestController
@RequestMapping("/groups")
@CrossOrigin(origins = "http://localhost:4200")
public class GroupController extends AbstractApplicationController {

    @Autowired
    private GroupService groupService;

    @GetMapping
    public List<GroupDto> getAllGroups() {
        return groupService.findAll();
    }
}
