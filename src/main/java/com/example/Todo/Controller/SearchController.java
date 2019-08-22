package com.example.Todo.Controller;

import com.example.Todo.Entity.TodoEntity;
import com.example.Todo.Entity.TodoForm;
import com.example.Todo.Service.TodoService;
import com.example.Todo.function.EditRecord;
import com.example.Todo.function.FlagJudge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping("/Search")
public class SearchController {


    @Autowired
    private TodoService todoService;


    @Autowired
    private HttpSession session;


    private static final String SESSION_FORM_ID="TodoForm";


    ModelAndView mv = new ModelAndView();

    //検索画面
    @GetMapping
    public ModelAndView Search(){
        mv.setViewName("Search");
        mv.clear();
        mv.addObject("TodoForm",new TodoForm());
        return mv;
    }

    //検索結果
    @RequestMapping(value="/todo_title",method=RequestMethod.POST)
    public ModelAndView search(@ModelAttribute(SESSION_FORM_ID) TodoForm form){
        //編集後と完了判定後に同じ検索内容が表示されるように値を保持させる
        session.setAttribute(SESSION_FORM_ID,form);
        //該当の件数を取得
        String count = todoService.getCount(form.getTitle());
        //検索対象のデータを取得する
        List<TodoEntity> todos = todoService.findMatch(form.getTitle());
        //表示させる内容を判定
        if(count.equals("0")){
            count = "対象のTodoが見つかりません";
        }else{
            count = "Todoが"+count+"件見つかりました";
        }

        mv.setViewName("Search");
        mv.addObject("todos",todos);
        mv.addObject("count",count);
        return mv;
    }

    //完了判定
    @RequestMapping(value ="/changeFlag",method=RequestMethod.GET)
    public ModelAndView changeFlag(@RequestParam("id") String id, @RequestParam("flag")Integer flag) {
        todoService.changeFlag(id, FlagJudge.Judge(flag));
        return search((TodoForm)session.getAttribute(SESSION_FORM_ID));
    }

    //編集画面
    @RequestMapping(value="/compilation", method=RequestMethod.GET)
    public String getRecord(@RequestParam("id") String id, Model model) throws ParseException {
        EditRecord.displayEdit(id,model,todoService);
        //トップ画面か検索画面かを判定するため
        model.addAttribute("url","/Search/compilation");
        return "Edit";
    }

    //編集データを挿入
    @RequestMapping(value="/compilation",method =RequestMethod.POST)
    public ModelAndView editRecord(@RequestParam("todoID") String id,@RequestParam("todo_title") String title,@RequestParam("deadline") String deadline) throws ParseException {
        EditRecord.toEdit(id,title,deadline,todoService);
        return search((TodoForm)session.getAttribute(SESSION_FORM_ID));
    }
}
