create table message_likes
(
	message_id int8,
	user_id int8,
	primary key(message_id, user_id)
);

alter table if exists message_likes 
	add constraint message_likes_message_fk 
	foreign key (message_id) 
	references message;

alter table if exists message_likes 
	add constraint message_likes_user_fk
	foreign key (user_id) 
	references usr;