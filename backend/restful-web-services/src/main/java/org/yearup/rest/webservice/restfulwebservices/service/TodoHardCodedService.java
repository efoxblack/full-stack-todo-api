package org.yearup.rest.webservice.restfulwebservices.service;

import org.springframework.stereotype.Service;
import org.yearup.rest.webservice.restfulwebservices.model.Todo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TodoHardCodedService {

    private static final List<Todo> todos = new ArrayList<>();

    private static Long idCounter = 0L;

    static {
        todos.add(new Todo(++idCounter, "bruh", "Learn to dance", new Date(), false));
        todos.add(new Todo(++idCounter, "bruh", "Learn about Microservices", new Date(), false));
        todos.add(new Todo(++idCounter, "bruh", "Learn about Angular", new Date(), false));
    }

    public List<Todo> findAll() {
        return todos;
    }

    public Todo save(Todo todo) {
        if(todo.getId() == -1 || todo.getId() == 0) {
            todo.setId(++idCounter);
        } else {
            deleteById(todo.getId());
        }
        todos.add(todo);
        return todo;
    }

    public Todo deleteById(Long id) {
        Todo todo = findById(id);

        if (todo == null) {
            return null;
        }
        if (todos.remove(todo)) {
            return todo;
        }
        return null;
    }

    public Todo findById(Long id) {
        for (Todo todo: todos) {
            if (todo.getId().equals(id)) {
                return todo;
            }
        }
        return null;
    }

}
