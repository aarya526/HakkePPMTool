package com.hakke.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hakke.ppmtool.domain.Project;
import com.hakke.ppmtool.exceptions.ProjectIdException;
import com.hakke.ppmtool.repository.ProjectRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	public Project saveOrUpdate(Project project) {

		try {

			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
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

}
