create table page_fields(
  id integer not null generated always as identity primary key,
  data jsonb not null,
  created timestamptz default now(),
  updated timestamptz
);

insert into page_fields(data)
values('
{
	"site": {
		"description": "maliketh cms",
		"header": "maliketh cms"
	},
	"page": {
		"name": "home",
		"title": "home page"
	}
}
');