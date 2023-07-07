package pilotproject.Project_Management_ELCA.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pilotproject.Project_Management_ELCA.model.entity.Group;
import pilotproject.Project_Management_ELCA.repository.GroupRepository;
import pilotproject.Project_Management_ELCA.service.GroupService;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public List<Group> findAllGroups() {
        return groupRepository.findAll();
    }
}
