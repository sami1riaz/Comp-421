
-- Check 2) 
-- Only allow event that last less than a year

-- Add Constraint:
ALTER TABLE eventt ADD CONSTRAINT eventt_time_constraint CHECK ( end_time < (start_time + INTERVAL '1 year'));

-- Revised Schema:
\d eventt
 
-- Trying to violate constraint (update an event to be last longer than a year):
UPDATE eventt SET end_time = '2020-06-05' WHERE eventt_id = 1;
