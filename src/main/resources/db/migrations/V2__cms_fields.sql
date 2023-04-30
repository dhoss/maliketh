create table cms_fields(
  id integer not null generated always as identity primary key,
  key varchar unique not null,
  value varchar not null,
  created timestamptz,
  updated timestamptz default now()
);