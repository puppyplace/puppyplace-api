CREATE TABLE dbo.product_variant (
   id UNIQUEIDENTIFIER NOT NULL,
   id_product UNIQUEIDENTIFIER,
   stock INT NOT NULL,
   price numeric(19,2) NOT NULL,
   percent_promotional numeric(19,2) NULL,
   isbn_code varchar(255) NOT NULL,
   unit varchar(255) NOT NULL,
   created_at datetime NULL,
   updated_at datetime NULL,
   PRIMARY KEY (id),
   FOREIGN KEY (id_product) REFERENCES product(id)
);
