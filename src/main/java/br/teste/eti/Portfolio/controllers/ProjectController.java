package br.teste.eti.Portfolio.controllers;
import br.teste.eti.Portfolio.domain.Person;
import br.teste.eti.Portfolio.domain.Project;
import br.teste.eti.Portfolio.enums.Risco;
import br.teste.eti.Portfolio.enums.StatusProjeto;
import br.teste.eti.Portfolio.services.PersonService;
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

        view.addObject("listPerson", listPerson);
        view.addObject("riscoEnum", Risco.values());
        view.addObject("statusEnum", StatusProjeto.values());
        return view;
    }
    @GetMapping("/alterarprojeto/{id}")
    public ModelAndView getProjectById(@PathVariable String id) {

        long num = 0;
        num= Long.parseLong(id);
        ModelAndView view = new ModelAndView("alterarprojeto");
        List<Person> listPerson=personService.getAllPeople();
        view.addObject("listPerson", listPerson);
        view.addObject("riscoEnum", Risco.values());
        view.addObject("statusEnum", StatusProjeto.values());

        view.addObject("project",projectService.getProjectById(num)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with id: " + id)));
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

//

    }

    @CrossOrigin
    @PostMapping(value = "/cadastrarProjeto",consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public ModelAndView createProject(@RequestParam String nome,
                               @RequestParam LocalDate data_inicio,
                               @RequestParam LocalDate data_previsao_fim,
                               @RequestParam LocalDate data_fim,
                               @RequestParam String descricao,
                               @RequestParam(value = "status",defaultValue = "DESCONHECIDO") String status,
                               @RequestParam Float orcamento,
                               @RequestParam(value = "risco",defaultValue = "DESCONHECIDO") String risco,
                               @RequestParam Long idgerente ) {
        Project project  = new Project();
        // Dentro do método createProject
        try {
            Risco riscoEnum = Risco.valueOf(risco);
            project.setRisco(riscoEnum.getDescricao());
        } catch (IllegalArgumentException e) {
            project.setRisco(Risco.DESCONHECIDO.name());
        }

        try {
            StatusProjeto statusEnum = StatusProjeto.valueOf(status.toUpperCase());
            project.setStatus(statusEnum.getDescricao());
        } catch (IllegalArgumentException e) {
            project.setStatus(StatusProjeto.DESCONHECIDO.getDescricao());
        }
        project.setNome(nome);
        project.setData_inicio(data_inicio);
        project.setData_previsao_fim(data_previsao_fim);
        project.setData_fim(data_fim);
        project.setDescricao(descricao);
        project.setOrcamento(orcamento);
        project.setIdgerente(idgerente);
        projectService.createProject(project);
        ModelAndView modelAndView= new ModelAndView("listaprojetos");

        List<Person> listPerson=personService.getAllPeople();
        modelAndView.addObject("listPerson",listPerson);
        modelAndView.addObject("listaproj",projectService.getAllProjects());
        return modelAndView;

    }
    @CrossOrigin
    @PostMapping(value = "/alterarProjeto/{id}",consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public ModelAndView alterProject(@PathVariable String id,
                                      @RequestParam String nome,
                                      @RequestParam LocalDate data_inicio,
                                      @RequestParam LocalDate data_previsao_fim,
                                      @RequestParam LocalDate data_fim,
                                      @RequestParam String descricao,
                                      @RequestParam String status,
                                      @RequestParam Float orcamento,
                                      @RequestParam String risco,
                                      @RequestParam Long idgerente ) {
        long num = 0;
        num= Long.parseLong(id);
        Optional<Project> project  = projectService.getProjectById(num);
        Project p = project.orElse(new Project());
        p.setNome(nome);
        p.setData_inicio(data_inicio);
        p.setData_previsao_fim(data_previsao_fim);
        p.setData_fim(data_fim);
        p.setDescricao(descricao);
        p.setOrcamento(orcamento);
        p.setIdgerente(idgerente);

        try {
            Risco riscoEnum = Risco.valueOf(risco);
            p.setRisco(riscoEnum.getDescricao());
        } catch (IllegalArgumentException e) {
            p.setRisco(Risco.DESCONHECIDO.name());
        }

        try {
            StatusProjeto statusEnum = StatusProjeto.valueOf(status);
            p.setStatus(statusEnum.getDescricao());
        } catch (IllegalArgumentException e) {
            p.setStatus(StatusProjeto.DESCONHECIDO.getDescricao());
        }

        projectService.updateProject(num,p);
        List<Person> listPerson=personService.getAllPeople();
        ModelAndView modelAndView= new ModelAndView("listaprojetos");
        modelAndView.addObject("listPerson", listPerson);
        modelAndView.addObject("listaproj",projectService.getAllProjects());
        return modelAndView;

    }

}