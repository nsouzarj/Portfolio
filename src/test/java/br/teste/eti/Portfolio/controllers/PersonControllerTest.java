package br.teste.eti.Portfolio.controllers;

import br.teste.eti.Portfolio.domain.Person;
import br.teste.eti.Portfolio.domain.Project;
import br.teste.eti.Portfolio.services.PersonService;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@ExtendWith(MockitoExtension.class)
class PersonControllerTest {

    @Mock
    private PersonService personService;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private PersonController personController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
    }

    @Test
    void getAllPeople() throws Exception {
        //Cenario
        Person person1 = new Person();
        person1.setId(1L);
        person1.setNome("Nelsom");
        person1.setDatanascimento(LocalDate.now());
        person1.setCpf("3049309403940");
        person1.setFuncionario(true);
        person1.setGerente(true);
        Person person2 = new Person();
        person2.setId(2L);
        person2.setNome("Ana Rosa");
        person2.setDatanascimento(LocalDate.now());
        person2.setCpf("3049309402673");
        person2.setFuncionario(false);
        person2.setGerente(true);
        List<Person> people = Arrays.asList(person1, person2);
        //Acao
        when(personService.getAllPeople()).thenReturn(people);

        mockMvc.perform(get("/api/pessoa/lista"))
                .andExpect(status().isOk())
                .andExpect(view().name("listapessoas"))
                .andExpect(model().attribute("people", hasSize(2)));
        //Verificacao
        verify(personService, times(1)).getAllPeople();
    }

    @Test
    void getPersonById() throws Exception {
        //Cenario
        Person person1 = new Person();
        person1.setId(1L);
        person1.setNome("Nelsom");
        person1.setDatanascimento(LocalDate.now());
        person1.setCpf("3049309403940");
        person1.setFuncionario(true);
        person1.setGerente(true);
        //Veriricacao
        when(personService.getPersonById(1L)).thenReturn(Optional.of(person1));

        mockMvc.perform(get("/api/pessoa/alteracao/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("alterarpessoa"))
                .andExpect(model().attribute("person", person1));
        //Verificacao
        verify(personService, times(1)).getPersonById(1L);
    }

    @Test
    void createPerson() throws Exception {
        //Cenario
        Person person1 = new Person();
        person1.setId(1L);
        person1.setNome("Nelsom");
        person1.setDatanascimento(LocalDate.now());
        person1.setCpf("3049309403940");
        person1.setFuncionario(true);
        person1.setGerente(true);
        //Acao
        when(personService.createPerson(any(Person.class))).thenReturn(person1);

        mockMvc.perform(post("/api/pessoa/cadastrarPessoa")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("nome", "John Doe")
                        .param("dataNascimento", LocalDate.now().toString())
                        .param("cpf", "12345678901")
                        .param("funcionario", "true")
                        .param("gerente", "false"))
                .andExpect(status().isOk())
                .andExpect(view().name("listapessoas"));
        //Verificacao
        verify(personService, times(1)).createPerson(any(Person.class));
    }

    @Test
    void createPersonError() throws Exception {
        //Cenario
        Person person1 = new Person();
        person1.setId(1L);
        person1.setNome("Nelsom");
        person1.setDatanascimento(LocalDate.now());
        person1.setCpf("3049309403940");
        person1.setFuncionario(false);
        person1.setGerente(false);
        List<Person> teste= new ArrayList<>();
        teste.add(person1);
       //Acao
       //when(personService.createPerson(any(Person.class))).thenReturn(person1);

        mockMvc.perform(post("/api/pessoa/cadastrarPessoa")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("nome", "John Doe")
                        .param("dataNascimento", LocalDate.now().toString())
                        .param("cpf", "12345678901")
                        .param("funcionario", "false")
                        .param("gerente", "true"))
                .andExpect(status().isOk())
                .andExpect(view().name("listapessoas"));
        //Verificacao
        //verify(personService, times(1)).getAllPeople();
        verify(personService, times(1)).getAllPeople();
    }



    @Test
    void deletePerson() throws Exception {
        mockMvc.perform(get("/api/pessoa/excluirPessoa/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("listapessoas"));

        verify(personService, times(1)).getAllPeople();
    }


    @Test
    public void testUpdatePersonSuccess() {
        //Cenario
        Person person1 = new Person();
        person1.setId(1L);
        person1.setNome("Nelsom");
        person1.setDatanascimento(LocalDate.now());
        person1.setCpf("3049309403940");
        person1.setFuncionario(true);
        person1.setGerente(true);

        //Acao
        when(personService.updatePerson(anyLong(), eq(person1))).thenReturn(person1);
        ModelAndView modelAndView = personController.updatePerson("123", person1);

        //Verificacao
        assertEquals("listapessoas", modelAndView.getViewName());
        // Add more assertions as needed
    }

    @Test
    public void testUpdatePersonError() {
        Person person1 = new Person();
        person1.setId(1L);
        person1.setNome("Nelsom");
        person1.setDatanascimento(LocalDate.now());
        person1.setCpf("3049309403940");
        person1.setFuncionario(false);
        person1.setGerente(true);
        // Example valid data
        when(personService.updatePerson(anyLong(), eq(person1))).thenReturn(person1);

        // Act
        ModelAndView modelAndView = personController.updatePerson("123", person1);

        // Assert
        assertEquals("listapessoas", modelAndView.getViewName());
        // Add more assertions as needed
    }

    @Test
    public void testCadastrarPessoas() {
        //Cenario
        ModelAndView expectedView = new ModelAndView("cadastropessoa");
        Person expectedPerson = new Person();

        //Acao
        ModelAndView actualView = personController.cadastrarPessoas(response);

        //Verificacao
        assertEquals(expectedView.getViewName(), actualView.getViewName());
        assertEquals(expectedPerson, actualView.getModel().get("p"));

    }


}