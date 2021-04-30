CREATE TABLE public.customer (
    id uuid NOT NULL,
    created_at timestamp NULL,
    updated_at timestamp NULL,
    "name" varchar(255) NULL,
    email varchar(255) NULL,
    "document" varchar(255) NULL,
    active bool NULL DEFAULT true,
    birthdate date NOT NULL,
    cellphone varchar(255) NULL,
    "password" varchar(255) NOT NULL,
    CONSTRAINT customer_document_key UNIQUE (document),
    CONSTRAINT customer_pkey PRIMARY KEY (id)
);


CREATE TABLE public.address (
    id uuid NOT NULL,
    created_at timestamp NULL,
    updated_at timestamp NULL,
    street varchar(255) NULL,
    "number" varchar(255) NULL,
    complement varchar(255) NULL,
    zipcode varchar(255) NULL,
    city varchar(255) NULL,
    state varchar(255) NULL,
    id_customer uuid NULL,
    CONSTRAINT address_pkey PRIMARY KEY (id)
);
