    --CREATE DATABASE warehouse;
    SELECT 'CREATE DATABASE warehouse'
    WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'warehouse')\gexec

    -- Create the categories table
    CREATE TABLE IF NOT EXISTS categories (
        category_id SERIAL PRIMARY KEY,
        label VARCHAR(50) NOT NULL UNIQUE,
        description VARCHAR(255) NOT NULL
    );

    -- Insert initial data into categories (5 lines)
    INSERT INTO categories (label, description) VALUES
        ('Standard', 'Non-specific goods with no special restrictions'),
        ('Fragile', 'Goods requiring delicate handling'),
        ('Dangerous', 'Dangerous goods according to ADR standards'),
        ('Perishable', 'Goods sensitive to temperature or storage duration'),
        ('Heavy', 'High-weight goods requiring special equipment');

    -- Create the dimensions table
    CREATE TABLE IF NOT EXISTS dimensions (
        dimension_id SERIAL PRIMARY KEY,
        label VARCHAR(50) NOT NULL UNIQUE,
        width INTEGER NOT NULL CHECK (width > 0),
        length INTEGER NOT NULL CHECK (length > 0)
    );

    -- Insert initial data into dimensions (5 lines)
    INSERT INTO dimensions (label, width, length) VALUES
        ('Euro Palette', 800, 1200),
        ('American Palette', 1016, 1219),
        ('Half-Palette', 600, 800),
        ('Industrial Palette', 1000, 1200),
        ('CP1 Palette', 1000, 1200);

    -- Create the items table
    CREATE TABLE IF NOT EXISTS items (
        item_id SERIAL PRIMARY KEY,
        label VARCHAR(50) NOT NULL UNIQUE,
        description VARCHAR(255) NOT NULL,
        weight NUMERIC NOT NULL CHECK (weight > 0)
    );

    -- Insert initial data into items (20 lines)
    INSERT INTO items (label, description, weight) VALUES
        ('Smartphone X1', 'Smartphone 5G avec écran OLED 6,5 pouces', 0.2),
        ('Ordinateur portable Z2', 'PC portable 16 Go RAM, SSD 512 Go', 1.8),
        ('Casque Bluetooth B3', 'Casque sans fil avec réduction de bruit', 0.3),
        ('Téléviseur LED T4', 'TV 4K 55 pouces avec HDR', 15.0),
        ('Réfrigérateur R5', 'Réfrigérateur 400L avec congélateur', 80.0),
        ('Lave-linge L6', 'Machine à laver 8 kg, 1400 tr/min', 65.0),
        ('Café Arabica C7', 'Café en grains 1 kg, torréfaction moyenne', 1.0),
        ('Chocolat Noir CH8', 'Tablettes de chocolat noir 70%, 200g', 0.2),
        ('Riz Basmati R9', 'Riz basmati premium, sac de 5 kg', 5.0),
        ('Huile d''Olive H10', 'Huile d''olive vierge extra, bouteille 1L', 1.0),
        ('T-shirt Coton T11', 'T-shirt en coton bio, taille M', 0.15),
        ('Jean Slim J12', 'Jean slim fit, bleu foncé, taille 32', 0.5),
        ('Chaussures Running C13', 'Chaussures de course, pointure 42', 0.8),
        ('Veste Polaire V14', 'Veste polaire chaude, taille L', 0.6),
        ('Livre Roman L15', 'Roman policier, édition brochée 300 pages', 0.4),
        ('Puzzle 1000 P16', 'Puzzle 1000 pièces, paysage montagneux', 0.7),
        ('Jouet Robot J17', 'Robot interactif pour enfants, 3+ ans', 1.2),
        ('Parfum Floral P18', 'Eau de parfum 50 ml, notes florales', 0.1),
        ('Batterie Externe B19', 'Batterie portable 10000 mAh, USB-C', 0.25),
        ('Lampe LED L20', 'Lampe de bureau LED, 10W, lumière réglable', 1.5);

    -- Create the receptions table
    CREATE TABLE IF NOT EXISTS receptions (
        id INT PRIMARY KEY,
        reference INT,
        orderInitiator VARCHAR(50),
        shiper VARCHAR(50),
        consignee VARCHAR(50),
        shippingAddress VARCHAR(50),
        deliveryAddress VARCHAR(50),
        createdAt DATE,
        updatedAt DATE
    );

    -- Insert initial data into receptions (10 lines)
    INSERT INTO receptions (id, reference, orderInitiator, shiper, consignee, shippingAddress, deliveryAddress, createdAt, updatedAt) VALUES
    (1, 751788, 'Photolist', 'Jabberbean', 'Oodoo', '9 Columbus Road', '5797 Kim Street', '2024-12-15', '2024-12-17'),
    (2, 422443, 'Zooxo', 'Yodo', 'Thoughtworks', '089 Bellgrove Parkway', '4 Paget Terrace', '2024-05-04', '2024-05-14'),
    (3, 328332, 'Edgewire', 'Aimbu', 'Skalith', '01173 Johnson Crossing', '814 Gale Drive', '2024-07-03', '2024-07-12'),
    (4, 232714, 'Browsebug', 'Edgeify', 'Livetube', '920 Evergreen Pass', '99805 Bayside Avenue', '2024-11-10', '2024-11-15'),
    (5, 426213, 'Trudoo', 'Gigaclub', 'Jabbertype', '0 Prairie Rose Junction', '6 Ludington Center', '2025-06-23', '2025-06-25'),
    (6, 830692, 'Youopia', 'Fanoodle', 'Photolist', '6175 Roth Parkway', '73497 Bunker Hill Crossing', '2024-02-15', '2024-02-23'),
    (7, 685368, 'Twiyo', 'Yodoo', 'Devbug', '1 Monument Crossing', '51 Bluestem Alley', '2024-03-03', '2024-03-13'),
    (8, 90116, 'Vitz', 'Trunyx', 'Brainverse', '06 Del Sol Terrace', '0 Anderson Drive', '2025-06-03', '2025-06-06'),
    (9, 615868, 'Vitz', 'Aimbo', 'Livepath', '15 Canary Center', '0 Sutteridge Center', '2025-05-04', '2025-05-13'),
    (10, 267396, 'Browsetype', 'Blogspan', 'Camido', '7 Bayside Lane', '49079 Lillian Trail', '2024-08-12', '2024-08-14');

    -- Create the palettes table
    CREATE TABLE IF NOT EXISTS palettes (
        id INT PRIMARY KEY,
        reception_id INT REFERENCES receptions(id),
        category_id INT REFERENCES categories(category_id),
        dimension_id INT REFERENCES dimensions(dimension_id),
        height DECIMAL(3,2),
        weight DECIMAL(6,2),
        stackable BOOLEAN,
        fragile BOOLEAN,
        rotten BOOLEAN,
        delivery_date DATE,
        expiration_date DATE,
        created_at DATE,
        updated_at DATE
    );

    -- Insert initial data into palettes (30 lines)
    INSERT INTO palettes (id, reception_id, category_id, dimension_id, height, weight, stackable, fragile, rotten, delivery_date, expiration_date, created_at, updated_at) VALUES
    (1, 3, 4, 1, 1.57, 764.32, false, false, false, '2025-12-04', '2025-12-14', '2025-07-04', '2025-07-14'),
    (2, 8, 5, 5, 1.6, 364.78, false, false, false, '2024-09-08', '2024-09-12', '2025-07-09', '2025-07-11'),
    (3, 6, 1, 2, 0.91, 905.55, false, true, false, '2026-05-07', '2026-05-16', '2024-07-01', '2024-07-11'),
    (4, 9, 3, 4, 0.62, 602.42, true, true, false, '2025-04-10', '2025-04-16', '2025-08-16', '2025-08-18'),
    (5, 1, 3, 3, 0.6, 994.83, true, false, true, '2026-02-28', '2026-02-28', '2025-02-20', '2025-03-02'),
    (6, 4, 3, 3, 1.62, 558.61, false, false, false, '2025-09-25', '2025-10-02', '2024-02-15', '2024-02-20'),
    (7, 5, 5, 2, 0.86, 18.15, false, false, true, '2024-12-24', '2024-12-28', '2025-10-11', '2025-10-13'),
    (8, 7, 3, 1, 1.49, 193.66, true, true, false, '2024-09-28', '2024-09-29', '2024-02-08', '2024-02-10'),
    (9, 1, 3, 3, 1.03, 960.82, true, true, false, '2026-03-14', '2026-03-23', '2024-05-06', '2024-05-16'),
    (10, 1, 3, 5, 1.71, 10.3, false, true, false, '2024-12-11', '2024-12-11', '2025-12-03', '2025-12-03'),
    (11, 8, 4, 2, 0.91, 390.61, false, true, true, '2024-08-10', '2024-08-17', '2026-02-27', '2026-03-03'),
    (12, 8, 3, 1, 0.57, 735.19, false, true, false, '2025-12-01', '2025-12-09', '2026-01-22', '2026-01-27'),
    (13, 1, 3, 3, 0.53, 573.4, true, false, false, '2025-04-18', '2025-04-28', '2024-02-07', '2024-02-07'),
    (14, 10, 3, 3, 0.57, 937.39, true, false, false, '2025-06-16', '2025-06-20', '2026-05-28', '2026-06-01'),
    (15, 1, 3, 3, 1.63, 719.18, false, false, true, '2026-04-12', '2026-04-18', '2024-06-08', '2024-06-11'),
    (16, 5, 3, 3, 0.59, 140.94, true, true, true, '2024-09-26', '2024-09-30', '2024-04-21', '2024-04-27'),
    (17, 7, 5, 3, 1.56, 675.91, false, true, true, '2025-03-08', '2025-03-08', '2025-06-17', '2025-06-26'),
    (18, 10, 2, 1, 0.75, 394.09, true, true, true, '2024-11-09', '2024-11-11', '2024-12-18', '2024-12-26'),
    (19, 7, 1, 4, 1.87, 954.27, false, false, true, '2025-12-16', '2025-12-17', '2025-03-11', '2025-03-15'),
    (20, 1, 4, 2, 1.98, 779.12, true, false, false, '2025-01-11', '2025-01-16', '2025-04-01', '2025-04-05'),
    (21, 4, 4, 4, 0.6, 469.87, true, true, true, '2024-12-28', '2024-12-31', '2024-07-13', '2024-07-14'),
    (22, 3, 2, 3, 1.36, 918.85, true, false, true, '2025-02-05', '2025-02-12', '2026-04-01', '2026-04-04'),
    (23, 7, 5, 2, 1.36, 729.23, false, false, false, '2025-02-20', '2025-02-28', '2025-06-30', '2025-07-08'),
    (24, 5, 2, 1, 0.8, 477.73, false, true, false, '2025-07-27', '2025-08-05', '2026-06-13', '2026-06-18'),
    (25, 6, 5, 2, 1.31, 178.7, true, true, true, '2025-03-16', '2025-03-26', '2025-08-15', '2025-08-20'),
    (26, 4, 3, 3, 0.58, 476.41, true, false, true, '2025-02-21', '2025-02-28', '2026-02-05', '2026-02-13'),
    (27, 10, 2, 1, 1.61, 516.52, false, false, false, '2026-01-11', '2026-01-16', '2024-06-22', '2024-06-26'),
    (28, 7, 3, 4, 1.86, 332.39, true, false, true, '2025-02-09', '2025-02-18', '2024-01-22', '2024-01-24'),
    (29, 6, 3, 1, 1.3, 266.71, true, true, false, '2026-02-09', '2026-02-16', '2025-08-12', '2025-08-17'),
    (30, 8, 3, 4, 1.36, 784.99, false, false, false, '2026-04-10', '2026-04-17', '2025-06-27', '2025-07-06');

    -- Create the parcels table
    CREATE TABLE IF NOT EXISTS parcels (
        id INT PRIMARY KEY,
        reception_id INT REFERENCES receptions(id),
        width INT,
        length INT,
        weight DECIMAL(6,2),
        fragile BOOLEAN,
        rotten BOOLEAN,
        delivery_date DATE,
        expiration_date DATE,
        created_at DATE,
        updated_at DATE
    );

    -- Insert initial data into parcels (30 lines)
    INSERT INTO parcels (id, reception_id, width, length, weight, fragile, rotten, delivery_date, expiration_date, created_at, updated_at) VALUES
    (1, 1, 30, 60, 760.06, false, false, '2025-12-03', '2025-12-04', '2025-03-18', '2025-03-28'),
    (2, 9, 10, 50, 588.5, true, true, '2025-04-16', '2025-04-16', '2026-05-16', '2026-05-20'),
    (3, 8, 20, 60, 950.3, false, false, '2025-07-25', '2025-08-01', '2024-01-27', '2024-02-01'),
    (4, 3, 50, 60, 763.86, true, true, '2025-10-03', '2025-10-04', '2026-01-11', '2026-01-12'),
    (5, 8, 20, 50, 48.36, false, true, '2024-11-27', '2024-11-29', '2026-04-30', '2026-05-10'),
    (6, 7, 50, 80, 204.91, false, false, '2025-03-06', '2025-03-14', '2025-05-15', '2025-05-19'),
    (7, 3, 50, 90, 471.23, true, false, '2024-08-18', '2024-08-26', '2025-01-31', '2025-02-06'),
    (8, 9, 30, 70, 278.32, true, false, '2026-06-20', '2026-06-27', '2025-05-18', '2025-05-22'),
    (9, 2, 20, 60, 560.45, true, true, '2025-08-11', '2025-08-12', '2025-11-28', '2025-12-05'),
    (10, 5, 20, 50, 181.6, true, true, '2024-09-02', '2024-09-05', '2026-01-04', '2026-01-06'),
    (11, 6, 20, 70, 95.39, false, false, '2025-07-06', '2025-07-07', '2024-05-14', '2024-05-15'),
    (12, 9, 30, 80, 897.03, true, false, '2026-06-29', '2026-07-02', '2026-01-26', '2026-02-05'),
    (13, 1, 30, 50, 240.68, false, true, '2026-06-28', '2026-07-02', '2026-01-25', '2026-01-25'),
    (14, 6, 10, 30, 465.4, false, true, '2025-12-04', '2025-12-10', '2024-12-03', '2024-12-10'),
    (15, 3, 30, 40, 58.17, true, false, '2025-11-28', '2025-12-02', '2025-12-28', '2025-12-31'),
    (16, 8, 10, 40, 415.18, false, false, '2025-03-22', '2025-03-31', '2024-12-15', '2024-12-22'),
    (17, 9, 30, 70, 868.25, true, true, '2026-04-10', '2026-04-18', '2025-11-07', '2025-11-11'),
    (18, 3, 50, 100, 547.24, false, true, '2025-04-02', '2025-04-10', '2025-11-03', '2025-11-09'),
    (19, 6, 30, 80, 313.8, false, false, '2026-05-15', '2026-05-15', '2026-02-04', '2026-02-06'),
    (20, 8, 40, 90, 238.63, true, true, '2024-11-17', '2024-11-21', '2025-06-07', '2025-06-14'),
    (21, 4, 40, 80, 393.56, true, false, '2025-09-27', '2025-10-03', '2025-06-08', '2025-06-18'),
    (22, 3, 40, 70, 725.91, false, true, '2025-04-19', '2025-04-21', '2024-07-26', '2024-07-30'),
    (23, 3, 40, 90, 405.57, true, false, '2025-10-01', '2025-10-03', '2024-12-21', '2024-12-28'),
    (24, 1, 50, 90, 128.45, true, true, '2025-07-12', '2025-07-20', '2024-04-27', '2024-05-01'),
    (25, 2, 20, 70, 541.87, true, true, '2024-11-19', '2024-11-28', '2024-01-09', '2024-01-13'),
    (26, 4, 20, 30, 644.78, false, true, '2026-04-10', '2026-04-19', '2024-08-01', '2024-08-09'),
    (27, 2, 10, 30, 186.3, true, true, '2025-01-26', '2025-01-31', '2025-08-03', '2025-08-07'),
    (28, 7, 50, 90, 186.39, false, true, '2025-06-17', '2025-06-21', '2025-09-10', '2025-09-10'),
    (29, 3, 50, 100, 551.13, true, true, '2025-12-27', '2026-01-06', '2025-10-24', '2025-11-03'),
    (30, 10, 20, 70, 832.77, false, true, '2025-03-17', '2025-03-23', '2025-08-08', '2025-08-10');

    -- Create the itemLinePalette table
    CREATE TABLE IF NOT EXISTS item_line_palette (
        item_id INT REFERENCES items(item_id),
        palette_id INT REFERENCES palettes(id),
        quantite INT,
        PRIMARY KEY (item_id, palette_id)
    );

 -- Insert initial data into itemLinePalette (50 lines)
