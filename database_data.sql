USE pkuidlefish;
TRUNCATE TABLE account;
ALTER TABLE account AUTO_INCREMENT = 1000;
TRUNCATE TABLE bargain;
TRUNCATE TABLE category;
TRUNCATE TABLE comment;
TRUNCATE TABLE product;
TRUNCATE TABLE transaction;

INSERT INTO account(username, en_password, birth, sex, email, phone, account_status, update_time) VALUES
('admin', myhash('admin'), '2000-01-05', '男', 'admin@pku.edu.cn', '10086000005', 'admin', '2006-06-06'),
('user1', myhash('password1'), '2000-01-01', '男', 'user1@pku.edu.cn', '10086000001', 'seller', '2011-11-11'),
('user2', myhash('password2'), '2000-01-02', '女', 'user2@pku.edu.cn', '10086000002', 'forseller', '2010-10-10'),
('user3', myhash('password3'), '2000-01-03', '女', 'user3@pku.edu.cn', '10086000003', 'seller', '2009-09-09'),
('user4', myhash('password4'), '2000-01-04', '女', 'user4@pku.edu.cn', '10086000004', 'buyer', '2008-08-08'),
('user5', myhash('password5'), '2000-01-05', '男', 'user5@pku.edu.cn', '10086000005', 'buyer', '2007-07-07');

INSERT INTO category (category, category_name) VALUES
(1, '穿戴服饰'),
(2, '手机数码'),
(3, '美容化妆'),
(4, '运动户外'),
(5, '图书文娱');

