package com.hakke.ppmtool.web;

import java.util.List;

import javax.validation.Valid;

import org.apache.juli.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hakke.ppmtool.domain.Project;
import com.hakke.ppmtool.exceptions.ProjectIdException;
import com.hakke.ppmtool.services.MapValidationErrorService;
import com.hakke.ppmtool.services.ProjectService;

@RestController
@RequestMapping("/api/project")
@CrossOrigin(origins= {"http://localhost:3000"})
public class ProjectController {

	private static final Logger log = LoggerFactory.getLogger(ProjectController.class);

	@Autowired
	private ProjectService projectService;

	@Autowired
	private MapValidationErrorService errorService;

	@PostMapping("")
	public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result) {

		ResponseEntity<?> errorMap = errorService.mapValidationService(result);
		if (errorMap != null)
			return errorMap;
		projectService.saveOrUpdate(project);
		return new ResponseEntity<Project>(project, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Project> getProjectbyId(@PathVariable("id") String projectIdentifier) {

		Project project = projectService.findById(projectIdentifier);
		log.info("Project Id: " + project.getProjectIdentifier() + ", ProjctName: " + project.getProjectName());
		return new ResponseEntity<Project>(project, HttpStatus.OK);

	}

	@GetMapping("/all")
	public ResponseEntity<List<Project>> listAllProjects() {

		List<Project> projects = projectService.findAll();
		return new ResponseEntity<List<Project>>(projects, HttpStatus.OK);
	}

	@DeleteMapping("/{projectId}")
	public ResponseEntity<?> deleteProject(@PathVariable String projectId) {

		projectService.deleteProjectByIdentifier(projectId);
		return new ResponseEntity<String>("Project With Id: '" + projectId + "' Successfully Deleted!", HttpStatus.OK);

	}

}