INSERT INTO item_line_palette (item_id, palette_id, quantite) VALUES
(4, 7, 5),(17, 19, 9),(4, 17, 2),(4, 20, 2),(19, 4, 3),(12, 26, 10),(6, 26, 5),(2, 6, 5),(19, 5, 4),
(19, 3, 8),(15, 20, 10),(5, 8, 4),(7, 2, 3),(8, 1, 2),(14, 21, 4),(19, 6, 10),(1, 22, 2),(19, 2, 7),
(15, 16, 4),(1, 11, 5),(19, 11, 7),(15, 6, 9),(14, 19, 8),(8, 30, 2),(8, 10, 5),(5, 2, 2),
(14, 7, 8),(5, 28, 2),(14, 15, 1),(12, 8, 10),(5, 14, 6),(9, 29, 8),(6, 13, 10),(9, 22, 5),(6, 4, 3),
(12, 9, 8),(1, 3, 6),(6, 11, 7),(14, 29, 10),(13, 4, 7),(11, 8, 3),(15, 5, 9),(2, 12, 4),(12, 3, 9),
(10, 3, 7),(9, 7, 10),(2, 24, 3),(19, 23, 4),(4, 24, 1),(1,1,1);

   -- Create the itemLineParcel table
CREATE TABLE IF NOT EXISTS item_line_parcel (
    item_id INT REFERENCES items(item_id),
    parcel_id INT REFERENCES parcels(id),
    quantite INT,
    PRIMARY KEY (item_id, parcel_id)
);

