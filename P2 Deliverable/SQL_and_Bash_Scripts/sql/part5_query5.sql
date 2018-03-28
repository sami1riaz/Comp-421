
-- Query 5) 
-- Number of comments by gender
SELECT gender, COUNT(*) 
FROM commentt C 
LEFT JOIN usert U 
ON C.usert_handle = U.usert_handle 
GROUP BY gender