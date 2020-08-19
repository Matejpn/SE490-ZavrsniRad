INSERT IGNORE INTO role (id, name) VALUES (1, 'ADMIN'),(2, 'USER');

INSERT IGNORE INTO user (id, email, first_name, is_active, is_verified, last_name, password, role_id) VALUES (1, 'admin@mailinator.com', 'Admin', 1, 1, 'Admin', '$2a$10$l9xkDM7mV6ktcc4XZNnurOfDCSm79.IcY/CK/r0/rYO/MdAEp5hDe', 1);

INSERT IGNORE INTO size (id, value, type) VALUES ('5', '34', 'SHOES'),('6', '35', 'SHOES'),('7', '36', 'SHOES'),('8', '37', 'SHOES'),('9', '38', 'SHOES'),('10', '39', 'SHOES'),('11', '40', 'SHOES'),('12', '41', 'SHOES'),('13', '42', 'SHOES'),('14', '43', 'SHOES'),('15', '44', 'SHOES'),('16', '45', 'SHOES'),('17', '46', 'SHOES'),('18', '47', 'SHOES'),('19', '48', 'SHOES'),('20', 'S', 'CLOTHES'),('21', 'M', 'CLOTHES'),('22', 'L', 'CLOTHES'),('23', 'XL', 'CLOTHES'),('24', 'XXL', 'CLOTHES');

INSERT IGNORE INTO category (id, name) VALUES (1, 'SHOES'),(2, 'CLOTHES');

INSERT IGNORE INTO brand (id, name) VALUES (1, 'Nike'),(2, 'Adidas'),(2, 'Puma');
