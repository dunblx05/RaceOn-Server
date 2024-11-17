INSERT INTO member(member_id, nickname, profile_image_url, member_status, created_at, updated_at, deleted_at)
VALUES (1, "test",  "http://dippin.com/image/url",  "ACTIVE", "2024-09-07 23:13:12", null, null),
       (2, "test2",  "http://dippin.com/image/url",  "ACTIVE", "2024-09-07 23:13:12", null, null),
       (3, "test3",  "http://dippin.com/image/url",  "ACTIVE", "2024-09-07 23:13:12", null, null);

INSERT INTO friends(member_id, friend_id, created_at, deleted_at, updated_at)
VALUES (1, 2, "2024-11-17 10:10:10", null, null),
       (1, 3, "2024-11-17 10:10:10", null, null)