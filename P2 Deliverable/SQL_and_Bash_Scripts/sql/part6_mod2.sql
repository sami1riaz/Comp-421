
-- Modification 2)
-- Delete all event subscriptions to events older than 11 months
DELETE FROM eventt_subscription WHERE eventt_id IN
(SELECT (eventt_id) FROM eventt WHERE creation_time < CURRENT_DATE -  INTERVAL '11 months');
-- Delete all events with creation times earlier than 11 months
DELETE FROM eventt WHERE creation_time < CURRENT_DATE - INTERVAL '11 months';
