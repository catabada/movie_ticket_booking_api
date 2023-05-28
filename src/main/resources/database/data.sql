insert into genre(name, state)
values ('Hành động', 'ACTIVE'),
       ('Phiêu lưu', 'ACTIVE'),
       ('Hoạt hình', 'ACTIVE'),
       ('Hài kịch', 'ACTIVE'),
       ('Tội phạm', 'ACTIVE'),
       ('Tài liệu', 'ACTIVE'),
       ('Chính kịch', 'ACTIVE'),
       ('Gia đình', 'ACTIVE'),
       ('Fantasy', 'ACTIVE'),
       ('Lịch sử', 'ACTIVE'),

       ('Kinh dị', 'ACTIVE'),
       ('Âm nhạc', 'ACTIVE'),
       ('Huyền bí', 'ACTIVE'),
       ('Lãng mạn', 'ACTIVE'),
       ('Khoa học viễn tưởng', 'ACTIVE'),
       ('TV Movie', 'ACTIVE'),
       ('Giật gân', 'ACTIVE'),
       ('Chiên tranh', 'ACTIVE'),
       ('Viễn Tây', 'ACTIVE');


INSERT INTO movie (duration, rating, created_date, deleted_date, modified_date, release_date, country, director,
                   image_horizontal, image_vertical, language, movie_state, name, producer, slug, state, story_line,
                   subtitle, trailer_url)
VALUES (93,
        8.5,
        '2023-04-18 18:20:53.197444',
        NULL,
        '2023-04-18 18:20:53.199342',
        '2023-04-07 00:00:00',
        'Hoa Kỳ', 'Aaron Horvath',
        '/images/phim_anh_em_super_mario_horizontal.jpg',
        '/images/phim_anh_em_super_mario_vertical.jpg',
        'Tiếng Anh',
        'NOW_SHOWING',
        'Phim anh em Super Mario',
        'Universal Pictures',
        'phim-anh-em-super-mario',
        'ACTIVE',
        'Trong một lần làm việc, bộ đôi vô tình bị hút tới một xứ sở kỳ lạ. Mario đặt chân tới vương quốc Nấm, nơi công chúa Peach ngự trị. Trong khi đó, Luigi kém may mắn hơn khi phải đối mặt với Bowser tại vùng đất Bóng đêm.Lãnh đạo đội quân Koopa, Goomba và nhiều sinh vật khác, bạo chúa Bowser mưu đồ đánh chiếm vương quốc Nấm hòng ép công chúa kết hôn với mình. Để cứu lấy em trai, đồng thời giải cứu vương quốc Nấm, Mario phải hợp sức với Peach để đánh bại Bowse Họ cùng nhau tới vương quốc Rừng rậm, kêu gọi sự trợ giúp của đức vua Cranky Kong.',
        'Tiếng Việt', 'https://www.youtube.com/watch?v=YJXqvnT_rsk');

INSERT INTO movie (duration, rating, created_date, deleted_date, modified_date, release_date, country, director,
                   image_horizontal, image_vertical, language, movie_state, name, producer, slug, state, story_line,
                   subtitle, trailer_url)
VALUES (103,
        7.5,
        '2023-04-18 18:20:53.197444',
        NULL,
        '2023-04-18 18:20:53.199342',
        '2023-04-07 00:00:00',
        'Hoa Kỳ', 'Julius Avery',
        '/images/khắc_tinh_của_quỷ_horizontal.jpg',
        '/images/khắc_tinh_của_quỷ_vertical.jpg',
        'Tiếng Anh',
        'NOW_SHOWING',
        'Khắc tinh của quỷ',
        'Doug Belgrad',
        'khac-tinh-cua-quy',
        'ACTIVE',
        'Phim mở đầu với mô típ quen thuộc khi một gia đình dọn vào ngôi nhà cổ để sinh sống. Không may, nơi đây là địa điểm trú ngụ của một thực thể hắc ám. Nó đã len lỏi, lợi dụng sự ngây thơ, non nớt của con người và thao túng họ, rút cạn sự sống của bất kỳ ai lưu lại nơi này. Vì lẽ đó, linh mục Gabriele Armoth được mời đến nhằm trục xuất con quỷ đang giết dần giết mòn gia đình kia.',
        'Tiếng Việt', 'https://www.youtube.com/watch?v=SXfnUAW9gwA');



INSERT INTO movie_actors
VALUES (1, 'Russell Crowe');
INSERT INTO movie_actors
VALUES (1, 'Laurel Marsden');
INSERT INTO movie_actors
VALUES (1, 'Franco Nero');
INSERT INTO movie_actors
VALUES (1, 'Alex Essoe');
INSERT INTO movie_actors
VALUES (1, 'Ralph Ineson');

INSERT INTO movie_actors
VALUES (2, 'Russell Crowe');
INSERT INTO movie_actors
VALUES (2, 'Alex Essoe');
INSERT INTO movie_actors
VALUES (2, 'Franco Nero');
INSERT INTO movie_actors
VALUES (2, 'Daniel Zovatto');

INSERT INTO movie_genre
VALUES (2, 1);
INSERT INTO movie_genre
VALUES (3, 1);

INSERT INTO movie_genre
VALUES (11, 2);
INSERT INTO movie_genre
VALUES (17, 2);

INSERT INTO movie_movie_formats(movie_id, movie_formats)
values (1, 'TWO_D'),
       (1, 'THREE_D'),
       (1, 'VOICE_OVER'),
       (2, 'TWO_D'),
       (2, 'THREE_D');
