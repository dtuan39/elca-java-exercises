package vn.elca.training.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.elca.training.model.dto.GroupDto;
import vn.elca.training.repository.GroupRepository;
import vn.elca.training.service.GroupService;
import vn.elca.training.util.ApplicationMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Primary
@Transactional
public class GroupServiceImpl implements GroupService {
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ApplicationMapper mapper;
    @Override
    public List<GroupDto> findAll() {
        return groupRepository.findAll()
                .stream()
                .map(mapper::groupToGroupDto)
                .collect(Collectors.toList());
    }

}
