create table user (
userIdx int auto_increment not null,
userPlusfriendKey varchar(100) not null,
insertDate datetime not null,
updateDate datetime not null,
constraint user_pk primary key (userIdx)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci;

create table chatbot (
userIdx int not null,
chatbotIdx int auto_increment not null,
insertDate datetime not null,
updateDate datetime not null,
chatbotBody text not null,
constraint chatbot_pk primary key (chatbotIdx),
constraint chatbot_fk foreign key (userIdx) references user(userIdx)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci;

create table bicycle (
bicycleDate datetime not null,
chatbotIdx int not null,
insertDate datetime not null,
updateDate datetime not null,
constraint bicycle_pk primary key (bicycleDate),
constraint bicycle_fk foreign key (chatbotIdx) references chatbot(chatbotIdx)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci;