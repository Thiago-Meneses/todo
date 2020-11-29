package com.todo.controller;

import com.todo.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.todo.models.ToDo;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class ToDoController {

    @Autowired
    private ToDoRepository tdr;

    @RequestMapping(value="/cadastrarTarefa", method= RequestMethod.GET)
    public String form(){
        return "todo/formTodo";
    }

    @RequestMapping(value="/cadastrarTarefa", method= RequestMethod.POST)
    public String form(@Valid ToDo tarefa, BindingResult result, RedirectAttributes attributes){
        if (result.hasErrors()){
            attributes.addFlashAttribute("incompleto", "Preencha a tarefa!");
            return "redirect:/cadastrarTarefa";
        }
        tdr.save(tarefa);
        attributes.addFlashAttribute("mensagem", "Lista de Tarefas atualizada!");
        return "redirect:/cadastrarTarefa";
    }

    @RequestMapping("/tarefas")
    public ModelAndView listaTarefas(){
        ModelAndView mv = new ModelAndView("index");
        Iterable<ToDo> tarefas = tdr.findAll();
        mv.addObject("tarefas", tarefas);
        return mv;
    }

    @RequestMapping("/atualizarTarefa")
    public String atualizarTarefa(long codigo){
        ToDo tarefa = tdr.findByCodigo(codigo);
        if (tarefa.isConcluido() == false){
            tarefa.setConcluido(true);
            tdr.save(tarefa);
        } else {
            tarefa.setConcluido(false);
            tdr.save(tarefa);
        }
        return "redirect:/tarefas";
    }

    @RequestMapping("/deletarTarefa")
    public String deletarTarefa(long codigo){
        ToDo tarefa = tdr.findByCodigo(codigo);
        tdr.delete(tarefa);
        return "redirect:/tarefas";
    }
}