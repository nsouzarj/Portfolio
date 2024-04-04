package br.teste.eti.Portfolio.controllers;

import br.teste.eti.Portfolio.domain.Person;
import br.teste.eti.Portfolio.services.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
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
    void updatePerson() throws Exception {
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

        verify(personService, times(1)).updatePerson(1L, person1);
    }

    @Test
    void deletePerson() throws Exception {
        mockMvc.perform(get("/api/pessoa/excluirPessoa/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("listapessoas"));

        verify(personService, times(1)).deletePerson(1L);
    }


    // Add similar tests for updatePerson and deletePerson methods
}