INSERT INTO member(member_id, nickname, profile_image_url, member_code, member_status, social_provider, social_id, last_active_at,created_at, updated_at, deleted_at)
VALUES (1, "test",  "http://dippin.com/image/url",  "A12345", "ACTIVE", "KAKAO", "kakao1", "2024-09-07 23:13:12", "2024-09-07 23:13:12", null, null),
       (2, "test2",  "http://dippin.com/image/url",  "A12346", "ACTIVE", "APPLE", "apple2", "2024-09-07 23:13:12", "2024-09-07 23:13:12", null, null),
       (3, "test3",  "http://dippin.com/image/url",  "A12347", "ACTIVE", "KAKAO", "kakao3", "2024-09-07 23:13:12", "2024-09-07 23:13:12", null, null),
       (4, "4test",  "http://dippin.com/image/url",  "A12348", "ACTIVE", "APPLE", "apple4", "2024-09-07 23:13:12", "2024-09-07 23:13:12", null, null),
       (5, "test5",  "http://dippin.com/image/url",  "A12349", "ACTIVE", "KAKAO", "kakao5", "2024-09-07 23:13:12", "2024-09-07 23:13:12", null, null);

INSERT INTO friends(member_id, friend_id, created_at, deleted_at, updated_at)
VALUES (1, 2, "2024-11-17 10:10:10", null, null),
       (1, 3, "2024-11-17 10:10:10", null, null)