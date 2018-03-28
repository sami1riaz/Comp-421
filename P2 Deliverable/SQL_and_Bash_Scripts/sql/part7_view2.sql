
-- View 2) 
-- Create a view that consists of all the users’ with unread notifications usert_handle and the number of each users unread notifications.

-- Create the view:
CREATE VIEW unread_notifications (usert_handle, num_of_notifs) AS SELECT usert_handle, COUNT(usert_handle)
FROM notification
WHERE read_status = 'f'
GROUP BY usert_handle; 

-- Use the view (to find the users with highest number of unread notifications)
SELECT usert_handle FROM unread_notifications WHERE num_of_notifs = (SELECT MAX(num_of_notifs) FROM unread_notifications);

-- Trying to update:
UPDATE unread_notifications SET num_of_notifs = 4 WHERE usert_handle = 'tbettenay33'; 
