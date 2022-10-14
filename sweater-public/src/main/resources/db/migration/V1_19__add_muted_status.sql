alter table usr add column muted boolean;

update usr set muted = false;