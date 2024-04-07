package br.teste.eti.Portfolio.domain.dto;

import br.teste.eti.Portfolio.domain.Person;
import br.teste.eti.Portfolio.domain.Project;

public class ConvertDTOS {
    public Person covertPessonDto(PersonDto personDTO){
        Person person= new Person();
        person.setId(personDTO.getId());
        person.setNome(personDTO.getNome());
        person.setCpf(personDTO.getCpf());
        person.setGerente(personDTO.isGerente());
        person.setDatanascimento(personDTO.getDatanascimento());
        person.setFuncionario(personDTO.isFuncionario());
        return person;
    }

    public PersonDto covertDtoPesson(Person person){
        PersonDto personDto= new PersonDto();
        personDto.setId(person.getId());
        personDto.setNome(person.getNome());
        personDto.setCpf(person.getCpf());
        personDto.setGerente(person.isGerente());
        personDto.setDatanascimento(person.getDatanascimento());
        personDto.setFuncionario(person.isFuncionario());
        return personDto;
    }

    public Project convertProjectDto(ProjectDto projectDto){
        Project project = new Project();
        project.setId(projectDto.getId());
        project.setNome(projectDto.getNome());
        project.setDescricao(projectDto.getDescricao());
        project.setData_inicio(projectDto.getData_inicio());
        project.setData_previsao_fim(projectDto.getData_previsao_fim());
        project.setData_fim(projectDto.getData_fim());
        project.setStatus(projectDto.getStatus());
        project.setRisco(projectDto.getRisco());
        project.setOrcamento(projectDto.getOrcamento());
        return project;
    }

    public ProjectDto projectDtoConvert(Project project){
        ProjectDto projectDto = new ProjectDto();
        projectDto.setId(project.getId());
        projectDto.setNome(project.getNome());
        projectDto.setDescricao(project.getDescricao());
        projectDto.setOrcamento(project.getOrcamento());
        projectDto.setData_inicio(project.getData_inicio());
        projectDto.setData_previsao_fim(project.getData_previsao_fim());
        projectDto.setData_fim(project.getData_fim());
        projectDto.setStatus(project.getStatus());
        projectDto.setRisco(project.getRisco());
        projectDto.setIdgerente(project.getIdgerente());
        return projectDto;
    }




}
