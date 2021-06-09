CREATE TABLE dbo.category (
	id  UNIQUEIDENTIFIER NOT NULL UNIQUE,
	created_at datetime NULL,
	updated_at datetime NULL,
	name varchar(255) NULL,
	CONSTRAINT category_pkey PRIMARY KEY (id)
);

CREATE TABLE dbo.lead (
	id  UNIQUEIDENTIFIER NOT NULL UNIQUE,
	created_at datetime NULL,
	updated_at datetime NULL,
	email varchar(255) NULL,
	"name" varchar(255) NULL,
	CONSTRAINT lead_pkey PRIMARY KEY (id)
);


CREATE TABLE dbo.partner (
	id  UNIQUEIDENTIFIER ROWGUIDCOL NOT NULL UNIQUE,
	created_at datetime NULL,
	updated_at datetime NULL,
	"name" varchar(255) NULL,
	CONSTRAINT partner_pkey PRIMARY KEY (id)
);


CREATE TABLE dbo.product (
	id  UNIQUEIDENTIFIER ROWGUIDCOL NOT NULL UNIQUE,
	created_at datetime NULL,
	updated_at datetime NULL,
	avatar_url varchar(255) NULL,
	deleted BIT NULL DEFAULT 0,
	deleted_at datetime NULL,
	description varchar(255) NOT NULL,
	isbn_code varchar(255) NULL,
	price money NOT NULL,
	product_code varchar(255) NULL,
	promotional_price money NULL,
	specifications varchar(255) NULL,
	stock float NOT NULL,
	title varchar(255) NOT NULL,
	unit varchar(255) NULL,
	id_partner uniqueidentifier NULL,
	CONSTRAINT product_pkey PRIMARY KEY (id),
	CONSTRAINT uk_hcpr86kgtroqvxu1mxoyx4ahm UNIQUE (product_code),
	CONSTRAINT fk4ig7yip2wwiyortskq4g6x0yq FOREIGN KEY (id_partner) REFERENCES partner(id)
);


CREATE TABLE dbo.category_product (
	product_id uniqueidentifier NOT NULL,
	category_id uniqueidentifier NOT NULL,
	CONSTRAINT fk5ps5w0dm3cg4cpqy1rkkc9stb FOREIGN KEY (product_id) REFERENCES product(id),
	CONSTRAINT fkfq3ughhuv8mc9baqffys8itkx FOREIGN KEY (category_id) REFERENCES category(id)
);