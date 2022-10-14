create table subscription_messages
(
	subscriber_id int8 references usr(id),
	message_id int8 references message(id),
	primary key(subscriber_id, message_id)
);