CREATE TABLE IF NOT EXISTS product (
   id uuid NOT NULL DEFAULT gen_random_uuid() ,
   title varchar(250) NOT NULL,
   type varchar(50) NOT NULL,
   price numeric NOT NULL,
   created_date date NOT NULL,
   updated_date date,
   PRIMARY KEY (id)
);