-- USERS TABLES

SET NON_KEYWORDS VALUE;

CREATE TABLE IF NOT EXISTS users (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    avatar VARCHAR(100),
    first_name VARCHAR(150),
    last_name VARCHAR(150),
    username VARCHAR(150),
    email VARCHAR(150),
    password VARCHAR(100),
    birth_of_date DATE,
    phone_number VARCHAR(100),
    enabled BOOLEAN,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS roles (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS users_roles (
  user_id BIGINT,
  role_id BIGINT,
  PRIMARY KEY (user_id, role_id),
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS addresses (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    title VARCHAR(150),
    addresses_line_1 VARCHAR(150),
    addresses_line_2 VARCHAR(150),
    country VARCHAR(150),
    city VARCHAR(100),
    commune VARCHAR(100),
    postal_code VARCHAR(100),
    landmark VARCHAR(150),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
);

-- PRODUCTS TABLES

CREATE TABLE IF NOT EXISTS categories (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    parent BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS brands (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    logo_url TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS products (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(150) NOT NULL UNIQUE,
  description TEXT,
  category_id BIGINT,
  brand_id BIGINT,
  cover TEXT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (category_id) REFERENCES categories(id),
  FOREIGN KEY (brand_id) REFERENCES brands(id)
);

CREATE TABLE IF NOT EXISTS product_attributes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(255) NOT NULL,
    value VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS product_skus (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT,
    size_attribute_id BIGINT,
    color_attribute_id BIGINT,
    sku VARCHAR(200) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    is_featured BOOLEAN DEFAULT FALSE,
    is_on_sale BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (size_attribute_id) REFERENCES product_attributes(id),
    FOREIGN KEY (color_attribute_id) REFERENCES product_attributes(id)
);

CREATE TABLE IF NOT EXISTS discount_product_sku (
    discount_id BIGINT,
    product_sku_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (product_sku_id) REFERENCES product_skus(id)
);

CREATE TABLE IF NOT EXISTS wishlist (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    product_sku_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (product_sku_id) REFERENCES product_skus(id)
);

CREATE TABLE IF NOT EXISTS product_gallery (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT,
    color_attribute_id BIGINT,
    image_url VARCHAR(200),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (color_attribute_id) REFERENCES product_attributes(id)
);

CREATE TABLE IF NOT EXISTS discounts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    discount_type VARCHAR(80),
    discount_value DECIMAL,
    start_date TIMESTAMP,
    end_date TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS discount_codes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(60) UNIQUE,
    discount_id BIGINT,
    usage_limit INTEGER,
    used_count INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (discount_id) REFERENCES discounts(id) ON DELETE CASCADE
);