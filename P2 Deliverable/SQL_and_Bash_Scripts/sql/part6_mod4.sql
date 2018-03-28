
-- Modification 4) 
-- Update the like_count number for each submission:
UPDATE submission SET like_count = (SELECT COUNT(*) FROM submission_like WHERE submission.submission_id = submission_like.submission_id); 
-- Print the results
SELECT * FROM submission LIMIT 10;
