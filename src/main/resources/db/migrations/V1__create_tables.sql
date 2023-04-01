create table users(
  id integer not null generated always as identity primary key,
  name varchar,
  user_name varchar not null unique,
  email varchar not null unique,
  created timestamptz,
  updated timestamptz default now()
);

create table entry_types(
  id integer not null generated always as identity primary key,
  name varchar not null unique,
  created timestamptz,
  updated timestamptz default now()
);

create table categories(
  id integer not null generated always as identity primary key,
  name varchar not null unique,
  created timestamptz,
  updated timestamptz default now()
);

create table entries(
  id integer not null generated always as identity primary key,
  title varchar not null unique,
  slug varchar not null unique,
  users_id integer not null references users(id),
  version integer,
  entry_types_id integer not null references entry_types(id),
  body varchar not null,
  tags varchar[],
  categories_id integer not null references categories(id),
  published boolean default false,
  created timestamptz,
  updated timestamptz default now()
);