package br.teste.eti.Portfolio.services;

import br.teste.eti.Portfolio.domain.Project;
import br.teste.eti.Portfolio.repositorys.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }

    @Override
    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Project updateProject(Long id, Project updatedProject) {
        Project existingProject = projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with id: " + id));

        existingProject.setNome(updatedProject.getNome());
        existingProject.setData_inicio(updatedProject.getData_inicio());
        existingProject.setStatus(updatedProject.getStatus());
        existingProject.setRisco(updatedProject.getRisco());
        existingProject.setDescricao(updatedProject.getDescricao());
        existingProject.setNome(updatedProject.getNome());
        existingProject.setData_previsao_fim(updatedProject.getData_previsao_fim());
        existingProject.setData_inicio(updatedProject.getData_inicio());
        existingProject.setData_fim(updatedProject.getData_fim());
        existingProject.setOrcamento(updatedProject.getOrcamento());

        // ... Atualize outros campos conforme necessário

        return projectRepository.save(existingProject);
    }

    @Override
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
}