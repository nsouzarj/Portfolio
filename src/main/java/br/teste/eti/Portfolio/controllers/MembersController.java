package br.teste.eti.Portfolio.controllers;

import br.teste.eti.Portfolio.domain.Members;
import br.teste.eti.Portfolio.services.MembersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@CrossOrigin
@RequestMapping ("/api/grupos")
public class MembersController {

    @Autowired
    private MembersService membersService;

    @PostMapping
    public Members createMember(@RequestBody Members member) {
        return membersService.createMember(member);
    }

    @GetMapping("/{idProjeto}/{idPessoa}")
    public Members getMember(@PathVariable Long idProjeto, @PathVariable Long idPessoa) {
        return membersService.getMember(idProjeto, idPessoa);
    }

    @PutMapping
    public Members updateMember(@RequestBody Members member) {
        return membersService.updateMember(member);
    }

    @DeleteMapping("/{idProjeto}/{idPessoa}")
    public void deleteMember(@PathVariable Long idProjeto, @PathVariable Long idPessoa) {
        membersService.deleteMember(idProjeto, idPessoa);
    }

    // Outros métodos para lidar com diferentes endpoints e ações
}