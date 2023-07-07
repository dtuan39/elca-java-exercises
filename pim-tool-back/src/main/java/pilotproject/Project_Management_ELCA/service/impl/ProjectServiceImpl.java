package pilotproject.Project_Management_ELCA.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pilotproject.Project_Management_ELCA.model.dto.ProjectDto;
import pilotproject.Project_Management_ELCA.model.entity.Project;
import pilotproject.Project_Management_ELCA.model.exception.ProjectNumberExistedException;
import pilotproject.Project_Management_ELCA.repository.GroupRepository;
import pilotproject.Project_Management_ELCA.repository.ProjectRepository;
import pilotproject.Project_Management_ELCA.service.ProjectService;
import pilotproject.Project_Management_ELCA.util.ApplicationMapper;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
//@Transactional
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ApplicationMapper mapper;

    private final ProjectRepository projectRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public ProjectDto addProject(ProjectDto dto) throws ProjectNumberExistedException {
        Project project = mapper.projectDtoToProjectEntity(dto);
        project.setGroup(groupRepository.findById(dto.getGroupId()).orElseThrow());
        try {
            return mapper.projectToProjectDto(projectRepository.save(project));
        } catch (DataIntegrityViolationException e) {
            throw new ProjectNumberExistedException("The project number already existed. Please select a different project number");
        }
    }

    @Override
    public ProjectDto updateProject(ProjectDto dto) {
        Project entity = projectRepository.findProjectByNumber(dto.getNumber());
        entity.setCustomer(dto.getCustomer());
        entity.setName(dto.getName());
        entity.setStatus(Project.Status.valueOf(dto.getStatus().toString()));
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setGroup(groupRepository.findById(dto.getGroupId()).orElseThrow());

        return mapper.projectToProjectDto(projectRepository.save(entity));
    }

    @Override
    public List<Project> searchProject(String searchText, String status) {
        return projectRepository.findProjectBySearchTextAndStatus(searchText, status);
    }

    @Override
    public List<Project> findAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Project findProjectByNumber(Integer number) {
        return projectRepository.findProjectByNumber(number);
    }

    @Override
    @Transactional
    public void deleteSingleProject(Long id) {
        projectRepository.deleteProjectById(id);
    }


}
