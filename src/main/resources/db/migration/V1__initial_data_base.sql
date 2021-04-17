CREATE TABLE public.category (
	id uuid NOT NULL,
	created_at timestamp NULL,
	updated_at timestamp NULL,
	"name" varchar(255) NULL,
	CONSTRAINT category_pkey PRIMARY KEY (id)
);

CREATE TABLE public."lead" (
	id uuid NOT NULL,
	created_at timestamp NULL,
	updated_at timestamp NULL,
	email varchar(255) NULL,
	"name" varchar(255) NULL,
	CONSTRAINT lead_pkey PRIMARY KEY (id)
);


CREATE TABLE public.partner (
	id uuid NOT NULL,
	created_at timestamp NULL,
	updated_at timestamp NULL,
	"name" varchar(255) NULL,
	CONSTRAINT partner_pkey PRIMARY KEY (id)
);


CREATE TABLE public.product (
	id uuid NOT NULL,
	created_at timestamp NULL,
	updated_at timestamp NULL,
	avatar_url varchar(255) NULL,
	deleted bool NULL DEFAULT false,
	deleted_at timestamp NULL,
	description varchar(255) NOT NULL,
	isbn_code varchar(255) NULL,
	price float4 NOT NULL,
	product_code varchar(255) NULL,
	promotional_price float4 NULL,
	specifications varchar(255) NULL,
	stock int4 NOT NULL,
	title varchar(255) NOT NULL,
	unit varchar(255) NULL,
	id_partner uuid NULL,
	CONSTRAINT product_pkey PRIMARY KEY (id),
	CONSTRAINT uk_hcpr86kgtroqvxu1mxoyx4ahm UNIQUE (product_code),
	CONSTRAINT fk4ig7yip2wwiyortskq4g6x0yq FOREIGN KEY (id_partner) REFERENCES partner(id)
);


CREATE TABLE public.category_product (
	products_id uuid NOT NULL,
	categories_id uuid NOT NULL,
	CONSTRAINT fk5ps5w0dm3cg4cpqy1rkkc9stb FOREIGN KEY (products_id) REFERENCES product(id),
	CONSTRAINT fkfq3ughhuv8mc9baqffys8itkx FOREIGN KEY (categories_id) REFERENCES category(id)
);