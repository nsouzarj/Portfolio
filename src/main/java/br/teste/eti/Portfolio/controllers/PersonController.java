package br.teste.eti.Portfolio.controllers;
import br.teste.eti.Portfolio.domain.Person;
import br.teste.eti.Portfolio.services.PersonService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/pessoa")
public class PersonController {

    private final PersonService personService;
    @Autowired
    public PersonController (PersonService personService) {
        this.personService = personService;
    }
    public List<Person> getAllPeople() {
        return personService.getAllPeople();
    }
    @CrossOrigin
    @GetMapping("/lista")
    public ModelAndView listaPessoas(){
        ModelAndView modelAndView = new ModelAndView("listapessoas");
        List<Person> people= personService.getAllPeople();
        System.out.println(people);
        modelAndView.addObject("people",people);
        return  modelAndView;
    }
    @CrossOrigin
    @GetMapping("/cadastrarpessoa")
    public ModelAndView cadastrarPessoas(HttpServletResponse response){
        response.setContentType("text/html; charset=UTF-8");
        return new ModelAndView("cadastropessoa");
    }


    @GetMapping(value = "/alteracao/{id}")
    public ModelAndView getPersonById(@PathVariable String id) {
        long num = 0;
        num= Long.parseLong(id);
        ModelAndView view = new ModelAndView("alterarpessoa");
        view.addObject("person",personService.getPersonById(num)
                .orElseThrow(() -> new EntityNotFoundException("Person not found with id: " + id)));
        return  view;
    }

    @CrossOrigin
    @PostMapping(value = "/cadastrarPessoa",consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public ModelAndView createPerson(@RequestParam String nome,
                               @RequestParam LocalDate dataNascimento,
                               @RequestParam String cpf,
                               @RequestParam(value = "funcionario",defaultValue = "false", required = true) boolean funcionario,
                               @RequestParam(value = "gerente", defaultValue = "false", required = true) boolean gerente)  {
        Person person= new Person();
        person.setNome(nome);
        person.setCpf(cpf);
        person.setDatanascimento(dataNascimento);
        person.setFuncionario(funcionario);
        person.setGerente(gerente);
        personService.createPerson(person);
        List<Person> people= personService.getAllPeople();
        System.out.println(people);
        ModelAndView modelAndView = new ModelAndView("listapessoas");
        modelAndView.addObject("people",people);
        return modelAndView;
    }

    @CrossOrigin
    @PostMapping(value="/alterarPessoa/{id}", consumes = "application/x-www-form-urlencoded")
    public ModelAndView updatePerson(@PathVariable String id,
                                     @RequestParam String nome,
                                     @RequestParam LocalDate dataNascimento,
                                     @RequestParam String cpf,
                                     @RequestParam(value = "funcionario",defaultValue = "false", required = true) boolean funcionario,
                                     @RequestParam(value = "gerente", defaultValue = "false", required = true) boolean gerente) {

        ModelAndView andView = new ModelAndView("listapessoas");
        long num = 0;
        num= Long.parseLong(id);
        Optional<Person> person = personService.getPersonById(num);
        Person person1 = person.orElse(new Person());
        person1.setNome(nome);
        person1.setDatanascimento(dataNascimento);
        person1.setCpf(cpf);
        person1.setFuncionario(funcionario);
        person1.setGerente(gerente);
        personService.updatePerson(num,person1);
        List<Person> people= personService.getAllPeople();
        andView = new ModelAndView("listapessoas");
        andView.addObject("people",people);
        return andView;
    }

    @CrossOrigin
    @GetMapping(value ="/excluirPessoa/{id}")
    public ModelAndView deletePerson(@PathVariable String id) {
        long num = 0;
        num= Long.parseLong(id);
        personService.deletePerson(num);
        List<Person> people= personService.getAllPeople();
        ModelAndView andView=new ModelAndView("listapessoas");
        andView.addObject("people",people);
        return andView;
    }
}