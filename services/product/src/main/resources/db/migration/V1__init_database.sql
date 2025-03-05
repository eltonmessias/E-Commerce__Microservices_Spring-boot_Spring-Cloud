CREATE TABLE IF NOT EXISTS category (
                                        id UUID NOT NULL PRIMARY KEY,
                                        description VARCHAR(255),
    name VARCHAR(255)
    );

-- Criar a tabela Product garantindo que a chave estrangeira category_id seja UUID
CREATE TABLE IF NOT EXISTS product (
                                       id UUID NOT NULL PRIMARY KEY,
                                       available_quantity INTEGER,
                                       description VARCHAR(255),
    name VARCHAR(255),
    price NUMERIC(38, 2),
    category_id UUID NOT NULL, -- Alterado para UUID
    CONSTRAINT fk_product_category FOREIGN KEY (category_id) REFERENCES category(id)
    );