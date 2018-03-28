
CREATE TABLE wall(
	wall_id SERIAL,
	description VARCHAR(1023),
	PRIMARY KEY(wall_id)
);

CREATE TABLE creator(
	handle VARCHAR(255),
	wall_id INTEGER UNIQUE NOT NULL,
	PRIMARY KEY (handle),
	FOREIGN KEY (wall_id) REFERENCES wall
);

CREATE TABLE usert(
	usert_handle VARCHAR(255), 
	display_name VARCHAR(255), 
	email VARCHAR(255) UNIQUE, 
	firstname VARCHAR(255), 
	lastname VARCHAR(255), 
	gender VARCHAR(255), 
	birth_date TIMESTAMP,
	PRIMARY KEY (usert_handle),
	FOREIGN KEY (usert_handle) REFERENCES creator(handle)
);

CREATE TABLE page(
	page_handle VARCHAR(255),
	description VARCHAR(1023),
	title VARCHAR(255),
	follower_count INTEGER NOT NULL DEFAULT 0,
	usert_handle VARCHAR(255) NOT NULL,
	PRIMARY KEY (page_handle),
	FOREIGN KEY (page_handle) REFERENCES creator(handle),
	FOREIGN KEY (usert_handle) REFERENCES usert
);

CREATE TABLE submission(
	submission_id SERIAL,
	text_content VARCHAR(8000),
	like_count INTEGER NOT NULL DEFAULT 0,
	creation_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (submission_id)
);

CREATE TABLE post(
	post_id INTEGER,
	view_count INTEGER NOT NULL DEFAULT 0,
	attachment_link VARCHAR(2083),
	image VARCHAR(2083),
	handle VARCHAR(255) NOT NULL,
	wall_id INTEGER NOT NULL,
	PRIMARY KEY(post_id),
	FOREIGN KEY(post_id) REFERENCES submission(submission_id),
	FOREIGN KEY(handle) REFERENCES creator,
	FOREIGN KEY(wall_id) REFERENCES wall
);

CREATE TABLE commentt(
	commentt_id INTEGER,
	usert_handle VARCHAR(255) NOT NULL,
	post_id INTEGER NOT NULL,
	PRIMARY KEY (commentt_id),
	FOREIGN KEY (commentt_id) REFERENCES submission(submission_id),
	FOREIGN KEY (usert_handle) REFERENCES usert,
	FOREIGN KEY (post_id) REFERENCES post
);

CREATE TABLE eventt(
	eventt_id SERIAL,
	handle VARCHAR(255) NOT NULL,
	wall_id INTEGER UNIQUE NOT NULL,
	creation_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	start_time TIMESTAMP NOT NULL,
	end_time TIMESTAMP NOT NULL,
	title VARCHAR(255) NOT NULL,
	description VARCHAR(1023),
	location VARCHAR(255),
	PRIMARY KEY(eventt_id),
	FOREIGN KEY(handle) REFERENCES creator,
	FOREIGN KEY(wall_id) REFERENCES wall
);

CREATE TABLE notification(
	notif_id SERIAL,
	read_status BOOLEAN,
	notif_link VARCHAR(2083),
	notif_text VARCHAR(1023), 
	notif_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 
	usert_handle VARCHAR(255) NOT NULL,
	PRIMARY KEY (notif_id),
	FOREIGN KEY (usert_handle) REFERENCES usert
);

CREATE TABLE page_follower(
	usert_handle VARCHAR(255),
	page_handle VARCHAR(255),
	PRIMARY KEY(usert_handle, page_handle),
	FOREIGN KEY(usert_handle) REFERENCES usert,
	FOREIGN KEY(page_handle) REFERENCES page
);

CREATE TABLE eventt_subscription(
	usert_handle VARCHAR(255),
	eventt_id INTEGER,
	rsvp_status VARCHAR(255),
	PRIMARY KEY(usert_handle, eventt_id),
	FOREIGN KEY(usert_handle) REFERENCES usert,
	FOREIGN KEY(eventt_id) REFERENCES eventt
);

CREATE TABLE submission_like(
	usert_handle VARCHAR(255),
	submission_id INTEGER,
	PRIMARY KEY(usert_handle, submission_id),
	FOREIGN KEY(usert_handle) REFERENCES usert,
	FOREIGN KEY(submission_id) REFERENCES submission
);

CREATE TABLE feed_view(
	usert_handle VARCHAR(255),
	post_id INTEGER,
	PRIMARY KEY(usert_handle, post_id),
	FOREIGN KEY(usert_handle) REFERENCES usert,
	FOREIGN KEY(post_id) REFERENCES post
);

CREATE TABLE usert_friend(
	usert_handle VARCHAR(255),
	friend_handle VARCHAR(255),
	PRIMARY KEY(usert_handle, friend_handle),
	FOREIGN KEY(usert_handle) REFERENCES usert,
	FOREIGN KEY(friend_handle) REFERENCES usert(usert_handle),
	CHECK (usert_handle <> friend_handle)
);
