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
    "version"
  )
select
    'test body ' || now(),
    (select id from categories order by random() limit 1),
    now(),
    (select id from entry_types where name='post'),
    true,
    'test-title-' || now(),
    '{"test", "post"}',
    'test title ' || now(),
    1,
    --(select id from users where user_name='dja'),
    1
from
    generate_series(1, 100) s(i);