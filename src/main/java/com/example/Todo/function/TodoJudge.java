package com.example.Todo.function;

import com.example.Todo.Entity.TodoEntity;
import com.example.Todo.Service.TodoService;
import org.springframework.ui.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TodoJudge {
    private static TodoJudge todoJudge = null;
    private TodoJudge(){}
    public static TodoJudge getInstance(){
        if(todoJudge==null){
            todoJudge = new TodoJudge();
        }
        return todoJudge;
    }
    public boolean judgeTitle(TodoEntity todoEntity, TodoService todoService, Model model){
        //同じTodo名があるか判定
        if(todoService.JudgeTitleName(todoEntity.getTodo_title())!=null){
            List<TodoEntity> todos = todoService.findAll();
            String error = "同じTodo名があります";
            model.addAttribute("todos", todos);
            model.addAttribute("error", error);
            return true;
        }
        return false;
    }
    public boolean judgeTime(TodoEntity todoEntity, TodoService todoService, Model model) throws ParseException {
        Date nowDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Date date = format.parse(todoEntity.getDeadline().replaceAll("-","/"));
        format.applyPattern("yyyy年MM月dd日");
        //期限を過去にしていないか判定
        if(format.format(nowDate).compareTo(format.format(date))>0) {
            List<TodoEntity> todos = todoService.findAll();
            String error = "未来の日付にしてください";
            model.addAttribute("todos", todos);
            model.addAttribute("error", error);
            return true;
        }
        return false;
    }
}
