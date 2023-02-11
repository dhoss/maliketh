create database maliketh;

create user maliketh with password 'maliketh';

grant all privileges on database "maliketh" to maliketh;

grant all on schema public to maliketh;