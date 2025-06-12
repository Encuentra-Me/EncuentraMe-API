-- Remove the unique constraint from role_id in users table
ALTER TABLE users DROP CONSTRAINT IF EXISTS ukkrvotbtiqhudlkamvlpaqus0t;

-- Ensure the foreign key constraint is properly set
ALTER TABLE users 
    ADD CONSTRAINT fk_users_role 
    FOREIGN KEY (role_id) 
    REFERENCES roles(id); 