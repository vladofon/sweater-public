create table comment
(
	id int8 not null,
	text varchar(2048) not null,
	message_id int8 not null,
	user_id int8 not null,
	photo_id int8,
	primary key(id)
);

alter table if exists comment add constraint comment_photo_fk foreign key (photo_id) references photo;
alter table if exists comment add constraint comment_user_fk foreign key (user_id) references usr;
alter table if exists comment add constraint comment_message_fk foreign key (message_id) references message;