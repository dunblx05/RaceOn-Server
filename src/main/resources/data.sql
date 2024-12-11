INSERT INTO member(member_id, nickname, profile_image_url, member_status, social_provider, social_id, member_code, created_at, updated_at, deleted_at)
VALUES (1, "test",  "http://dippin.com/image/url",  "ACTIVE", "KAKAO", "kakao1", "J13D3E", "2024-09-07 23:13:12", null, null),
       (2, "test2",  "http://dippin.com/image/url",  "ACTIVE", "APPLE", "apple2", "J13D4E", "2024-09-07 23:13:12", null, null),
       (3, "test3",  "http://dippin.com/image/url",  "ACTIVE", "KAKAO", "kakao3", "J13D5E", "2024-09-07 23:13:12", null, null),
       (4, "4test",  "http://dippin.com/image/url",  "ACTIVE", "APPLE", "apple4", "J13D6E", "2024-09-07 23:13:12", null, null),
       (5, "test5",  "http://dippin.com/image/url",  "ACTIVE", "KAKAO", "kakao5", "J13D7E", "2024-09-07 23:13:12", null, null);

INSERT INTO friends(member_id, friend_id, created_at, deleted_at, updated_at)
VALUES (1, 2, "2024-11-17 10:10:10", null, null),
       (1, 3, "2024-11-17 10:10:10", null, null)