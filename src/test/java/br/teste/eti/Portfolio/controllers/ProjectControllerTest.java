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
import org.mockito.stubbing.OngoingStubbing;
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
    private PersonService personService;
    @Mock
    private ProjectService projectService;
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
    public void testAlterProject_successfulUpdate() {
        // Arrange
        Long id = 1L;
        Project existingProject = new Project();
        OngoingStubbing<Optional<Project>> optionalOngoingStubbing = when(projectService.getProjectById(id)).thenReturn(Optional.of(existingProject));
        List<Person> mockPersonList = new ArrayList<>();
        when(personService.getAllPeople()).thenReturn(mockPersonList);
        List<Project> mockProjectList = new ArrayList<>();
        when(projectService.getAllProjects()).thenReturn(mockProjectList);

        // Act
        ModelAndView result = projectController.alterProject(id.toString(), "New Name", LocalDate.now(),
                LocalDate.now(), LocalDate.now(), "New Description", "Em Andamento", 10000F, "Risco Baixo", 2L);

        // Assert
        verify(projectService).updateProject(eq(id), any(Project.class));
        assertEquals("listaprojetos", result.getViewName());
        assertEquals(mockPersonList, result.getModel().get("listPerson"));
        assertEquals(mockProjectList, result.getModel().get("listaproj"));
    }



    @Test
    public void testCreateProject() {
        // Prepare test data
        String nome = "Test Project";
        LocalDate dataInicio = LocalDate.now();
        LocalDate dataPrevisaoFim = dataInicio.plusMonths(6);
        String descricao = "TNovo Projeto";
        Float orcamento = 10000f;
        Long idGerente = 1L;

        // Mock service behavior
        when(projectService.createProject(Mockito.any(Project.class))).thenReturn(null);

        // Call the controller method
        ModelAndView modelAndView = projectController.createProject(nome, dataInicio, dataPrevisaoFim, null, descricao, "Em Andamento", orcamento, "Risco Baixo", idGerente);

        // Verify service call and model attributes
        verify(projectService).createProject(Mockito.any(Project.class));
        assertEquals("listaprojetos", modelAndView.getViewName());
        // Add assertions for the listaproj attribute based on your expected behavior
    }




}