create table public.order (
  id uuid not null,
  created_at timestamp,
  updated_at timestamp,
  pay_method varchar(255),
  total numeric(19, 2),
  tracking_code varchar(255),
  id_address uuid not null,
  id_customer uuid not null,
  primary key (id)
);
ALTER TABLE public.order ADD FOREIGN KEY (id_customer) REFERENCES public.customer(id);
ALTER TABLE public.order ADD FOREIGN KEY (id_address) REFERENCES public.address(id);
