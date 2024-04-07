package br.teste.eti.Portfolio.controllers;
import br.teste.eti.Portfolio.domain.Person;
import br.teste.eti.Portfolio.domain.dto.ConvertDTOS;
import br.teste.eti.Portfolio.domain.dto.PersonDto;
import br.teste.eti.Portfolio.services.PersonServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/pessoa")
public class PersonController {
    private String msg;
    private boolean alert;
    private final PersonServiceImpl personService;
    @Autowired
    public PersonController (PersonServiceImpl personService) {
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
       // Person person= new Person();
        PersonDto personDTO= new PersonDto();
        response.setContentType("text/html; charset=UTF-8");
        ModelAndView view = new ModelAndView("cadastropessoa");
        view.addObject("p",personDTO);
        return view;
    }


    @GetMapping(value = "/alteracao/{id}")
    public ModelAndView getPersonById(@PathVariable String id) {
        long num = 0;
        num= Long.parseLong(id);
        ConvertDTOS dtos = new ConvertDTOS();
        Optional<Person> person= personService.getPersonById(num);
        Person p = person.orElse(new Person());
        PersonDto personDto= dtos.covertDtoPesson(p);
        ModelAndView view = new ModelAndView("alterarpessoa");
        view.addObject("person",personDto);
        return  view;
    }

    @CrossOrigin
    @PostMapping(value = "/cadastrarPessoa")
    @ResponseBody
    public ModelAndView createPerson1(PersonDto personDto)  {
        ConvertDTOS dtos = new ConvertDTOS();
        Person person=dtos.covertPessonDto(personDto);
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
    public ModelAndView updatePerson(@PathVariable String id,PersonDto personDto) {
        ConvertDTOS dtos = new ConvertDTOS();
        Person person=dtos.covertPessonDto(personDto);
        ModelAndView andView = new ModelAndView("listapessoas");
        long num = 0;
        num= Long.parseLong(id);
        if (personDto.isFuncionario()  && !Objects.equals(person.getNome(), "")){
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