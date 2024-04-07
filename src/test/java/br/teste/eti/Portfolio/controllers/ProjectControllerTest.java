package br.teste.eti.Portfolio.controllers;

import br.teste.eti.Portfolio.domain.Person;
import br.teste.eti.Portfolio.domain.Project;
import br.teste.eti.Portfolio.domain.dto.ConvertDTOS;
import br.teste.eti.Portfolio.domain.dto.ProjectDto;
import br.teste.eti.Portfolio.enums.Risco;
import br.teste.eti.Portfolio.enums.StatusProjeto;
import br.teste.eti.Portfolio.services.PersonService;
import br.teste.eti.Portfolio.services.PersonServiceImpl;
import br.teste.eti.Portfolio.services.ProjectService;
import br.teste.eti.Portfolio.services.ProjectServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith (MockitoExtension.class)
class ProjectControllerTest {

    @Mock
    private PersonServiceImpl personService;
    @Mock
    private ProjectServiceImpl projectService;
    @InjectMocks
    private ProjectController projectController;


    @BeforeEach
    void setUp() {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(projectController).build();
    }

    @Test
    void testCadprojeto() {
        //Acao
        when(personService.getAllPeople()).thenReturn(new ArrayList<>());
        ModelAndView modelAndView = projectController.cadprojeto();
        //Verificacao
        assertEquals("/cadastroprojeto", modelAndView.getViewName());
        assertNotNull(modelAndView.getModel().get("listPerson"));
        assertNotNull(modelAndView.getModel().get("riscoEnum"));
        assertNotNull(modelAndView.getModel().get("statusEnum"));
    }

    @Test
    public void testGetProjectById() {
        //Cenario
        long projectId = 1L;
        Project mockProject = new Project();
        //Acao
        when(projectService.getProjectById(projectId)).thenReturn(Optional.of(mockProject));
        ModelAndView result = projectController.getProjectById(String.valueOf(projectId));
        //Verificacao
        assertEquals("alterarprojeto", result.getViewName());

    }


    @Test
    public void testDeleteProjectEmAnalise() {
        // Mock the service behavior
        long projectId = 1L;
        Project mockProject = new Project();
        mockProject.setId(projectId);
        mockProject.setNome("TESTTE");
        mockProject.setDescricao("XXXXXXXXXXXXXXXXX");
        mockProject.setOrcamento(2000.00F);
        mockProject.setStatus(StatusProjeto.EM_ANALISE.getDescricao());
        when(projectService.getProjectById(projectId)).thenReturn(Optional.of(mockProject));

        // Call the controller method
        ModelAndView result = projectController.deleteProject(String.valueOf(projectId));

        // Verify the results
        verify(projectService).deleteProject(projectId);
        assertEquals("listaprojetos", result.getViewName());
    }

    @Test
    public void testDeleteProjectEmAndamento() {
        //Cenario
        long projectId = 1L;
        Project mockProject = new Project();
        mockProject.setId(projectId);
        mockProject.setNome("TESTTE");
        mockProject.setDescricao("XXXXXXXXXXXXXXXXX");
        mockProject.setOrcamento(2000.00F);
        mockProject.setStatus(StatusProjeto.EM_ANDAMENTO.getDescricao());
        //Acao
        when(projectService.getProjectById(projectId)).thenReturn(Optional.of(mockProject));
        // Call the controller method
        ModelAndView result = projectController.deleteProject(String.valueOf(projectId));
        //Verificacao
        assertEquals("listaprojetos", result.getViewName());
    }




    @Test
    public void testAlterProject_successfulUpdate() {
        //Cenario
        ConvertDTOS dtos = new ConvertDTOS();
        Long id = 1L;
        Project existingProject = new Project();
        existingProject.setId(1L);
        existingProject.setNome("AAAAAA");
        existingProject.setRisco(Risco.MEDIO.getDescricao());
        existingProject.setStatus(StatusProjeto.EM_ANALISE.getDescricao());
        ProjectDto projectDto = dtos.projectDtoConvert(existingProject);
        List<Person> mockPersonList = new ArrayList<>();
        when(personService.getAllPeople()).thenReturn(mockPersonList);
        List<Project> mockProjectList = new ArrayList<>();
        when(projectService.getAllProjects()).thenReturn(mockProjectList);

        //Acao
        ModelAndView result = projectController.alterProject(id.toString(),projectDto);

        //Verificacao
        verify(projectService).updateProject(eq(id), any(Project.class));
        assertEquals("listaprojetos", result.getViewName());
        assertEquals(mockPersonList, result.getModel().get("listPerson"));
        assertEquals(mockProjectList, result.getModel().get("listaproj"));
    }



    @Test
    public void testCreateProject() {
        ConvertDTOS dtos = new ConvertDTOS();
        //Cenario
        Project project= new Project();
        // Prepare test data
        project.setNome("XXXXXXXX");
        project.setRisco("Risco baixo");
        project.setData_inicio(LocalDate.now());
        project.setDescricao("projeto de teste");
        project.setData_previsao_fim(LocalDate.now());
        project.setData_fim(LocalDate.now());
        project.setOrcamento(10000F);
        project.setIdgerente(1L);
        project.setStatus("Baixo Risco");

        ProjectDto projectDto = dtos.projectDtoConvert(project);

        //Acao
        when(projectService.createProject(Mockito.any(Project.class))).thenReturn(null);
        ModelAndView modelAndView = projectController.createProject(projectDto);

        //Verirficacao
        verify(projectService).createProject(Mockito.any(Project.class));
        assertEquals("listaprojetos", modelAndView.getViewName());
        // Add assertions for the listaproj attribute based on your expected behavior
    }


    @Test
    public void testGetAllProjects_ValidData() {
        // Arrange
        List<Person> mockPeople = personService.getAllPeople();
        List<Project> mockProjects = projectService.getAllProjects();
        when(personService.getAllPeople()).thenReturn(mockPeople);
        when(projectService.getAllProjects()).thenReturn(mockProjects);

        // Act
        ModelAndView modelAndView = projectController.getAllProjects();

        // Assert
        assertEquals("listaprojetos", modelAndView.getViewName());
        assertFalse((boolean) modelAndView.getModel().get("alert"));
        assertEquals("XXXXXXXXXXXXXXXXXXX", modelAndView.getModel().get("msg"));
        assertEquals(mockPeople, modelAndView.getModel().get("listPerson"));
        assertEquals(mockProjects, modelAndView.getModel().get("listaproj"));
        // Add more assertions as needed
    }


}