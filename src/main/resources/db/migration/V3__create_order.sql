create table order (
  id UNIQUEIDENTIFIER not null,
  created_at datetime,
  updated_at datetime,
  pay_method varchar(255),
  total numeric(19, 2),
  tracking_code varchar(255),
  id_address UNIQUEIDENTIFIER not null,
  id_customer UNIQUEIDENTIFIER not null,
  primary key (id),
  FOREIGN KEY (id_customer) REFERENCES customer(id),
  FOREIGN KEY (id_address) REFERENCES address(id)
);
