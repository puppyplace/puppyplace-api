CREATE TABLE public."lead" (
	id uuid NOT NULL,
	nome varchar NOT NULL,
	email varchar NOT NULL,
	CONSTRAINT lead_pk PRIMARY KEY (id)
);