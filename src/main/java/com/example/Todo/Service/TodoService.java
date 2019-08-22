package com.example.Todo.Service;

import com.example.Todo.Repository.TodoRepository;
import com.example.Todo.Entity.TodoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Sort.Direction;

import java.util.List;

@Service
@Transactional
public class TodoService {
    @Autowired
    TodoRepository todoRepository;

    public List<TodoEntity> findAll() {
        return todoRepository.findAll(new Sort(Direction.DESC,"todoID"));
    }

    public TodoEntity save(TodoEntity todoEntity) {
        return todoRepository.save(todoEntity);
    }
    public List<TodoEntity> findMatch(String title){
        return todoRepository.findMatch(title);
    }

    public TodoEntity findOne(String id){
        return todoRepository.findOne(id);
    }

    public void update(String id,String title,String deadline){
        todoRepository.update(id,title,deadline);
    }

    public void changeFlag(String id,int flag){
        todoRepository.changeFlag(id,flag);
    }

    public String getCount(String title){
        return todoRepository.getCount(title);
    }

    public String getCount(){
        return todoRepository.getCount();
    }

    public String JudgeTitleName(String title){
        return todoRepository.JudgeTitleName(title);
    }

}