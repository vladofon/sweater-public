create table user_subscriptions
(
	channel_id int8,
	subscriber_id int8,
	primary key(channel_id, subscriber_id)
);

alter table if exists user_subscriptions 
	add constraint subscriptions_channel_fk 
	foreign key (channel_id) 
	references usr;

alter table if exists user_subscriptions 
	add constraint subscriptions_subscriber_fk 
	foreign key (subscriber_id) 
	references usr;