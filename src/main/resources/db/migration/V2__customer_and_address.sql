CREATE TABLE dbo.customer (
    id uniqueidentifier NOT NULL,
    created_at datetime NULL,
    updated_at datetime NULL,
    "name" varchar(255) NULL,
    email varchar(255) NULL,
    "document" varchar(255) NULL,
    active BIT NULL DEFAULT 1,
    birthdate date NOT NULL,
    cellphone varchar(255) NULL,
    "password" varchar(255) NOT NULL,
    CONSTRAINT customer_document_key UNIQUE (document),
    CONSTRAINT customer_pkey PRIMARY KEY (id)
);


CREATE TABLE dbo.address (
    id uniqueidentifier NOT NULL,
    created_at datetime NULL,
    updated_at datetime NULL,
    street varchar(255) NULL,
    "number" varchar(255) NULL,
    complement varchar(255) NULL,
    zipcode varchar(255) NULL,
    city varchar(255) NULL,
    state varchar(255) NULL,
    id_customer UNIQUEIDENTIFIER NULL,
    CONSTRAINT address_pkey PRIMARY KEY (id)
);
