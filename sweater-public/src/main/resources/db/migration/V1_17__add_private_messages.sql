create table private_message
(
	id int8,
	sender_id int8 references usr (id),
	reciever_id int8 references usr (id),
	text varchar(2048),
	posted_at time without time zone default (now() at time zone 'utc'),
	primary key(id)
);

