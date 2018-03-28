
-- Modification 3)
-- Delete all comments with no likes:
DELETE FROM commentt C WHERE NOT EXISTS (SELECT * FROM submission_like SL WHERE C.commentt_id = SL.submission_id);
-- Delete the corresponding submission for each deleted comment:
DELETE FROM submission WHERE submission_id NOT IN (SELECT post_id AS submission_id FROM post UNION SELECT commentt_id AS submission_id FROM commentt);
