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
import java.util.Objects;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/pessoa")
public class PersonController {
    private String msg;
    private boolean alert;

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
        Person person= new Person();
        response.setContentType("text/html; charset=UTF-8");
        ModelAndView view = new ModelAndView("cadastropessoa");
        view.addObject("p",person);
        return view;
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
    @PostMapping(value = "/cadastrarPessoa")
    @ResponseBody
    public ModelAndView createPerson1(Person person1)  {
        Person person= new Person();
        person=person1;

        if (person.isFuncionario()  && !Objects.equals(person.getNome(), "")){
            alert=true;
            msg="Pessoa cadastrada com sucesso.";
            personService.createPerson(person);
        }else{
            alert=true;
            msg="Erro cadastrar verifique o cargo se empregado estao marcado.";
        }

        List<Person> people= personService.getAllPeople();
        System.out.println(people);

        ModelAndView modelAndView = new ModelAndView("listapessoas");
        modelAndView.addObject("msg",msg);
        modelAndView.addObject("alert",alert);
        modelAndView.addObject("people",people);
        return modelAndView;
    }

    @CrossOrigin
    @PostMapping(value="/alterarPessoa/{id}")
    public ModelAndView updatePerson(@PathVariable String id,Person person) {

        ModelAndView andView = new ModelAndView("listapessoas");
        long num = 0;
        num= Long.parseLong(id);

        if (person.isFuncionario()  && !Objects.equals(person.getNome(), "")){
            personService.updatePerson(num, person);
        }else{
            alert=true;
            msg="Erro ao alterar a pessoa verifique o cargo se empregado esta marcado.";
        }
        personService.updatePerson(num, person);
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