package vn.elca.training.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.elca.training.model.dto.GroupDto;
import vn.elca.training.model.mapping.GroupMapper;
import vn.elca.training.repository.GroupRepository;
import vn.elca.training.service.GroupService;

import java.util.List;

@Service
@Primary
@Transactional
public class GroupServiceImpl implements GroupService {
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupMapper mapper;

    @Override
    public List<GroupDto> findAll() {
        return mapper.toDTOs(groupRepository.findAll());
    }

}
