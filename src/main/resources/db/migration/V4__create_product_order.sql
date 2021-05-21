CREATE TABLE public.product_order (
      id uuid NOT NULL,
      created_at timestamp NULL,
      updated_at timestamp NULL,
      quantity int4 NULL,
      total_price numeric(19,2) NULL,
      unit_price numeric(19,2) NULL,
      id_order uuid NOT NULL,
      id_product uuid NOT NULL,
      PRIMARY KEY (id)
);
ALTER TABLE public.product_order ADD FOREIGN KEY (id_order) REFERENCES public.order(id);
ALTER TABLE public.product_order ADD FOREIGN KEY (id_product) REFERENCES public.product(id);