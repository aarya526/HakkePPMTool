package com.hakke.ppmtool.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hakke.ppmtool.domain.Backlog;
import com.hakke.ppmtool.domain.Project;
import com.hakke.ppmtool.exceptions.ProjectIdException;
import com.hakke.ppmtool.repository.BacklogRepository;
import com.hakke.ppmtool.repository.ProjectRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private BacklogRepository backlogRepository;

	public Project saveOrUpdate(Project project) {

		try {
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			if (project.getId() == null) {
				Backlog backlog = new Backlog();
				project.setBacklog(backlog);
				backlog.setProject(project);
				backlog.setProjectIdentifier(project.getProjectIdentifier());
			}

			if (project.getId() != null) {

				project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier()));
			}
			return projectRepository.save(project);

		} catch (Exception e) {

			throw new ProjectIdException(
					"Project ID: " + project.getProjectIdentifier().toUpperCase() + " already Exists!");
		}

	}

	public Project findById(String projectIdentifier) {

		Project project = projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());
		if (project == null) {

			throw new ProjectIdException("Project ID: '" + projectIdentifier + "' does not exists!");
		}
		return project;
	}

	public List<Project> findAll() {

		return (List<Project>) projectRepository.findAll();
	}

	public void deleteProjectByIdentifier(String projectIdentifier) {

		Project project = findById(projectIdentifier.toUpperCase());
		if (project == null) {

			throw new ProjectIdException("Unable to Delete as Id: '" + projectIdentifier + "' does not exists!");
		}
		projectRepository.delete(project);

	}

}
