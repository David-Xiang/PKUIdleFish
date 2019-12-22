USE pkuidlefish;
TRUNCATE TABLE account;
ALTER TABLE account AUTO_INCREMENT = 1000;
TRUNCATE TABLE bargain;
TRUNCATE TABLE category;
TRUNCATE TABLE comment;
TRUNCATE TABLE product;
TRUNCATE TABLE transaction;

INSERT INTO account(username, en_password, birth, sex, email, phone, account_status, update_time) VALUES
('admin', myhash('admin'), '2000-01-05', 'M', 'admin@pku.edu.cn', '10086000005', 'admin', '2006-06-06'),
('user1', myhash('password1'), '2000-01-01', 'M', 'user1@pku.edu.cn', '10086000001', 'seller', '2011-11-11'),
('user2', myhash('password2'), '2000-01-02', 'F', 'user2@pku.edu.cn', '10086000002', 'forseller', '2010-10-10'),
('user3', myhash('password3'), '2000-01-03', 'M', 'user3@pku.edu.cn', '10086000003', 'seller', '2009-09-09'),
('user4', myhash('password4'), '2000-01-04', 'F', 'user4@pku.edu.cn', '10086000004', 'buyer', '2008-08-08'),
('user5', myhash('password5'), '2000-01-05', 'M', 'user5@pku.edu.cn', '10086000005', 'buyer', '2007-07-07');

INSERT INTO category (category, category_name) VALUES
(1, 'category1'),
(2, 'category2');
INSERT INTO product
(category, title, imgsrc, price, seller_name, description, update_time, product_status) VALUES
(2, 'HUAWEI/华为nova 5 Pro麒麟980新品4800万AI四摄超级快充屏内指纹NFC手机', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1575715338590&di=94dbbaeca0758c01902f028c78d54e41&imgtype=0&src=http%3A%2F%2Fclubimg.club.vmall.com%2Fdata%2Fattachment%2Fforum%2F201907%2F02%2F000729yts29urfnynxzpka.jpeg', 3099.0, 'user1', '证书编号：2019011606187963证书状态：有效产品名称：TD-LTE数字移动电话机3C规格型号：SEA-AL10(开关电源适配器：HW-100400C00，输出：5VDC，2A 或 9VDC，2A...产品名称：HUAWEI nova 5 Pro华为型号: nova 5 Pro机身颜色: 苏音蓝 绮境森林 珊瑚橙 仲夏紫 珊瑚橙·星耀礼盒版 亮黑色运行内存RAM: 8GB存储容量: 8+128GB 8+256GB网络模式: 双卡双待CPU型号: 980', '2018-10-01', 'sale'),
(1, '麻霖原创女装2019冬新品 内蒙古阿拉善 100%软糯细腻山羊绒衫', 'https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=878857037,200431388&fm=26&gp=0.jpg', 1399.0, 'user3', '品牌: 麻霖适用年龄: 30-34周岁材质: 羊绒尺码: S M L图案: 纯色领子: 圆领风格: 通勤通勤: 文艺颜色分类: 杏色 米白面料材质: 羊绒袖型: 常规组合形式: 单件货号: 19WM1596成分含量: 96%及以上年份季节: 2019年秋季袖长: 长袖款式: 套头厚薄: 加厚衣长: 常规服装版型: 宽松', '2017-06-01', 'sale'),
(2, '阿玛尼唇釉新款口红烂番茄色405 204 400', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1575715673561&di=95e92dae6688eef26b5ff07812efc9d4&imgtype=0&src=http%3A%2F%2Fpic6.58cdn.com.cn%2Fzhuanzh%2Fn_v299d82de24a67488a8d5725ea85b0e1d9.jpg%3Fw%3D750%26h%3D0', 56.0, 'user3', '转手原因：怀孕了，为了宝宝的健康，化妆品都不用了，年头跟我老公去香港买到两支，还有一支是我生日闺蜜送我的，一共三支低价出了！！！全新的！！我去心心鉴定过了，正品，支持你去坚定的，假了包退，可以直接拍下，点我想要联系我！', '2019-11-11', 'sale');

INSERT INTO bargain(buyer_name, product_id) VALUES
('user1', 1),
('user1', 2),
('user1', 3),
('user2', 1),
('user2', 3),
('user3', 1),
('user3', 2);

INSERT INTO comment(buyer_name, product_id, content, time) VALUES
('user1', 1, 'good', '09.09.09'),
('user1', 1, 'very good', '10.10.10'),
('user1', 1, 'very very very good', '19.09.19');
