begin;
insert into
  categories(
      name
    , slug
    , created
  )
select
    md5(random() :: text),
    md5(random() :: text),
    now()
from
    generate_series(1, 100) s(i);

insert into entry_types(name, created)
values ('post', now()),
('page', now());

select * from entry_types;


insert into users(email, name, user_name, created)
values('devin.austin@gmail.com', 'devin', 'dja', now());


insert into
  entries(
    body,
    categories_id,
    created,
    entry_types_id,
    published,
    slug,
    tags,
    title,
    users_id,
    version)
select
    'test body ' || s,
    (select id from categories order by random() limit 1),
    now(),
    (select id from entry_types where name='post'),
     now(),
    'test-title-' || s,
    '{"test", "post"}',
    'test title ' || s,
    (select id from users where user_name='dja'),
    1
from
    generate_series(1, 100) s(i);
commit;