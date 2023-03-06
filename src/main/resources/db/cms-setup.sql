begin;

insert into users(name, user_name, email)
values('devin', 'dja', 'devin.austin@gmail.com')
on conflict(email) do update set updated = now();

insert into entry_types(name)
values
 ('post'),
 ('page')
on conflict (name) do update set updated = now();

insert into categories(name)
values
 ('tech'),
 ('fitness')
on conflict (name) do update set updated = now();

commit;