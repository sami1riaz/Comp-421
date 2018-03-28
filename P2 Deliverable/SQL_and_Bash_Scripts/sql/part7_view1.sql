
-- View 1) 
-- This view represents the average age per event of the people that rsvped that they are ‘going’ to said event.

-- Create the view:
CREATE VIEW eventt_average_ages (eventt_id, average_age) AS SELECT t1.eventt_id, AVG(age(usert.birth_date)) AS avgage FROM (SELECT * FROM eventt_subscription WHERE rsvp_status = 'going') t1 LEFT JOIN usert ON usert.usert_handle = t1.usert_handle GROUP BY eventt_id ORDER BY avgage;

-- Use the view (to find the event with the max average age of people that are going))
SELECT * FROM eventt_average_ages WHERE average_age = (SELECT MAX(average_age) FROM eventt_average_ages);

-- Trying to update:
UPDATE eventt_average_ages SET average_age = age(CURRENT_TIMESTAMP) WHERE eventt_id = 32;