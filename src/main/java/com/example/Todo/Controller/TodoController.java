package com.example.Todo.Controller;


import java.text.ParseException;
import java.util.List;

import com.example.Todo.Entity.TodoEntity;
import com.example.Todo.Service.TodoService;
import com.example.Todo.function.EditRecord;
import com.example.Todo.function.FlagJudge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import java.text.SimpleDateFormat;

@Controller
public class TodoController{
    //logを取得する変数
    private static final Logger log = LoggerFactory.getLogger(TodoController.class);

    @Autowired
    private TodoService todoService;

    //トップ画面
    @GetMapping("/")
    public String top(@ModelAttribute("todoEntity") TodoEntity todoEntity, Model model) {
        List<TodoEntity> todos = todoService.findAll();
        String judge =todoService.getCount();
        if(judge.equals("0")){
            judge = "登録されたTodoはございません";
            model.addAttribute("judge", judge);
        }
        model.addAttribute("todos", todos);
        return "index";
    }
    //Todoを追加
    @PostMapping(value="/add")
    public  String addNewRecord(@Validated @ModelAttribute("todoEntity") TodoEntity todoEntity, BindingResult result, Model model) throws ParseException {
        //エラー判定
        if (result.hasErrors()) {
            for(FieldError err: result.getFieldErrors()) {
                log.debug(err.getCode());
            }
            List<TodoEntity> todos = todoService.findAll();
            model.addAttribute("todos", todos);
            return "index";
        }
        //日付を取得
        Date nowDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Date date = format.parse(todoEntity.getDeadline().replaceAll("-","/"));
        format.applyPattern("yyyy年MM月dd日");
        //同じTodo名があるか判定
        if(todoService.JudgeTitleName(todoEntity.getTodo_title())!=null){
            List<TodoEntity> todos = todoService.findAll();
            String error = "同じTodo名があります";
            model.addAttribute("todos", todos);
            model.addAttribute("error", error);
            return "index";
         //期限を過去にしていないか判定
        }else if(format.format(nowDate).compareTo(format.format(date))>0) {
            List<TodoEntity> todos = todoService.findAll();
            String error = "未来の日付にしてください";
            model.addAttribute("todos", todos);
            model.addAttribute("error", error);
            return "index";
        }
        todoEntity.setReporting_date(format.format(nowDate));
        todoEntity.setDeadline(format.format(date));
        todoService.save(todoEntity);
        return "redirect:/";
    }

    //編集画面
    @GetMapping("/compilation")
    public String getRecord(@RequestParam("id") String id,Model model) throws ParseException {
        //idにあったデータを取得する
        EditRecord.displayEdit(id,model,todoService);
        //トップ画面か検索画面かを判定するため
        model.addAttribute("url","/compilation");
        return "Edit";
    }
    //編集データを挿入
    @PostMapping("/compilation")
    public String editRecord(@RequestParam("todoID") String id,@RequestParam("todo_title") String title,@RequestParam("deadline") String deadline) throws ParseException {
        //idに当たるレコードをupdateする
        EditRecord.toEdit(id,title,deadline,todoService);
        return "redirect:/";
    }
    //完了判定
    @GetMapping("/topFlag")
    public String topFlag(@RequestParam("id")String id,@RequestParam("flag")Integer flag){
        todoService.changeFlag(id,FlagJudge.Judge(flag));
        return "redirect:/";
    }
}
