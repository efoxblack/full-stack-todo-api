package org.yearup.rest.webservice.restfulwebservices.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.yearup.rest.webservice.restfulwebservices.model.Todo;
import org.yearup.rest.webservice.restfulwebservices.service.TodoHardCodedService;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class TodoController {

    @Autowired
    private TodoHardCodedService todoService;

    @GetMapping(path = "/users/{userName}/todos")
    public List<Todo> getAllTodos(@PathVariable String userName) {
        return todoService.findAll();
    }

    @GetMapping(path = "/users/{userName}/todos/{id}")
    public Todo getTodo(@PathVariable String userName, @PathVariable Long id) {
        return todoService.findById(id);
    }

    @DeleteMapping(path = "/users/{userName}/todos/{id}")
    public ResponseEntity<?> deleteTodoById(@PathVariable String userName, @PathVariable Long id) {
        Todo todo = todoService.deleteById(id);

        if (todo != null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/users/{userName}/todos/{id}")
    public ResponseEntity<Todo> updateTodo (@PathVariable String userName,
                                         @PathVariable Long id,
                                         @RequestBody Todo todo) {
        Todo updatedTodo = todoService.save(todo);
        return new ResponseEntity<>(todo, HttpStatus.OK);
    }


    @PostMapping("/users/{userName}/todos")
    public ResponseEntity<?> createTodo (@PathVariable String userName, @RequestBody Todo todo) {
        Todo createdTodo = todoService.save(todo);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdTodo.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

}
