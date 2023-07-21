package vn.elca.training.web;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import vn.elca.training.model.dto.ErrorResponse;
import vn.elca.training.model.dto.ProjectDto;
import vn.elca.training.model.dto.ProjectRetrieveResponseDto;
import vn.elca.training.model.exception.*;
import vn.elca.training.service.ProjectService;

import java.time.LocalDateTime;

/**
 * @author thomas.dang
 */
@RestController
@RequestMapping("/projects")
@CrossOrigin(origins = "http://localhost:4200")
@RestControllerAdvice
public class ProjectsController extends AbstractApplicationController {

    private final Logger logger = LogManager.getLogger(ProjectsController.class);

    private final ProjectService projectService;

    @Autowired
    public ProjectsController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @ExceptionHandler(ResponseStatusException.class)
    //We implement a @ControllerAdvice globally but also ResponseStatusExceptions locally
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException ex) {
        HttpStatus httpStatus = ex.getStatus();
        String errorMessage = ex.getReason();

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(httpStatus.value());
        errorResponse.setError(httpStatus.getReasonPhrase());
        errorResponse.setMessage(errorMessage);
        errorResponse.setTimestamp(LocalDateTime.now());

        //Log the error
        logger.error("ResponseStatusException: HTTP {} {}",
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                ex);

        return ResponseEntity.status(httpStatus).body(errorResponse);
    }


    @PostMapping // POST /projects
    public ResponseEntity<ProjectDto> createProject(@RequestBody ProjectDto projectDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(projectService.create(projectDto));
        } catch (StartDateAfterEndDateException | IllegalArgumentException e) { //validation
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (ProjectNumberAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (GroupNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }


    @PutMapping // PUT /projects
    public ResponseEntity<ProjectDto> updateProject(@RequestBody ProjectDto projectDto) {
        ProjectDto response;
        try {
            response = projectService.update(projectDto);
        } catch (ProjectNotFoundException | GroupNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (StartDateAfterEndDateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (ConcurrentUpdateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}") // GET /projects/1
    public ResponseEntity<ProjectDto> findProjectByNumber(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(projectService.findById(id));
        } catch (ProjectNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }


    @GetMapping // GET /projects?page=0&limit=10
    public ResponseEntity<ProjectRetrieveResponseDto> getAll(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int limit) {

        Page<ProjectDto> result = projectService.findAllOrderByProjectNumber(page, limit);

        // Extract the total count of results
        long totalCount = result.getTotalElements();

        return ResponseEntity.ok().body(new ProjectRetrieveResponseDto(result.getContent(), totalCount));
    }


    @GetMapping("/search") // GET /projects/search?keyword=abc&status=NEW&page=0&limit=10
    public ResponseEntity<ProjectRetrieveResponseDto> search(@RequestParam String keyword,
                                                             @RequestParam String status,
                                                             @RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int limit) {
        if (StringUtils.isBlank(keyword) && StringUtils.isBlank(status)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Keyword and status are blank");
        }

        ProjectDto.StatusDto statusDto;

        try {
            statusDto = StringUtils.isBlank(status) ? null : ProjectDto.StatusDto.valueOf(status);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Status %s is invalid", status));
        }

        Page<ProjectDto> result = projectService.findByKeyword(keyword, statusDto, page, limit);

        // Extract the total count of results
        long totalCount = result.getTotalElements();

        return ResponseEntity.ok().body(new ProjectRetrieveResponseDto(result.getContent(), totalCount));
    }


    @DeleteMapping("/{id}") // DELETE /projects/1
    public ResponseEntity<ProjectDto> deleteProjectById(@PathVariable int id) {
        try {
            projectService.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (ProjectNotInNewStatusException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (ProjectNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
