package vn.elca.training.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.elca.training.model.dto.ProjectDto;
import vn.elca.training.service.ProjectService;
import vn.elca.training.util.ApplicationMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gtn
 * @author thomas.dang
 */
@RestController
@RequestMapping("/projects")
public class ProjectController extends AbstractApplicationController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/search")
    public List<ProjectDto> search(@RequestParam String keyword) {
        if (keyword.isBlank()) {
            return new ArrayList<>();
        }
        return projectService.findAll()
                .stream()
                .filter(product -> product.getName().trim().toLowerCase()
                        .contains(keyword.trim().toLowerCase()))
                .map(mapper::projectToProjectDto)
                .collect(Collectors.toList());
    }
}