INSERT INTO product
(category, title, imgsrc, price, seller_name, description, update_time, product_status) VALUES
(1, '麻霖原创女装2019冬新品 内蒙古阿拉善 100%软糯细腻山羊绒衫', '/product1.jpg', 7533.1, 'user2', '品牌: 麻霖适用年龄: 30-34周岁材质: 羊绒尺码: S M L图案: 纯色领子: 圆领风格: 通勤通勤: 文艺颜色分类: 杏色 米白面料材质: 羊绒袖型: 常规组合形式: 单件货号: 19WM1596成分含量: 96%及以上年份季节: 2019年秋季袖长: 长袖款式: 套头厚薄: 加厚衣长: 常规服装版型: 宽松', '2017-06-01' , 'sale'),
(1, '【店长推荐 99元2件】长袖T恤男装2019秋冬季新款衣服加绒加厚保暖内衣高领打底衫修身体恤 656白色+316黑色 XL码', '/product2.jpg', 3606.3, 'user3', '品牌: 麻霖适用年龄: 30-34周岁材质: 羊绒尺码: S M L图案: 纯色领子: 圆领风格: 通勤通勤: 文艺颜色分类: 杏色 米白面料材质: 羊绒袖型: 常规组合形式: 单件货号: 19WM1596成分含量: 96%及以上年份季节: 2019年秋季袖长: 长袖款式: 套头厚薄: 加厚衣长: 常规服装版型: 宽松', '2018-05-30' , 'sale'),
(1, '加绒加厚两件套连衣裙秋冬女装气质洋气阔太太蕾丝长袖套装打底裙', '/product3.jpg', 8746.7, 'user4', '品牌: 麻霖适用年龄: 30-34周岁材质: 羊绒尺码: S M L图案: 纯色领子: 圆领风格: 通勤通勤: 文艺颜色分类: 杏色 米白面料材质: 羊绒袖型: 常规组合形式: 单件货号: 19WM1596成分含量: 96%及以上年份季节: 2019年秋季袖长: 长袖款式: 套头厚薄: 加厚衣长: 常规服装版型: 宽松', '2019-0830' , 'sale'),
(1, '温暖曲线 M家◆针织连衣裙女2019新款秋冬羊毛长袖内搭打底裙子潮', '/product4.jpg', 387.0, 'user5', '品牌: 麻霖适用年龄: 30-34周岁材质: 羊绒尺码: S M L图案: 纯色领子: 圆领风格: 通勤通勤: 文艺颜色分类: 杏色 米白面料材质: 羊绒袖型: 常规组合形式: 单件货号: 19WM1596成分含量: 96%及以上年份季节: 2019年秋季袖长: 长袖款式: 套头厚薄: 加厚衣长: 常规服装版型: 宽松', '2019-10-09' , 'sale'),
(1, '派克服男士2019冬款整貂水貂内胆中老年貂皮大衣爸爸装尼克服外套', '/product5.jpg', 2241.6, 'user6', '品牌: 麻霖适用年龄: 30-34周岁材质: 羊绒尺码: S M L图案: 纯色领子: 圆领风格: 通勤通勤: 文艺颜色分类: 杏色 米白面料材质: 羊绒袖型: 常规组合形式: 单件货号: 19WM1596成分含量: 96%及以上年份季节: 2019年秋季袖长: 长袖款式: 套头厚薄: 加厚衣长: 常规服装版型: 宽松', '2015-12-12' , 'sale'),
(1, '羽绒服男中长款2019新款韩版潮流修身帅气加厚冬季男士保暖外套潮', '/product6.jpg', 5289.7, 'user7', '品牌: 麻霖适用年龄: 30-34周岁材质: 羊绒尺码: S M L图案: 纯色领子: 圆领风格: 通勤通勤: 文艺颜色分类: 杏色 米白面料材质: 羊绒袖型: 常规组合形式: 单件货号: 19WM1596成分含量: 96%及以上年份季节: 2019年秋季袖长: 长袖款式: 套头厚薄: 加厚衣长: 常规服装版型: 宽松', '2016-05-05' , 'sale'),
(1, '欧洲站2019冬季毛衣中长款长裙配大衣过膝针织长袖打底连衣裙子', '/product7.jpg', 6612.5, 'user8', '品牌: 麻霖适用年龄: 30-34周岁材质: 羊绒尺码: S M L图案: 纯色领子: 圆领风格: 通勤通勤: 文艺颜色分类: 杏色 米白面料材质: 羊绒袖型: 常规组合形式: 单件货号: 19WM1596成分含量: 96%及以上年份季节: 2019年秋季袖长: 长袖款式: 套头厚薄: 加厚衣长: 常规服装版型: 宽松', '2017-06-01' , 'sale'),
(1, '小香风针织连衣裙2019秋冬新款长袖羊毛裙子中长款百搭宽松打底裙', '/product8.jpg', 3202.0, 'user9', '品牌: 麻霖适用年龄: 30-34周岁材质: 羊绒尺码: S M L图案: 纯色领子: 圆领风格: 通勤通勤: 文艺颜色分类: 杏色 米白面料材质: 羊绒袖型: 常规组合形式: 单件货号: 19WM1596成分含量: 96%及以上年份季节: 2019年秋季袖长: 长袖款式: 套头厚薄: 加厚衣长: 常规服装版型: 宽松', '2018-05-30' , 'sale'),
(1, '韩国金丝绒阔腿裤女秋冬2019新款加绒高腰垂感宽松拖地直筒长裤子', '/product9.jpg', 4722.6, 'user10', '品牌: 麻霖适用年龄: 30-34周岁材质: 羊绒尺码: S M L图案: 纯色领子: 圆领风格: 通勤通勤: 文艺颜色分类: 杏色 米白面料材质: 羊绒袖型: 常规组合形式: 单件货号: 19WM1596成分含量: 96%及以上年份季节: 2019年秋季袖长: 长袖款式: 套头厚薄: 加厚衣长: 常规服装版型: 宽松', '2019-0830' , 'sale'),
(1, 'ZADY优雅轻奢百褶灯笼袖法式连衣裙2019秋季新款长袖高腰开叉中裙', '/product10.jpg', 4963.1, 'user11', '证书编号：2019011606187963证书状态：有效产品名称：TD-LTE数字移动电话机3C规格型号：SEA-AL10(开关电源适配器：HW-100400C00，输出：5VDC，2A 或 9VDC，2A...产品名称：HUAWEI nova 5 Pro华为型号: nova 5 Pro机身颜色: 苏音蓝 绮境森林 珊瑚橙 仲夏紫 珊瑚橙·星耀礼盒版 亮黑色运行内存RAM: 8GB存储容量: 8+128GB 8+256GB网络模式: 双卡双待CPU型号: 980', '2019-10-09' , 'sale'),
(2, '【当天发/24期分期】Huawei/华为 Mate 30 Pro 5G版手机官方旗舰店华为手机Mate30 Pro5g全网通新款正品p30', '/product11.jpg', 8008.9, 'user12', '证书编号：2019011606187963证书状态：有效产品名称：TD-LTE数字移动电话机3C规格型号：SEA-AL10(开关电源适配器：HW-100400C00，输出：5VDC，2A 或 9VDC，2A...产品名称：HUAWEI nova 5 Pro华为型号: nova 5 Pro机身颜色: 苏音蓝 绮境森林 珊瑚橙 仲夏紫 珊瑚橙·星耀礼盒版 亮黑色运行内存RAM: 8GB存储容量: 8+128GB 8+256GB网络模式: 双卡双待CPU型号: 981', '2015-12-12' , 'sale'),
(2, '【当天发/24期分期/咨询省1250元】Huawei/华为 P30手机正品华为官方旗舰店华为直降p30pro新款mate30pro 5g', '/product12.jpg', 6293.0, 'user13', '证书编号：2019011606187963证书状态：有效产品名称：TD-LTE数字移动电话机3C规格型号：SEA-AL10(开关电源适配器：HW-100400C00，输出：5VDC，2A 或 9VDC，2A...产品名称：HUAWEI nova 5 Pro华为型号: nova 5 Pro机身颜色: 苏音蓝 绮境森林 珊瑚橙 仲夏紫 珊瑚橙·星耀礼盒版 亮黑色运行内存RAM: 8GB存储容量: 8+128GB 8+256GB网络模式: 双卡双待CPU型号: 982', '2016-05-05' , 'sale'),
(2, '8+256G版2499起+送无线充 xiaomi/小米9手机 plus官方旗舰店9Pro5G网骁龙855透明红米K20pro尊享探索九cc9pro', '/product13.jpg', 4634.6, 'user14', '证书编号：2019011606187963证书状态：有效产品名称：TD-LTE数字移动电话机3C规格型号：SEA-AL10(开关电源适配器：HW-100400C00，输出：5VDC，2A 或 9VDC，2A...产品名称：HUAWEI nova 5 Pro华为型号: nova 5 Pro机身颜色: 苏音蓝 绮境森林 珊瑚橙 仲夏紫 珊瑚橙·星耀礼盒版 亮黑色运行内存RAM: 8GB存储容量: 8+128GB 8+256GB网络模式: 双卡双待CPU型号: 983', '2017-06-01' , 'sale'),
(2, '8+256G版2499起+送无线充 xiaomi/小米9手机 plus官方旗舰店9Pro5G网骁龙855透明红米K20pro尊享探索九cc9pro', '/product14.jpg', 1943.4, 'user15', '证书编号：2019011606187963证书状态：有效产品名称：TD-LTE数字移动电话机3C规格型号：SEA-AL10(开关电源适配器：HW-100400C00，输出：5VDC，2A 或 9VDC，2A...产品名称：HUAWEI nova 5 Pro华为型号: nova 5 Pro机身颜色: 苏音蓝 绮境森林 珊瑚橙 仲夏紫 珊瑚橙·星耀礼盒版 亮黑色运行内存RAM: 8GB存储容量: 8+128GB 8+256GB网络模式: 双卡双待CPU型号: 984', '2018-05-30' , 'sale'),
(2, '倍思手机指环支架扣苹果X环扣手指支扣环xs磁吸华为oppo女手环6指扣支驾vivo环指配件女通用抖音神器贴壳环指', '/product15.jpg', 5973.8, 'user16', '证书编号：2019011606187963证书状态：有效产品名称：TD-LTE数字移动电话机3C规格型号：SEA-AL10(开关电源适配器：HW-100400C00，输出：5VDC，2A 或 9VDC，2A...产品名称：HUAWEI nova 5 Pro华为型号: nova 5 Pro机身颜色: 苏音蓝 绮境森林 珊瑚橙 仲夏紫 珊瑚橙·星耀礼盒版 亮黑色运行内存RAM: 8GB存储容量: 8+128GB 8+256GB网络模式: 双卡双待CPU型号: 985', '2019-0830' , 'sale'),
(2, '手机夹子平板夹转接器2三双多机位美颜补光灯声卡盘话筒架主播直播自拍杆三脚架直架配件通用多功能', '/product16.jpg', 3386.6, 'user17', '证书编号：2019011606187963证书状态：有效产品名称：TD-LTE数字移动电话机3C规格型号：SEA-AL10(开关电源适配器：HW-100400C00，输出：5VDC，2A 或 9VDC，2A...产品名称：HUAWEI nova 5 Pro华为型号: nova 5 Pro机身颜色: 苏音蓝 绮境森林 珊瑚橙 仲夏紫 珊瑚橙·星耀礼盒版 亮黑色运行内存RAM: 8GB存储容量: 8+128GB 8+256GB网络模式: 双卡双待CPU型号: 986', '2019-10-09' , 'sale'),
(2, '直播支架手机补光灯美颜三脚架全套设备抖音神器网红主播多功能落地户外快手桌面拍摄通用配件双拍照自拍便携', '/product17.jpg', 7750.9, 'user18', '证书编号：2019011606187963证书状态：有效产品名称：TD-LTE数字移动电话机3C规格型号：SEA-AL10(开关电源适配器：HW-100400C00，输出：5VDC，2A 或 9VDC，2A...产品名称：HUAWEI nova 5 Pro华为型号: nova 5 Pro机身颜色: 苏音蓝 绮境森林 珊瑚橙 仲夏紫 珊瑚橙·星耀礼盒版 亮黑色运行内存RAM: 8GB存储容量: 8+128GB 8+256GB网络模式: 双卡双待CPU型号: 987', '2015-12-12' , 'sale'),
(2, '手机红外线发射器苹果7安卓万能遥控器空调电视接收遥控头精灵x外接配件iphone8外置小米通用型vivo华为oppo', '/product18.jpg', 4419.5, 'user19', '证书编号：2019011606187963证书状态：有效产品名称：TD-LTE数字移动电话机3C规格型号：SEA-AL10(开关电源适配器：HW-100400C00，输出：5VDC，2A 或 9VDC，2A...产品名称：HUAWEI nova 5 Pro华为型号: nova 5 Pro机身颜色: 苏音蓝 绮境森林 珊瑚橙 仲夏紫 珊瑚橙·星耀礼盒版 亮黑色运行内存RAM: 8GB存储容量: 8+128GB 8+256GB网络模式: 双卡双待CPU型号: 988', '2016-05-05' , 'sale'),
(2, '手机指环扣支架手机扣环指扣女壳男磁吸支撑扣环懒人伸缩多功能超薄个性创意桌面架车载贴吸盘式隐形防摔配件', '/product19.jpg', 8446.7, 'user20', '证书编号：2019011606187963证书状态：有效产品名称：TD-LTE数字移动电话机3C规格型号：SEA-AL10(开关电源适配器：HW-100400C00，输出：5VDC，2A 或 9VDC，2A...产品名称：HUAWEI nova 5 Pro华为型号: nova 5 Pro机身颜色: 苏音蓝 绮境森林 珊瑚橙 仲夏紫 珊瑚橙·星耀礼盒版 亮黑色运行内存RAM: 8GB存储容量: 8+128GB 8+256GB网络模式: 双卡双待CPU型号: 989', '2017-06-01' , 'sale'),
(2, 'MARTVSEN手机稳定器三脚架延长杆DJI大疆灵眸手机云台osmo mobile3智云Soomth4Q2M2配件视频加长杆便携可伸缩', '/product20.jpg', 1490.9, 'user1', '证书编号：2019011606187963证书状态：有效产品名称：TD-LTE数字移动电话机3C规格型号：SEA-AL10(开关电源适配器：HW-100400C00，输出：5VDC，2A 或 9VDC，2A...产品名称：HUAWEI nova 5 Pro华为型号: nova 5 Pro机身颜色: 苏音蓝 绮境森林 珊瑚橙 仲夏紫 珊瑚橙·星耀礼盒版 亮黑色运行内存RAM: 8GB存储容量: 8+128GB 8+256GB网络模式: 双卡双待CPU型号: 990', '2018-05-30' , 'sale');


