--CREATE DATABASE warehouse;
SELECT 'CREATE DATABASE warehouse'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'warehouse')\gexec
-- FOR Testing
-- Create the categories table
CREATE TABLE IF NOT EXISTS categories (
    category_id SERIAL PRIMARY KEY,
    label VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255) NOT NULL
);

-- Insert initial data into categories
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

-- Insert initial data into dimensions
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

-- Insert initial data into items
-- Insert 20 fake records into the items table
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