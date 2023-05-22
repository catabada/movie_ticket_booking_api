INSERT INTO combo(name, price, image, state)
values ('iCombo 1 Big Extra STD', 99000, '/images/icombo_1_big_extra_std.jpg', 'ACTIVE'),
       ('iCombo 2 Big Extra STD', 119000, '/images/icombo_2_big_extra_std.jpg','ACTIVE'),
       ('iCombo 1 Big STD', 79000, '/images/icombo_1_big_std.jpg','ACTIVE'),
       ('iCombo 2 Big STD', 99000,'/images/icombo_2_big_std.jpg' ,'ACTIVE');

INSERT INTO combo_item(combo_id, product_id, quantity, state)
values (1, 2, 1, 'ACTIVE'),
       (1, 3, 1, 'ACTIVE'),
       (1, 5, 1, 'ACTIVE'),
       (2, 2, 2, 'ACTIVE'),
       (2, 3, 1, 'ACTIVE'),
       (2, 5, 1, 'ACTIVE'),
       (3, 2, 1, 'ACTIVE'),
       (3, 3, 1, 'ACTIVE'),
       (4, 2, 2, 'ACTIVE'),
       (4, 3, 1, 'ACTIVE');

