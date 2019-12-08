-- 查询格式: login?username=&password=
-- 返回值: incorrect_password, user_type
-- incorrect_password: 0 or 1
-- user_type: in ['buyer','forseller','seller','admin','deleted']
-- 注意: 账号不存在时返回为空！

SELECT IF(tmp.en_passwd = myhash(?), 0, 1) as incorrect_password, tmp.type as user_type
FROM (SELECT a.en_passwd, a.type FROM account AS a WHERE a.name = ?) AS tmp

-- 第一个问号填密码
-- 第二个问号填昵称
