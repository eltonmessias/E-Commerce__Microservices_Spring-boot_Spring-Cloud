CREATE TABLE IF NOT EXISTS category (
                                        id BIGINT NOT NULL PRIMARY KEY,
                                        description VARCHAR(255),
    name VARCHAR(255)
    );

CREATE TABLE IF NOT EXISTS product (
                                       id BIGINT NOT NULL PRIMARY KEY,
                                       available_quantity INTEGER,
                                       description VARCHAR(255),
    name VARCHAR(255),
    price numeric(38, 2),
    category_id BIGINT NOT NULL,  -- ALTERADO: Agora está como BIGINT
    CONSTRAINT fk_product_category FOREIGN KEY (category_id) REFERENCES category(id) -- ALTERADO: Nome descritivo
    );

CREATE SEQUENCE IF NOT EXISTS category_seq INCREMENT BY 50;
CREATE SEQUENCE IF NOT EXISTS product_seq INCREMENT BY 50;
