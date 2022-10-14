alter table if exists message add column photo_id int8;

create table photo (
	id int8 not null, 
	create_at timestamp, 
	image varchar(255), 
	title varchar(255), 
	primary key (id)
);

alter table if exists message add constraint message_photo_fk foreign key (photo_id) references photo;