create table dbo."order" (
     id UNIQUEIDENTIFIER not null,
     created_at datetime null,
     updated_at datetime null,
     pay_method varchar(255) null,
     total numeric(19, 2) null,
     tracking_code varchar(255) null,
     id_address UNIQUEIDENTIFIER not null,
     id_customer UNIQUEIDENTIFIER not null,
     primary key (id),
     FOREIGN KEY (id_customer) REFERENCES customer(id),
     FOREIGN KEY (id_address) REFERENCES address(id)
);
