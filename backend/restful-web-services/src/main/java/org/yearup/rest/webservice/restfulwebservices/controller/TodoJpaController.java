package org.yearup.rest.webservice.restfulwebservices.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.yearup.rest.webservice.restfulwebservices.model.Todo;
import org.yearup.rest.webservice.restfulwebservices.repository.TodoJpaRepository;
import org.yearup.rest.webservice.restfulwebservices.service.TodoHardCodedService;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class TodoJpaController {

    @Autowired
    private TodoHardCodedService todoService;

    @Autowired
    private TodoJpaRepository todoJpaRepository;

    @GetMapping(path = "/jpa/users/{userName}/todos")
    public List<Todo> getAllTodos(@PathVariable String userName) {
        return todoJpaRepository.findByUserName(userName);
    }

    @GetMapping(path = "/jpa/users/{userName}/todos/{id}")
    public Todo getTodo(@PathVariable String userName, @PathVariable Long id) {
        return todoJpaRepository.findById(id).get();
    }

    @DeleteMapping(path = "/jpa/users/{userName}/todos/{id}")
    public ResponseEntity<?> deleteTodoById(@PathVariable String userName, @PathVariable Long id) {
        todoJpaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/jpa/users/{userName}/todos/{id}")
    public ResponseEntity<Todo> updateTodo (@PathVariable String userName,
                                         @PathVariable Long id,
                                         @RequestBody Todo todo) {
        Todo updatedTodo = todoJpaRepository.save(todo);
        return new ResponseEntity<>(todo, HttpStatus.OK);
    }


    @PostMapping("/jpa/users/{userName}/todos")
    public ResponseEntity<?> createTodo (@PathVariable String userName, @RequestBody Todo todo) {
        todo.setUserName(userName);
        Todo createdTodo = todoJpaRepository.save(todo);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdTodo.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

}
