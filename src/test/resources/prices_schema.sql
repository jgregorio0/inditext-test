CREATE TABLE IF NOT EXISTS prices (
   id INT PRIMARY KEY,
   brand_id INT NOT NULL,
   start_date TIMESTAMP NOT NULL,
   end_date TIMESTAMP NOT NULL,
   price_list INT NOT NULL,
   product_id INT NOT NULL,
   priority INT NOT NULL,
   price DECIMAL(4, 2) NOT NULL,
   currency VARCHAR(3) NOT NULL
);
