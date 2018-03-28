
-- Query 3) 
-- Display names of all users who are ‘going’ to more than one event
SELECT display_name 
FROM usert 
WHERE usert_handle IN 
	(SELECT usert_handle FROM eventt_subscription 
	WHERE rsvp_status = 'going' 
	GROUP BY usert_handle HAVING COUNT(*) > 1);