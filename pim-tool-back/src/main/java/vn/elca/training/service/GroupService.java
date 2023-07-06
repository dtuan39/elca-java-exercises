package vn.elca.training.service;

import vn.elca.training.model.dto.GroupDto;

import java.util.List;

public interface GroupService {
    List<GroupDto> findAll();

}
