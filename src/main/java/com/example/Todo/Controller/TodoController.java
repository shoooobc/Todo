package com.example.Todo.Controller;


import java.text.ParseException;
import java.util.List;

import com.example.Todo.Entity.TodoEntity;
import com.example.Todo.Service.TodoService;
import com.example.Todo.function.EditRecord;
import com.example.Todo.function.FlagJudge;

import com.example.Todo.function.TodoJudge;
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
    @Autowired
    private TodoService todoService;
    //トップ画面
    @GetMapping("/")
    public String top(@ModelAttribute("todoEntity") TodoEntity todoEntity, Model model) {
        String judge =todoService.getCount();
        if(judge.equals("0")){
            judge = "登録されたTodoはございません";
            model.addAttribute("judge", judge);
            return "index";
        }
        List<TodoEntity> todos = todoService.findAll();
        model.addAttribute("todos", todos);
        return "index";
    }
    //Todoを追加
    @PostMapping(value="/add")
    public  String addNewRecord(@Validated @ModelAttribute("todoEntity") TodoEntity todoEntity, BindingResult result, Model model) throws ParseException {
        //エラー判定
        if (result.hasErrors()) {
            List<TodoEntity> todos = todoService.findAll();
            model.addAttribute("todos", todos);
            return "index";
        }
//        //日付を取得
        Date nowDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Date date = format.parse(todoEntity.getDeadline().replaceAll("-","/"));
        format.applyPattern("yyyy年MM月dd日");
        if(TodoJudge.getInstance().judgeTime(todoEntity,todoService,model)||TodoJudge.getInstance().judgeTitle(todoEntity,todoService,model)){
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
        model.addAttribute("url","/updateCompilation");
        return "Edit";
    }
    //編集データを挿入
    @PostMapping("/updateCompilation")
    public String editRecord(@Validated @ModelAttribute("todo") TodoEntity todoEntity, BindingResult result,Model model) throws ParseException {
        if (result.hasErrors()||TodoJudge.getInstance().judgeTime(todoEntity,todoService,model)) {
            return "Edit";
        }
        //idに当たるレコードをupdateする
        EditRecord.toEdit(String.valueOf(todoEntity.getId()),todoEntity.getTodo_title(),todoEntity.getDeadline(),todoService);
        return "redirect:/";
    }
    //完了判定
    @GetMapping("/topFlag")
    public String topFlag(@RequestParam("id")String id,@RequestParam("flag")Integer flag){
        todoService.changeFlag(id,FlagJudge.Judge(flag));
        return "redirect:/";
    }
}
