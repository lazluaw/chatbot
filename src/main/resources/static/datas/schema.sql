-- 기본 --
create table chat (
chat_id int auto_increment primary key,
user_code int not null,
insert_date timestamp not null,
update_date timestamp not null,
chat_kind varchar(1) not null,
chat_body text not null,
constraint chat_fk foreign key (user_code) references user(user_code)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci;

create table chathistory (
history_id int auto_increment primary key,
chat_id int null,
user_info varchar(1000) not null,
chat_body text not null,
insert_date timestamp not null,
update_date timestamp not null,
constraint history_fk foreign key (chat_id) references chat(chat_id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci;

-- 기능 --
create table exam (
exam_id int auto_increment primary key,
exam_kind int not null,
subject_code varchar(30) not null,
exam_num int not null,
exam_question varchar(1000) not null,
exam_division char(1) not null,
exam_choice1 varchar(100) not null,
exam_choice2 varchar(100) not null,
exam_choice3 varchar(100) not null,
exam_choice4 varchar(100) not null,
exam_choice5 varchar(100) not null,
exam_answer varchar(200) not null,
exam_content varchar(1000) not null,
insert_date timestamp not null
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci;

create table examanalysis (
analysis_id int auto_increment primary key,
user_code int not null,
subject_code varchar(10) not null,
exam_kind int not null,
exam_num int not null,
wrong_answer varchar(200) not null,
insert_date timestamp not null,
constraint examanalysis_fk foreign key (user_code) references user(user_code)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci;


--연관 관계--
CREATE TABLE user
(
    `id`                INT            NOT NULL    AUTO_INCREMENT,
    `user_code`         INT            NOT NULL UNIQUE,
    `school_code`       INT            NOT NULL,
    `cur_grade`         INT            NULL,
    `home_class`        INT            NULL,
    `name`              VARCHAR(9)     NOT NULL,
    `user_id`           VARCHAR(20)    NOT NULL,
    `user_pw`           VARCHAR(20)    NOT NULL,
    `email`             VARCHAR(50)    NULL,
    `phone`             VARCHAR(13)    NULL,
    `gardian_phone`     VARCHAR(13)    NULL,
    `gardian_relation`  VARCHAR(2)     NULL,
    `gender`            VARCHAR(1)     NULL,
    `position_checker`  int            NOT NULL,
    `insert_date`       DATETIME       NULL,
    `update_date`       DATETIME       NULL,
    PRIMARY KEY (id)
)ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci;