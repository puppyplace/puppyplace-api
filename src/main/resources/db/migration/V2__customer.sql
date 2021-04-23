CREATE TABLE public.customer (
	id uuid NOT NULL,
	created_at timestamp NULL,
	updated_at timestamp NULL,
	"name" varchar(255) NULL,
	"email" varchar(255) NULL,
	"document" varchar(255) NULL,
	CONSTRAINT customer_pkey PRIMARY KEY (id)
);


create table public.address(
	id uuid NOT NULL,
	created_at timestamp NULL,
	updated_at timestamp NULL,
	"street" varchar(255) NULL,
	"number" varchar(255) NULL,
	"zipcode" varchar(255) NULL,
	"state" varchar(255) NULL,
	id_customer uuid NULL,
	CONSTRAINT address_pkey PRIMARY KEY (id),
	CONSTRAINT customer_id_fk_address FOREIGN KEY (id_customer) REFERENCES customer(id)
);

