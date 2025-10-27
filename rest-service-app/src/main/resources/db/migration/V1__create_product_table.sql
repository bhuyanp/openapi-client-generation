CREATE TABLE IF NOT EXISTS product (
   id uuid NOT NULL DEFAULT gen_random_uuid() ,
   title varchar(250) NOT NULL,
   type varchar(50) NOT NULL,
   price float(53) NOT NULL,
   created_date timestamp NOT NULL,
   updated_date timestamp,
   PRIMARY KEY (id)
);