INSERT INTO product
(category, title, imgsrc, price, seller_name, description, update_time, product_status) VALUES
(3, '花印日本进口面霜女保湿补水滋润控油学生护肤品化妆品', '/product21.jpg', 4310.5, 'user2', '35 年来始终如一。备受瞩目、畅销全球的 SK-II 护肤精华露（神仙水）蕴含超过90%的天然生物成分PITERA，集维生素、矿物质和氨基酸等多种微量营养素于一身。', '2019-0830' , 'sale'),
(3, '诗莎娜糖果色化妆刷套装全套散粉刷腮红刷粉底刷子化妆工具眼影刷', '/product22.jpg', 3135.7, 'user3', '36 年来始终如一。备受瞩目、畅销全球的 SK-II 护肤精华露（神仙水 ）蕴含超过90%的天然生物成分PITERA ，集维生素、矿物质和氨基酸等多种微量营养素于一身。', '2019-10-09' , 'sale'),
(3, '法兰琳卡精油玫瑰护肤修护倍润精华油滋养渗透锁水保湿美容化妆品', '/product23.jpg', 6112.2, 'user4', '37 年来始终如一。备受瞩目、畅销全球的 SK-II 护肤精华露（神仙水 ）蕴含超过90%的天然生物成分PITERA ，集维生素、矿物质和氨基酸等多种微量营养素于一身。', '2015-12-12' , 'sale'),
(3, 'MKE美容仪器家用洗脸仪李佳导入仪琦推荐琪按摩V脸部射频清洁面仪', '/product24.jpg', 575.8, 'user5', '38 年来始终如一。备受瞩目、畅销全球的 SK-II 护肤精华露（神仙水 ）蕴含超过90%的天然生物成分PITERA ，集维生素、矿物质和氨基酸等多种微量营养素于一身。', '2016-05-05' , 'sale'),
(3, '【5包装】欧亿姿洗脸巾一次性纯棉女洗面擦脸卸妆棉化妆洁面巾纸', '/product25.jpg', 498.4, 'user6', '39 年来始终如一。备受瞩目、畅销全球的 SK-II 护肤精华露（神仙水 ）蕴含超过90%的天然生物成分PITERA ，集维生素、矿物质和氨基酸等多种微量营养素于一身。', '2017-06-01' , 'sale'),
(3, '【骆王宇推荐】LADES/蓝蒂丝陵姬化妆刷套装14支全套专业彩妆工具', '/product26.jpg', 6551.5, 'user7', '40 年来始终如一。备受瞩目、畅销全球的 SK-II 护肤精华露（神仙水 ）蕴含超过90%的天然生物成分PITERA ，集维生素、矿物质和氨基酸等多种微量营养素于一身。', '2018-05-30' , 'sale'),
(3, '面霜眼霜分装瓶避光双层真空面油按压瓶旅行携带化妆品收纳盒', '/product27.jpg', 4809.1, 'user8', '41 年来始终如一。备受瞩目、畅销全球的 SK-II 护肤精华露（神仙水 ）蕴含超过90%的天然生物成分PITERA ，集维生素、矿物质和氨基酸等多种微量营养素于一身。', '2019-0830' , 'sale'),
(3, '悦美时刻压取瓶旅行卸妆水瓶爽肤补水化妆棉用按压式化妆品分装瓶', '/product28.jpg', 2229.6, 'user9', '42 年来始终如一。备受瞩目、畅销全球的 SK-II 护肤精华露（神仙水 ）蕴含超过90%的天然生物成分PITERA ，集维生素、矿物质和氨基酸等多种微量营养素于一身。', '2019-10-09' , 'sale'),
(3, '大宝官方旗舰店美容日霜 舒缓肌肤面霜补水保湿滋润护肤化妆品', '/product29.jpg', 1551.4, 'user10', '43 年来始终如一。备受瞩目、畅销全球的 SK-II 护肤精华露（神仙水 ）蕴含超过90%的天然生物成分PITERA ，集维生素、矿物质和氨基酸等多种微量营养素于一身。', '2015-12-12' , 'sale'),
(3, '艾琳咪|AILINMI新品海绵蛋柔软虾粉蛋蛋底妆海绵美妆蛋化妆粉扑', '/product30.jpg', 2884.6, 'user11', '44 年来始终如一。备受瞩目、畅销全球的 SK-II 护肤精华露（神仙水 ）蕴含超过90%的天然生物成分PITERA ，集维生素、矿物质和氨基酸等多种微量营养素于一身。', '2016-05-05' , 'sale'),
(4, '男装 仿羊羔绒运动长裤 418709 裤内裆长范围70-76CM 优衣库', '/product31.jpg', 5594.7, 'user12', '适合雪地徒步： 全面包覆头部的连帽，保暖口袋，拉链后面较大的翻盖，可将身体感知温度提高3°C。防水，保暖温度不低于-10°C', '2017-06-01' , 'sale'),
(4, '华为运动健康生态款易跑MINI-C家用款小型超折叠健身房专用跑步机', '/product32.jpg', 3956.9, 'user13', '适合雪地徒步： 全面包覆头部的连帽，保暖口袋，拉链后面较大的翻盖，可将身体感知温度提高3°C。防水，保暖温度不低于-10°C', '2018-05-30' , 'sale'),
(4, '迪卡侬达人同款护膝运动专业篮球跑步羽毛球深蹲半月板保暖TARMAK', '/product33.jpg', 6721.2, 'user14', '适合雪地徒步： 全面包覆头部的连帽，保暖口袋，拉链后面较大的翻盖，可将身体感知温度提高3°C。防水，保暖温度不低于-10°C', '2019-0830' , 'sale'),
(4, '舒华智能跑步机家用款小型静音室内运动减肥神器全折叠走步机E3', '/product34.jpg', 4455.6, 'user15', '适合雪地徒步： 全面包覆头部的连帽，保暖口袋，拉链后面较大的翻盖，可将身体感知温度提高3°C。防水，保暖温度不低于-10°C', '2019-10-09' , 'sale'),
(4, '橙练深层肌肉放松筋膜枪运动健身电动腿部按摩器静音颈经膜松解枪', '/product35.jpg', 6028.6, 'user16', '适合雪地徒步： 全面包覆头部的连帽，保暖口袋，拉链后面较大的翻盖，可将身体感知温度提高3°C。防水，保暖温度不低于-10°C', '2015-12-12' , 'sale'),
(4, '迪卡侬羽绒服男秋冬新款户外保暖加厚面包服滑雪短款夹克WEDZE1', '/product36.jpg', 991.1, 'user17', '适合雪地徒步： 全面包覆头部的连帽，保暖口袋，拉链后面较大的翻盖，可将身体感知温度提高3°C。防水，保暖温度不低于-10°C', '2016-05-05' , 'sale'),
(4, '北山狼烤鱼夹烧烤夹子网夹不锈铁烧烤工具户外用品烧烤炉配件9寸', '/product37.jpg', 2423.9, 'user18', '适合雪地徒步： 全面包覆头部的连帽，保暖口袋，拉链后面较大的翻盖，可将身体感知温度提高3°C。防水，保暖温度不低于-10°C', '2017-06-01' , 'sale'),
(4, '900压缩饼干200g*20铁盒装户外旅行代餐充饥饱腹零食', '/product38.jpg', 994.9, 'user19', '适合雪地徒步： 全面包覆头部的连帽，保暖口袋，拉链后面较大的翻盖，可将身体感知温度提高3°C。防水，保暖温度不低于-10°C', '2018-05-30' , 'sale'),
(4, '探路者防护面罩 19秋冬户外男女通款登山防风HIMEX面罩TELH90328', '/product39.jpg', 361.1, 'user20', '适合雪地徒步： 全面包覆头部的连帽，保暖口袋，拉链后面较大的翻盖，可将身体感知温度提高3°C。防水，保暖温度不低于-10°C', '2019-0830' , 'sale'),
(4, '上海丽能压缩饼干90g*20包户外代餐干粮饱腹即食零食品批发', '/product40.jpg', 163.5, 'user1', '适合雪地徒步： 全面包覆头部的连帽，保暖口袋，拉链后面较大的翻盖，可将身体感知温度提高3°C。防水，保暖温度不低于-10°C', '2019-10-09' , 'sale');

