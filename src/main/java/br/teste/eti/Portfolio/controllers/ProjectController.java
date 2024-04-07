package br.teste.eti.Portfolio.controllers;
import br.teste.eti.Portfolio.domain.Person;
import br.teste.eti.Portfolio.domain.Project;
import br.teste.eti.Portfolio.domain.dto.ConvertDTOS;
import br.teste.eti.Portfolio.domain.dto.ProjectDto;
import br.teste.eti.Portfolio.enums.Risco;
import br.teste.eti.Portfolio.enums.StatusProjeto;
import br.teste.eti.Portfolio.services.PersonService;
import org.aspectj.weaver.patterns.PerObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import br.teste.eti.Portfolio.services.ProjectService;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/projeto")
public class ProjectController {
    private final ProjectService projectService;
    private final PersonService personService;
    @Autowired
    public ProjectController (ProjectService projectService, PersonService personService) {
        this.projectService = projectService;
        this.personService=personService;
    }

    @GetMapping("/listaprojetos")
    public ModelAndView  getAllProjects() {
        ModelAndView view = new ModelAndView("listaprojetos");
        String msg="XXXXXXXXXXXXXXXXXXX";
        boolean alert = false;
        List<Person> listPerson=personService.getAllPeople();
        view.addObject("msg",msg);
        view.addObject("alert",alert);
        view.addObject("listPerson",listPerson);
        view.addObject("listaproj",projectService.getAllProjects());
        return view;
    }

    @GetMapping("/cadprojeto")
    public ModelAndView cadprojeto(){
        ModelAndView view = new ModelAndView("/cadastroprojeto");
        // Adicione os enums ao ModelAndView

        List<Person> listPerson=personService.getAllPeople();
        ProjectDto projectDto = new ProjectDto();
        view.addObject("listPerson", listPerson);
        view.addObject("riscoEnum", Risco.values());
        view.addObject("pro",projectDto);
        view.addObject("statusEnum", StatusProjeto.values());

        return view;
    }
    @GetMapping("/alterarprojeto/{id}")
    public ModelAndView getProjectById(@PathVariable String id) {
        ConvertDTOS dtos = new ConvertDTOS();
        long num = 0;
        num= Long.parseLong(id);
        Optional<Project> project  = projectService.getProjectById(num);
        Project p = project.orElse(new Project());
        ProjectDto projectDto = dtos.projectDtoConvert(p);
        ModelAndView view = new ModelAndView("alterarprojeto");
        List<Person> listPerson=personService.getAllPeople();
        view.addObject("listPerson", listPerson);
        view.addObject("riscoEnum", Risco.values());
        view.addObject("statusEnum", StatusProjeto.values());
        view.addObject("project",projectDto);
        return view;
    }


    @GetMapping("/excluirProjeto/{id}")
    public ModelAndView deleteProject(@PathVariable String id) {
        long num = 0;
        String msg="XXXXXXXXXXXXXXXXXXX";
        boolean alert = false;
        num= Long.parseLong(id);
        Optional<Project> project=projectService.getProjectById(num);
        List<Person> listPerson=personService.getAllPeople();
        Project p = project.orElse(new Project());
        ModelAndView modelAndView= new ModelAndView("listaprojetos");
        try {
            if(p.getStatus().equals(StatusProjeto.INICIADO.getDescricao()) ||
                    p.getStatus().equals(StatusProjeto.EM_ANDAMENTO.getDescricao())||
                    p.getStatus().equals(StatusProjeto.ENCERRDO.getDescricao())){

                msg="Projeto não pode ser excluido o status esta em: "+p.getStatus();
                alert=true;
                modelAndView.addObject("msg",msg);
                modelAndView.addObject("alert",alert);
                modelAndView.addObject("listPerson",listPerson);
                modelAndView.addObject("listaproj",projectService.getAllProjects());

           }else{
                projectService.deleteProject(num);
                msg="Projeto excluido com sucesso.";
                alert=true;
                modelAndView.addObject("msg",msg);
                modelAndView.addObject("alert",alert);
                modelAndView.addObject("listPerson",listPerson);
                modelAndView.addObject("listaproj",projectService.getAllProjects());
           }
        } catch (IllegalArgumentException e) {
            msg="Erro ao excluir o projeto";
            alert=true;
            modelAndView.addObject("msg",msg);
            modelAndView.addObject("alert",alert);
            modelAndView.addObject("listaproj",projectService.getAllProjects());
        }
        return modelAndView;
    }

    @CrossOrigin
    @PostMapping(value = "/cadastrarProjeto",consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public ModelAndView createProject(ProjectDto projectDto ) {
        //Chama o DTO
        ConvertDTOS dtos = new ConvertDTOS();
        Project project  = dtos.convertProjectDto(projectDto);
        // Dentro do método createProject
        try {
            Risco riscoEnum = Risco.valueOf(projectDto.getRisco());
            project.setRisco(riscoEnum.getDescricao());
        } catch (IllegalArgumentException e) {
            project.setRisco(Risco.DESCONHECIDO.name());
        }

        try {
            StatusProjeto statusEnum = StatusProjeto.valueOf(project.getStatus().toUpperCase());
            project.setStatus(statusEnum.getDescricao());
        } catch (IllegalArgumentException e) {
            project.setStatus(StatusProjeto.DESCONHECIDO.getDescricao());
        }

        projectService.createProject(project);
        ModelAndView modelAndView= new ModelAndView("listaprojetos");

        List<Person> listPerson=personService.getAllPeople();
        modelAndView.addObject("listPerson",listPerson);
        modelAndView.addObject("listaproj",projectService.getAllProjects());
        return modelAndView;

    }
    @CrossOrigin
    @PostMapping(value = "/alterarProjeto/{id}")
    @ResponseBody
    public ModelAndView alterProject(@PathVariable String id,
                                     ProjectDto projectDto) {
        long num = 0;
        num= Long.parseLong(id);
        ConvertDTOS dtos = new ConvertDTOS();
        Project pro = dtos.convertProjectDto(projectDto);

        try {
            Risco risco= Risco.valueOf(pro.getRisco());
            pro.setRisco(risco.getDescricao());
        } catch (IllegalArgumentException e) {
            pro.setRisco(Risco.DESCONHECIDO.name());
        }

        try {
            StatusProjeto statusProjeto = StatusProjeto.valueOf(pro.getStatus());
            pro.setStatus(statusProjeto.getDescricao());
        } catch (IllegalArgumentException e) {
            pro.setStatus(StatusProjeto.DESCONHECIDO.getDescricao());
        }

        projectService.updateProject(num, pro);
        List<Person> listPerson=personService.getAllPeople();
        List<Project> listaproj=projectService.getAllProjects();
        ModelAndView modelAndView= new ModelAndView("listaprojetos");
        modelAndView.addObject("listPerson", listPerson);
        modelAndView.addObject("listaproj",listaproj);
        return modelAndView;

    }

}