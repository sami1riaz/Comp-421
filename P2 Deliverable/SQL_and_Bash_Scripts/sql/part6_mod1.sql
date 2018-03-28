
-- Modification 1) 
-- Update the following_count number for each page:
UPDATE page SET follower_count = (SELECT COUNT(*) FROM page_follower WHERE page_follower.page_handle = page.page_handle); 
-- Print the results
SELECT * FROM page LIMIT 10;
