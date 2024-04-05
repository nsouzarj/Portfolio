package br.teste.eti.Portfolio.controllers;

import br.teste.eti.Portfolio.domain.Person;
import br.teste.eti.Portfolio.domain.Project;
import br.teste.eti.Portfolio.services.PersonService;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@ExtendWith(MockitoExtension.class)
class PersonControllerTest {

    @Mock
    private PersonService personService;

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
        when(personService.getAllPeople()).thenReturn(people);

        mockMvc.perform(get("/api/pessoa/lista"))
                .andExpect(status().isOk())
                .andExpect(view().name("listapessoas"))
                .andExpect(model().attribute("people", hasSize(2)));

        verify(personService, times(1)).getAllPeople();
    }

    @Test
    void getPersonById() throws Exception {
        Person person1 = new Person();
        person1.setId(1L);
        person1.setNome("Nelsom");
        person1.setDatanascimento(LocalDate.now());
        person1.setCpf("3049309403940");
        person1.setFuncionario(true);
        person1.setGerente(true);

        when(personService.getPersonById(1L)).thenReturn(Optional.of(person1));

        mockMvc.perform(get("/api/pessoa/alteracao/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("alterarpessoa"))
                .andExpect(model().attribute("person", person1));

        verify(personService, times(1)).getPersonById(1L);
    }

    @Test
    void createPerson() throws Exception {
        Person person1 = new Person();
        person1.setId(1L);
        person1.setNome("Nelsom");
        person1.setDatanascimento(LocalDate.now());
        person1.setCpf("3049309403940");
        person1.setFuncionario(true);
        person1.setGerente(true);

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

        verify(personService, times(1)).createPerson(any(Person.class));
    }

    @Test
    void updatePersonSemErro() throws Exception {
        Person person1 = new Person();
        person1.setId(1L);
        person1.setNome("Nelsom");
        person1.setDatanascimento(LocalDate.now());
        person1.setCpf("3049309403940");
        person1.setFuncionario(true);
        person1.setGerente(true);
        when(personService.getPersonById(1L)).thenReturn(Optional.of(person1));

        mockMvc.perform(post("/api/pessoa/alterarPessoa/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("nome", person1.getNome())
                        .param("dataNascimento", person1.getDatanascimento().toString())
                        .param("cpf", person1.getCpf())
                        .param("funcionario", "true")
                        .param("gerente", "false"))
                .andExpect(status().isOk())
                .andExpect(view().name("listapessoas"));

        verify(personService, times(2)).updatePerson(1L, person1);
    }
    @Test
    void updatePersonComErro() throws Exception {
        Person person1 = new Person();
        person1.setId(1L);
        person1.setNome("Nelsom");
        person1.setDatanascimento(LocalDate.now());
        person1.setCpf("3049309403940");
        person1.setFuncionario(false);
        person1.setGerente(true);
        when(personService.getPersonById(1L)).thenReturn(Optional.of(person1));

        mockMvc.perform(post("/api/pessoa/alterarPessoa/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("nome", person1.getNome())
                        .param("dataNascimento", person1.getDatanascimento().toString())
                        .param("cpf", person1.getCpf())
                        .param("funcionario", "false")
                        .param("gerente", "false"))
                .andExpect(status().isOk())
                .andExpect(view().name("listapessoas"));

        verify(personService, times(1)).updatePerson(1L, person1);
    }

    @Test
    void deletePerson() throws Exception {
        mockMvc.perform(get("/api/pessoa/excluirPessoa/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("listapessoas"));

        verify(personService, times(1)).deletePerson(1L);
    }


    @Test
    public void testCreatePersonSuccess() {
        ///Cenario
        Person person = new Person();
        person.setId(1L);
        person.setNome("TESTET");
        person.setCpf("3848484");
        person.setFuncionario(true);
        person.setGerente(true);
        person.setDatanascimento(LocalDate.now());
        //Acao
        when(personService.createPerson(Mockito.any(Person.class))).thenReturn(null);

        // Call the controller method
        ModelAndView modelAndView = personController.createPerson(person.getNome(), person.getDatanascimento(),
                person.getCpf(), person.isFuncionario(),
                person.isGerente());

        // Verify service call and model attributes
        verify(personService).createPerson(Mockito.any(Person.class));
        assertEquals("listapessoas", modelAndView.getViewName());
        assertTrue((Boolean) modelAndView.getModel().get("alert"));
        assertEquals("Pessoa cadastrada com sucesso.", modelAndView.getModel().get("msg"));
    }

    @Test
    public void testCreatePersonFailNotFuncionario() {
        ///Cenario
        Person person = new Person();
        person.setId(1L);
        person.setNome("TESTET");
        person.setCpf("3848482");
        person.setFuncionario(false);
        person.setGerente(false);
        person.setDatanascimento(LocalDate.now());

        ModelAndView modelAndView = personController.createPerson(person.getNome(), person.getDatanascimento(),
                person.getCpf(), person.isFuncionario(),
                person.isGerente());

        assertEquals("listapessoas", modelAndView.getViewName());
        assertTrue((Boolean) modelAndView.getModel().get("alert"));
        assertEquals("Erro cadastrar verifique o cargo se empregado estao marcado.", modelAndView.getModel().get("msg"));
    }

}