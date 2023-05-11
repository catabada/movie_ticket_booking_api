-- USER_INFO
insert into user_info (full_name, is_male, avatar, date_of_birth, state)
values ('Huynh Van Huu An', true,'https://robohash.org/huynhvahuuan3620@gmail.com', '2001-10-03', 'ACTIVE');

-- APP_USER
insert into app_user (email, phone, password, enabled, account_non_locked, facebook_id, google_id, state)
values ('huynhvahuuan3620@gmail.com', '0787782050', '$2a$10$WYJRSKVUw5kO.0xfAjCy7.YixSDJK03cPB7BtH0sX.ZaroKlhXgba', true, true, null, null, 'ACTIVE');

-- USER_ROLE
insert into user_role (user_id, role_id)
values (1, 1);