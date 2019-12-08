-- 查询格式: login?username=&password=
-- 返回值: username_not_exist, incorrect_password, user_type
-- username_not_exist: 0 or 1
-- incorrect_password: 0 or 1
-- user_type: in ['buyer','forseller','seller','admin','deleted']

SELECT IF(tmp.passwd = NULL, 1, 0), IF(tmp.passwd = '123456', 0, 1), tmp.type as user_type
FROM
	(SELECT a.passwd, a.type FROM account AS a WHERE a.name = 'abc') AS tmp;
