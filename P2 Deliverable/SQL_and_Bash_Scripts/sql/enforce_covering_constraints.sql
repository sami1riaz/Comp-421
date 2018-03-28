
-- In order to enforce Covering Constraint for Submission ISA hierarchy (i.e. every entity of Submission entity set must be one of the subclass entity sets Post or Comment), this script deletes any submission that is not also a comment or post.
-- The following delete statement deletes submission likes on submissions that are not either a comment or post 
DELETE FROM submission_like WHERE submission_id NOT IN (SELECT post_id AS submission_id FROM post UNION SELECT commentt_id AS submission_id FROM commentt);
-- The following delete statement deletes the submissions (that are not either a post or comment) themselves
DELETE FROM submission WHERE submission_id NOT IN (SELECT post_id AS submission_id FROM post UNION SELECT commentt_id AS submission_id FROM commentt);
