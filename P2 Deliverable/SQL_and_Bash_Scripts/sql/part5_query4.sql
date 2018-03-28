
-- Query 4) 
-- Display names of all users who commented on post 39. Also display the text of their comment.
SELECT display_name, text_content FROM usert T1 JOIN (SELECT C.usert_handle, S.text_content FROM commentt C, submission S WHERE C.commentt_id = S.submission_id AND C.post_id = 39) T2 ON T1.usert_handle = T2.usert_handle;