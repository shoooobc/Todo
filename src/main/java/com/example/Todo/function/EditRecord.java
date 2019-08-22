package com.example.Todo.function;

import com.example.Todo.Entity.TodoEntity;
import com.example.Todo.Service.TodoService;
import org.springframework.ui.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//編集画面で使うクラス
public class EditRecord {
    //値をupdateするときに使うメソッド
    public static void toEdit(String id, String title, String deadline,TodoService todoService) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Date date = format.parse(deadline.replaceAll("-","/"));
        format.applyPattern("yyyy年MM月dd日");
        todoService.update(id, title,format.format(date));
    }
    //編集画面で表示するときに使うメソッド
    public static void displayEdit(String id, Model model,TodoService todoService)throws ParseException{
        TodoEntity todo = todoService.findOne(id);
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = format.parse(todo.getDeadline());
        format.applyPattern("yyyy-MM-dd");
        todo.setDeadline(format.format(date));
        model.addAttribute("todo", todo);
    }
}
