package br.teste.eti.Portfolio.controllers;

import br.teste.eti.Portfolio.domain.Person;
import br.teste.eti.Portfolio.domain.Project;
import br.teste.eti.Portfolio.enums.Risco;
import br.teste.eti.Portfolio.enums.StatusProjeto;
import br.teste.eti.Portfolio.services.PersonService;
import br.teste.eti.Portfolio.services.ProjectService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith (MockitoExtension.class)
class ProjectControllerTest {

    @Mock
    private ProjectService projectService;

    @MockBean
    private PersonService personService;

//    @Mock
//    private ProjectController projectController;

//    @MockBean
//    private ProjectService projectService;



    @InjectMocks
    private ProjectController projectController;



    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(projectController).build();
    }

    @Test
    void testCadprojeto() {

        when(personService.getAllPeople()).thenReturn(new ArrayList<>());

        ModelAndView modelAndView = projectController.cadprojeto();

        assertEquals("/cadastroprojeto", modelAndView.getViewName());
        assertNotNull(modelAndView.getModel().get("listPerson"));
        assertNotNull(modelAndView.getModel().get("riscoEnum"));
        assertNotNull(modelAndView.getModel().get("statusEnum"));
    }

    @Test
    public void testGetProjectById() {
        // Mock the service behavior
        long projectId = 1L;
        Project mockProject = new Project();
        when(projectService.getProjectById(projectId)).thenReturn(Optional.of(mockProject));

        // Call the controller method
        ModelAndView result = projectController.getProjectById(String.valueOf(projectId));

        // Verify the results
        assertEquals("alterarprojeto", result.getViewName());
        assertEquals(mockProject, result.getModel().get("project"));
    }


    @Test
    public void testDeleteProject() {
        // Mock the service behavior
        long projectId = 1L;
        Project mockProject = new Project();
        mockProject.setId(projectId);
        mockProject.setNome("TESTTE");
        mockProject.setDescricao("XXXXXXXXXXXXXXXXX");
        mockProject.setOrcamento(2000.00F);
        mockProject.setStatus(StatusProjeto.CANCELADO.getDescricao());
        when(projectService.getProjectById(projectId)).thenReturn(Optional.of(mockProject));

        // Call the controller method
        ModelAndView result = projectController.deleteProject(String.valueOf(projectId));

        // Verify the results
        verify(projectService).deleteProject(projectId);
        assertEquals("listaprojetos", result.getViewName());
    }




    @Test
    public void testAlterProject() {
        // Prepare test data
        Long id = 1L;
        String nome = "Test Project";
        LocalDate dataInicio = LocalDate.now();
        LocalDate dataPrevisaoFim = LocalDate.now().plusDays(30);
        LocalDate dataFim = LocalDate.now().plusDays(40);
        String descricao = "This is a test project";
        String status = "EM_ANDAMENTO";
        Float orcamento = 10000f;
        String risco = "BAIXO";
        Long idGerente = 2L;

        Project existingProject = new Project();
        existingProject.setId(id);

        // Mock dependencies
        when(projectService.getProjectById(id)).thenReturn(Optional.of(existingProject));
        when(personService.getAllPeople()).thenReturn(List.of(new Person()));
        when(projectService.getAllProjects()).thenReturn(List.of(existingProject));

        // Call the method under test
        ModelAndView modelAndView = projectController.alterProject(
                id.toString(), nome, dataInicio, dataPrevisaoFim, dataFim, descricao, status, orcamento, risco, idGerente);

        // Verify results
        Project updatedProject = new Project();
        updatedProject.setId(id);
        updatedProject.setNome(nome);
        updatedProject.setData_inicio(dataInicio);
        updatedProject.setData_previsao_fim(dataPrevisaoFim);
        updatedProject.setData_fim(dataFim);
        updatedProject.setDescricao(descricao);
        updatedProject.setStatus(StatusProjeto.EM_ANDAMENTO.getDescricao());
        updatedProject.setOrcamento(orcamento);
        updatedProject.setRisco(Risco.BAIXO.getDescricao());
        updatedProject.setIdgerente(idGerente);

        verify(projectService).updateProject(id, updatedProject);

        assertEquals("listaprojetos", modelAndView.getViewName());
        assertEquals(1, ((List<Person>) modelAndView.getModel().get("listPerson")).size());
        assertEquals(1, ((List<Project>) modelAndView.getModel().get("listaproj")).size());
    }

    @Test
    public void testCreateProjectWithValidData() {
        // Prepare test data
        String nome = "Test Project";
        LocalDate data_inicio = LocalDate.now();
        LocalDate data_previsao_fim = data_inicio.plusMonths(6);
        LocalDate data_fim = LocalDate.now();;
        String descricao = "This is a test project";
        String status = "EM_ANDAMENTO";
        Float orcamento = 10000.0f;
        String risco = "BAIXO";
        Long idgerente = 1L;

        Project project = new Project();
        project.setNome(nome);
        project.setData_inicio(data_inicio);
        project.setData_previsao_fim(data_previsao_fim);
        project.setData_fim(data_fim);
        project.setDescricao(descricao);
        project.setStatus(status);
        project.setOrcamento(orcamento);
        project.setRisco(risco);
        project.setIdgerente(idgerente);

        // Mock service behavior
        Mockito.when(projectService.createProject(project)).thenReturn(null).thenReturn(project);
        // Call the controller method
        ModelAndView modelAndView = projectController.createProject(nome, data_inicio, data_previsao_fim, data_fim, descricao, status, orcamento, risco, idgerente);

        // Verify results
        assertEquals("listaprojetos", modelAndView.getViewName());
        assertNotNull(modelAndView.getModel().get("listaproj"));
        Mockito.verify(projectService, Mockito.times(1)).createProject(project);
    }





}