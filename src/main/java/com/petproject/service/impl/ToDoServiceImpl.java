package com.petproject.service.impl;

import com.petproject.exception.NullEntityReferenceException;
import com.petproject.model.ToDo;
import com.petproject.repository.ToDoRepository;
import com.petproject.service.ToDoService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ToDoServiceImpl implements ToDoService {
    private ToDoRepository toDoRepository;

    public ToDoServiceImpl(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    @Override
    public ToDo create(ToDo todo) {
        if (todo == null) {
            throw new NullEntityReferenceException("ToDo cannot be 'null'");
        }
        return toDoRepository.save(todo);
    }

    @Override
    public ToDo readById(long id) {
        return toDoRepository.findById(id).orElseThrow(
                () -> new NullEntityReferenceException("Cannot find ToDo with given 'id'"));
    }

    @Override
    public ToDo update(ToDo todo) {

        if (todo == null) {
            throw new NullEntityReferenceException("ToDo cannot be 'null'");
        }

        readById(todo.getId());
        return toDoRepository.save(todo);
    }

    @Override
    public void delete(long id) {
        toDoRepository.delete(readById(id));
    }

    @Override
    public List<ToDo> getAll() {
        List<ToDo> todos = toDoRepository.findAll();
        return todos.isEmpty() ? new ArrayList<>() : todos;
    }

    @Override
    public List<ToDo> getByUserId(long userId) {
        List<ToDo> toDos = toDoRepository.getByUserId(userId);
        return toDos.isEmpty() ? new ArrayList<>() : toDos;
    }
}