INSERT INTO product
(category, title, imgsrc, price, seller_name, description, update_time, product_status) VALUES
(5, '哈利波特书全套1-8册正版 2019新版纪念版中文全套8册被诅咒的孩子魔法石死亡圣器 8-9-10-15周岁三四五六年级小学生畅销儿童文学', '/product41.jpg', 4469.6, 'user2', '一岁的哈利·波特失去父母后，神秘地出现在姨父姨妈家的门前。哈利在姨父家饱受欺凌，度过十年极其痛苦的日子。姨父和姨妈好似凶神恶煞，他们那混世魔王儿子达力——一个肥胖、娇惯、欺负人的大块头，更是经济对哈利拳脚相加。哈利的“房间”是位于楼梯口的一个又暗又小的碗橱。十年来，从来没有人为他过过生日。', '2015-12-12' , 'sale'),
(5, 'DHS/红双喜T919乒乓球桌迷你家用室内折叠乒乓球台多功能娱乐', '/product42.jpg', 9697.7, 'user3', '一岁的哈利·波特失去父母后，神秘地出现在姨父姨妈家的门前。哈利在姨父家饱受欺凌，度过十年极其痛苦的日子。姨父和姨妈好似凶神恶煞，他们那混世魔王儿子达力——一个肥胖、娇惯、欺负人的大块头，更是经济对哈利拳脚相加。哈利的“房间”是位于楼梯口的一个又暗又小的碗橱。十年来，从来没有人为他过过生日。', '2016-05-05' , 'sale'),
(5, '迪卡侬飞镖盘套装 家用室内电子飞镖靶双人娱乐软式飞镖GEOLOGIC', '/product43.jpg', 4635.3, 'user4', '一岁的哈利·波特失去父母后，神秘地出现在姨父姨妈家的门前。哈利在姨父家饱受欺凌，度过十年极其痛苦的日子。姨父和姨妈好似凶神恶煞，他们那混世魔王儿子达力——一个肥胖、娇惯、欺负人的大块头，更是经济对哈利拳脚相加。哈利的“房间”是位于楼梯口的一个又暗又小的碗橱。十年来，从来没有人为他过过生日。', '2017-06-01' , 'sale'),
(5, '奔驰E300L/C级/S级/GLC新E级V260L后排娱乐系统车载头枕屏显示器', '/product44.jpg', 1340.2, 'user5', '一岁的哈利·波特失去父母后，神秘地出现在姨父姨妈家的门前。哈利在姨父家饱受欺凌，度过十年极其痛苦的日子。姨父和姨妈好似凶神恶煞，他们那混世魔王儿子达力——一个肥胖、娇惯、欺负人的大块头，更是经济对哈利拳脚相加。哈利的“房间”是位于楼梯口的一个又暗又小的碗橱。十年来，从来没有人为他过过生日。', '2018-05-30' , 'sale'),
(5, '大型游乐场推币投币游戏机儿童乐园游艺厅动漫电玩城商用成人娱乐', '/product45.jpg', 3092.8, 'user6', '一岁的哈利·波特失去父母后，神秘地出现在姨父姨妈家的门前。哈利在姨父家饱受欺凌，度过十年极其痛苦的日子。姨父和姨妈好似凶神恶煞，他们那混世魔王儿子达力——一个肥胖、娇惯、欺负人的大块头，更是经济对哈利拳脚相加。哈利的“房间”是位于楼梯口的一个又暗又小的碗橱。十年来，从来没有人为他过过生日。', '2019-0830' , 'sale'),
(5, '领券减 国际大奖小说注音版全20册 爱德华的奇妙之旅共二十本小学生课外书5-6-7-8-9岁儿童故事书籍童书外国儿童文学童话故事书AG', '/product46.jpg', 4794.3, 'user7', '一岁的哈利·波特失去父母后，神秘地出现在姨父姨妈家的门前。哈利在姨父家饱受欺凌，度过十年极其痛苦的日子。姨父和姨妈好似凶神恶煞，他们那混世魔王儿子达力——一个肥胖、娇惯、欺负人的大块头，更是经济对哈利拳脚相加。哈利的“房间”是位于楼梯口的一个又暗又小的碗橱。十年来，从来没有人为他过过生日。', '2019-10-09' , 'sale'),
(5, '【现货包邮】 中国文学史 1234卷 袁行霈 第三版 考研教材 中国文学史考研 中国现代文学史高等教育出版社中国文学史第3版全4卷', '/product47.jpg', 4728.4, 'user8', '一岁的哈利·波特失去父母后，神秘地出现在姨父姨妈家的门前。哈利在姨父家饱受欺凌，度过十年极其痛苦的日子。姨父和姨妈好似凶神恶煞，他们那混世魔王儿子达力——一个肥胖、娇惯、欺负人的大块头，更是经济对哈利拳脚相加。哈利的“房间”是位于楼梯口的一个又暗又小的碗橱。十年来，从来没有人为他过过生日。', '2015-12-12' , 'sale'),
(5, '【当当网 正版书籍】穆斯林的葬礼精装版霍达 著 有生命力的茅盾文学奖经典作品 现当代文学长篇爱情小说书', '/product48.jpg', 7074.0, 'user9', '一岁的哈利·波特失去父母后，神秘地出现在姨父姨妈家的门前。哈利在姨父家饱受欺凌，度过十年极其痛苦的日子。姨父和姨妈好似凶神恶煞，他们那混世魔王儿子达力——一个肥胖、娇惯、欺负人的大块头，更是经济对哈利拳脚相加。哈利的“房间”是位于楼梯口的一个又暗又小的碗橱。十年来，从来没有人为他过过生日。', '2016-05-05' , 'sale'),
(5, '【当当网 正版书籍】爱你就像爱生命 精装彩插典藏版 王小波著 当代文学阅读无法绕过的经典 见字如面何冰', '/product49.jpg', 2489.0, 'user10', '一岁的哈利·波特失去父母后，神秘地出现在姨父姨妈家的门前。哈利在姨父家饱受欺凌，度过十年极其痛苦的日子。姨父和姨妈好似凶神恶煞，他们那混世魔王儿子达力——一个肥胖、娇惯、欺负人的大块头，更是经济对哈利拳脚相加。哈利的“房间”是位于楼梯口的一个又暗又小的碗橱。十年来，从来没有人为他过过生日。', '2017-06-01' , 'sale'),
(5, '樊登推荐【当当网 正版包邮】亲密关系 通往灵魂的桥梁 新版 知见领袖克里斯多福孟新体悟新观念', '/product50.jpg', 9102.2, 'user11', '一岁的哈利·波特失去父母后，神秘地出现在姨父姨妈家的门前。哈利在姨父家饱受欺凌，度过十年极其痛苦的日子。姨父和姨妈好似凶神恶煞，他们那混世魔王儿子达力——一个肥胖、娇惯、欺负人的大块头，更是经济对哈利拳脚相加。哈利的“房间”是位于楼梯口的一个又暗又小的碗橱。十年来，从来没有人为他过过生日。', '2018-05-30' , 'sale');

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
