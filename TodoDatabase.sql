create table todolist(
  todoID int AUTO_INCREMENT NOT NULL PRIMARY KEY,
  todo_title VARCHAR(60) UNIQUE NOT NULL,
  reporting_date VARCHAR(15) not null ,
  deadline  VARCHAR(15) not null,
  flag int(1) not null default 0
);