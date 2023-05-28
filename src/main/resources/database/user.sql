-- USER_INFO
insert into user_info (full_name, is_male, avatar, date_of_birth, state)
values ('Huynh Van Huu An', true,'https://firebasestorage.googleapis.com/v0/b/movie-ticket-booking-383806.appspot.com/o/user%2Favatar%2Fhuynhvahuuan3620%40gmail.com?alt=media&token=b8fc53f8-8359-4ba3-8670-1d25affb4924', '2001-10-03', 'ACTIVE');
insert into user_info (full_name, is_male, avatar, date_of_birth, state)
values ('ADMIN', true,'https://robohash.org/admin', '2001-10-03', 'ACTIVE');

-- APP_USER
insert into app_user (email, phone, password, enabled, account_non_locked, facebook_id, google_id, state)
values ('huynhvahuuan3620@gmail.com', '0787782050', '$2a$10$WYJRSKVUw5kO.0xfAjCy7.YixSDJK03cPB7BtH0sX.ZaroKlhXgba', true, true, null, null, 'ACTIVE');
insert into app_user (email, phone, password, enabled, account_non_locked, facebook_id, google_id, state)
values ('admin@gmail.com', '0123456789', '$2a$10$WYJRSKVUw5kO.0xfAjCy7.YixSDJK03cPB7BtH0sX.ZaroKlhXgba', true, true, null, null, 'ACTIVE');

-- USER_ROLE
insert into user_role (user_id, role_id)
values (1, 3);
insert into user_role (user_id, role_id)
values (2, 2);

insert into user_info (full_name, is_male, avatar, date_of_birth, state)
values ('Huynh Van Huu An', true,'https://robohash.org/huynhvahuuan3620@gmail.com', '2001-09-25', 'ACTIVE');

-- APP_USER
insert into app_user (email, phone, password, enabled, account_non_locked, facebook_id, google_id, state)
values ('funni250901@gmail.com', '0987609675', '$2a$10$WYJRSKVUw5kO.0xfAjCy7.YixSDJK03cPB7BtH0sX.ZaroKlhXgba', true, true, null, null, 'ACTIVE');

-- USER_ROLE
insert into user_role (user_id, role_id)
values (3, 2);