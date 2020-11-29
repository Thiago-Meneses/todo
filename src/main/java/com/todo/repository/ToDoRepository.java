package com.todo.repository;

import com.todo.models.ToDo;
import org.springframework.data.repository.CrudRepository;

public interface ToDoRepository extends CrudRepository<ToDo, String>{
    ToDo findByCodigo(long codigo);
}
