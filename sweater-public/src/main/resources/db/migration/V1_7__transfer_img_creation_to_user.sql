ALTER TABLE usr ADD COLUMN photo_id int8;

ALTER TABLE usr
    ADD CONSTRAINT user_photo_fk FOREIGN KEY (photo_id) REFERENCES photo (id);