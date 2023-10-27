INSERT INTO users(username, confirmed, locked, password) VALUES
    ('admin', true, false, '$2a$10$enfwuy1Wo3utyFXdLK.bBO7UzZ7wEwkVb162HYfQp7RKlXoEHLimy');

INSERT INTO users_roles(users_username, roles) VALUES
   ('admin', 'ADMIN');

select *
from user_file;

update users set photo_key = null where true;

delete from user_file where true;