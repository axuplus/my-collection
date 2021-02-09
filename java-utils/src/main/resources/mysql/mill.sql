## 演示百万数据的sql函数
DELIMITER $$
CREATE FUNCTION mock_data () RETURNS INT BEGIN
	DECLARE
		num INT DEFAULT 5000000;
	DECLARE
		i INT DEFAULT 0;
	WHILE
			i < num DO
			INSERT INTO school_stu ( `name`, `email`, `phone`, `gender`, `password`, `age` )
		VALUES
			(
				CONCAT( '用户', i ),
				'axuplus@gmail.com',
				CONCAT(
					'17',
					FLOOR(
					RAND()* (( 999999999-1000000000 )+ 100000000 ))),
				FLOOR( RAND()* 2 + 1 ),
				UUID(),
			FLOOR( RAND()* 100 ));
		SET i = i + 1;
	END WHILE;
RETURN i;
END;

SELECT mock_data();

CREATE INDEX id_school_stu_name ON school_stu(`name`);
