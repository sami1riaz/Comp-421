
-- Check 1) 
-- Check that the user is above 18 

-- Add Constraint:
ALTER TABLE usert ADD CONSTRAINT minimum_age CHECK (EXTRACT(YEAR FROM age(birth_date)) >= 18);

-- Revised Schema:
\d usert
 
-- Trying to violate constraint (update an age to under 18).
UPDATE usert SET birth_date = '2002-03-01' WHERE usert_handle = 'bworboy4'; 
