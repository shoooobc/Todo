package com.example.Todo.Entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Entity
@Table(name="todolist")
public class TodoEntity implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="todoID")
    private Integer todoID;
    @Column(name ="todo_title")
    @NotEmpty
    @Size(max = 30)
    private String todo_title;
    @Column(name ="reporting_date")
    private String reporting_date;
    @Column(name ="deadline")
    @NotEmpty
    private String deadline;
    @Column(name ="flag")
    private int  flag;

    public TodoEntity(){}

    public void setId(Integer todoID) {
        this.todoID = todoID;
    }

    public Integer getId() {
        return todoID;
    }

    public void setTodo_title(String todo_title) {
        this.todo_title = todo_title;
    }

    public String getTodo_title() {
        return todo_title;
    }

    public void setReporting_date(String reporting_date) {
        this.reporting_date = reporting_date;
    }

    public String getReporting_date() {
        return reporting_date;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getFlag() {
        return flag;
    }
}