-- Insert initial data into itemLineParcel (50 lines)
INSERT INTO item_line_parcel (item_id, parcel_id, quantite) VALUES
(11, 21, 1), (9, 9, 6), (19, 10, 6), (2, 1, 9), (1, 29, 8), (3, 24, 6), (9, 28, 6), (12, 15, 2),
(20, 29, 4), (14, 15, 1), (16, 18, 4), (17, 12, 5), (7, 3, 5), (17, 28, 9), (3, 26, 1), (20, 1, 1),
(1, 20, 2), (5, 27, 10), (14, 10, 9), (16, 7, 7), (2, 23, 7), (10, 2, 3), (15, 3, 8), (13, 19, 5),
(12, 13, 6), (20, 18, 9), (17, 8, 9), (4, 7, 1), (9, 25, 3), (8, 9, 4), (12, 17, 4), (6, 8, 5),
(20, 15, 7), (2, 15, 2), (6, 7, 5), (11, 14, 6), (16, 3, 5), (7, 30, 1), (9, 21, 1), (11, 12, 9),
(2, 8, 3), (11, 7, 3), (6, 4, 1), (13, 12, 10), (9, 11, 9), (18, 16, 2), (7, 23, 2),
(14, 29, 5), (19, 2, 2),(1,1,1);  

