package com.example.Todo.Repository;

import java.util.List;

import com.example.Todo.Entity.TodoEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, Integer> {
    @Query(value = "select * from todolist", nativeQuery = true)
    public List<TodoEntity> findAll();

    @Query(value = "Select * from todolist where todo_title like %:title% and flag = 0 ORDER By todoID DESC ",nativeQuery = true)
    public List<TodoEntity> findMatch(@Param("title")String title);

    @Query(value="Select * from todolist where todoID = :id",nativeQuery = true)
    public TodoEntity findOne(@Param("id")String id);

    @Modifying
    @Query(value="Update todolist set todo_title = :title, deadline = :deadline where todoID = :id",nativeQuery = true)
    public void update(@Param("id")String id,@Param("title")String title,@Param("deadline")String deadline);

    @Modifying
    @Query(value="Update todolist set flag = :flag where todoID = :id",nativeQuery = true)
    public void changeFlag(@Param("id")String id,@Param("flag")int flag);

    @Query(value="select count(*) from todolist where todo_title like %:title% and flag = 0 ",nativeQuery = true)
    public String getCount(@Param("title")String title);

    @Query(value="select count(*) from todolist ",nativeQuery = true)
    public String getCount();

    @Query(value="select todo_title from todolist where todo_title = :title",nativeQuery = true)
    public String JudgeTitleName(@Param("title")String title);

}
