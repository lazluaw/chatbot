create table chat (
chat_idx int auto_increment,
insert_date datetime,
update_date datetime,
chat_kind varchar(1),
chat_body text,
constraint chat_pk primary key (chat_idx)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci;

create table chathistory (
history_idx int auto_increment,
chat_idx int,
chat_body text,
insert_date datetime,
update_date datetime,
constraint history_pk primary key (history_idx),
constraint history_fk foreign key (chat_idx) references chat(chat_idx)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci;

create table product(
product_idx int auto_increment,
chat_idx int,
sku varchar(20),
price float,
constraint product_pk primary key (product_idx),
constraint product_fk foreign key (chat_idx) references chat(chat_idx)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci;