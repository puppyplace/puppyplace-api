ALTER TABLE product_order
  ADD id_variant UNIQUEIDENTIFIER not null,
  CONSTRAINT fk3c6e0b8a9c15224a8228b9a98 FOREIGN KEY(id_variant) REFERENCES product_variant(id);
