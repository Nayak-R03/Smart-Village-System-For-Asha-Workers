-- Smart Village System Database Tables
-- Run these SQL commands in your MySQL database

-- 1. ASHA table (must be created first as other tables reference it)
CREATE TABLE IF NOT EXISTS ashas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    asha_id BIGINT NOT NULL UNIQUE,
    name VARCHAR(255),
    dob DATE,
    mobile VARCHAR(20),
    email VARCHAR(255),
    password VARCHAR(255) NOT NULL,
    state VARCHAR(255),
    district VARCHAR(255),
    taluk VARCHAR(255),
    village VARCHAR(255),
    ward VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. State table
CREATE TABLE IF NOT EXISTS state (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- 3. District table
CREATE TABLE IF NOT EXISTS district (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    state_id BIGINT,
    FOREIGN KEY (state_id) REFERENCES state(id) ON DELETE CASCADE
);

-- 4. Taluk table
CREATE TABLE IF NOT EXISTS taluk (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    district_id BIGINT,
    FOREIGN KEY (district_id) REFERENCES district(id) ON DELETE CASCADE
);

-- 5. Village table
CREATE TABLE IF NOT EXISTS village (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    taluk_id BIGINT,
    FOREIGN KEY (taluk_id) REFERENCES taluk(id) ON DELETE CASCADE
);

-- 6. Ward table
CREATE TABLE IF NOT EXISTS ward (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    village_id BIGINT,
    FOREIGN KEY (village_id) REFERENCES village(id) ON DELETE CASCADE
);

-- 7. Family table
CREATE TABLE IF NOT EXISTS family (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    family_name VARCHAR(255),
    asha_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_family_asha_id (asha_id)
);

-- 8. Family Members table
CREATE TABLE IF NOT EXISTS family_members (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    dob DATE,
    gender VARCHAR(20),
    spouse VARCHAR(255),
    father_name VARCHAR(255),
    mother_name VARCHAR(255),
    education VARCHAR(255),
    occupation VARCHAR(255),
    relation VARCHAR(50),
    asha_id BIGINT,
    head_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (head_id) REFERENCES family(id) ON DELETE CASCADE,
    INDEX idx_family_members_asha_id (asha_id),
    INDEX idx_family_members_head_id (head_id)
);

-- 9. Announcements table
CREATE TABLE IF NOT EXISTS announcements (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    description TEXT NOT NULL,
    date_time DATETIME NOT NULL,
    sender_name VARCHAR(255) NOT NULL,
    venue VARCHAR(255),
    sender_type ENUM('PANCHAYATH', 'HEALTHCARE') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 10. Eligible Couples table
CREATE TABLE IF NOT EXISTS eligible_couples (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    spouse_name VARCHAR(255) NOT NULL,
    age INT NOT NULL,
    pregnant ENUM('Yes', 'No') NOT NULL,
    asha_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (asha_id) REFERENCES ashas(asha_id) ON DELETE CASCADE
);

-- 11. ANC (Antenatal Care) table
CREATE TABLE IF NOT EXISTS anc_pregnant_ladies (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    age INT NOT NULL,
    spouse_name VARCHAR(255) NOT NULL,
    delivered ENUM('Yes', 'No') NOT NULL,
    asha_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (asha_id) REFERENCES ashas(asha_id) ON DELETE CASCADE
);

-- 12. PNC (Postnatal Care) table
CREATE TABLE IF NOT EXISTS pnc_delivered_ladies (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    age INT NOT NULL,
    husband_name VARCHAR(255) NOT NULL,
    delivery_date DATE NOT NULL,
    delivery_phase ENUM('Healthy', 'Miscarriage', 'Problematic') NOT NULL,
    asha_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (asha_id) REFERENCES ashas(asha_id) ON DELETE CASCADE
);

-- 13. Child Care table
CREATE TABLE IF NOT EXISTS child_care (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    child_name VARCHAR(255) NOT NULL,
    child_weight DECIMAL(5,2) NOT NULL,
    dob DATE NOT NULL,
    age INT NOT NULL,
    asha_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (asha_id) REFERENCES ashas(asha_id) ON DELETE CASCADE
);

-- 14. Work Schedules table
CREATE TABLE IF NOT EXISTS work_schedules (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    asha_id BIGINT NOT NULL,
    asha_name VARCHAR(255) NOT NULL,
    work VARCHAR(500) NOT NULL,
    place VARCHAR(255) NOT NULL,
    hours INT NOT NULL,
    work_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (asha_id) REFERENCES ashas(asha_id) ON DELETE CASCADE
);

-- 15. Password Reset Tokens table
CREATE TABLE IF NOT EXISTS password_reset_tokens (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    asha_id BIGINT NOT NULL,
    token VARCHAR(255) NOT NULL UNIQUE,
    expires_at TIMESTAMP NOT NULL,
    used BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (asha_id) REFERENCES ashas(asha_id) ON DELETE CASCADE
);

-- Add indexes for better performance (MySQL doesn't support IF NOT EXISTS for indexes, so these are created inline above)
-- Additional indexes are already created in the table definitions above
