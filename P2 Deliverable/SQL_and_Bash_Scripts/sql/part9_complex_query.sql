
-- Complex Query
-- Find the friends with the highest age difference in terms of years
SELECT t1.usert_handle, usert.usert_handle, ABS(EXTRACT(YEAR FROM t1.birth_date) - EXTRACT(YEAR FROM usert.birth_date)) AS age_difference FROM 
	(SELECT usert.birth_date, usert_friend.usert_handle,
	usert_friend.friend_handle FROM usert_friend LEFT JOIN usert ON usert.usert_handle = usert_friend.usert_handle) t1 
LEFT JOIN usert ON t1.friend_handle = usert.usert_handle GROUP BY t1.usert_handle, usert.usert_handle, t1.birth_date, usert.birth_date HAVING ABS(EXTRACT(YEAR FROM t1.birth_date) - EXTRACT(YEAR FROM usert.birth_date)) = 
(SELECT MAX(ABS(EXTRACT(YEAR FROM t1.birth_date) - EXTRACT(YEAR FROM usert.birth_date))) AS age_difference FROM 
	(SELECT usert.birth_date, usert_friend.usert_handle,
	usert_friend.friend_handle FROM usert_friend LEFT JOIN usert ON
	usert.usert_handle = usert_friend.usert_handle) t1 
LEFT JOIN usert ON t1.friend_handle = usert.usert_handle);