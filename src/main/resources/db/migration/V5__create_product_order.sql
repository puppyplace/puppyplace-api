CREATE TABLE dbo.product_order (
   id UNIQUEIDENTIFIER NOT NULL,
   created_at datetime NULL,
   updated_at datetime NULL,
   quantity int NULL,
   total_price numeric(19,2) NULL,
   unit_price numeric(19,2) NULL,
   id_order UNIQUEIDENTIFIER NOT NULL,
   id_product UNIQUEIDENTIFIER NOT NULL,
   PRIMARY KEY (id),
   FOREIGN KEY (id_order) REFERENCES "order"(id),
   FOREIGN KEY (id_product) REFERENCES product(id)
);