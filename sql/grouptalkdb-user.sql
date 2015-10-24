drop user 'administrador'@'localhost';
create user 'administrador'@'localhost' identified by '1234';
grant all privileges on grouptalkdb.* to 'administrador'@'localhost';
flush privileges;
