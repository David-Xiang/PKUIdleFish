package cn.edu.pku.course.database.idlefish.controller;

import cn.edu.pku.course.database.idlefish.entity.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.Arrays;
import java.util.List;

@Controller
public class MockController {
    @GetMapping("/mock/login")
    @ResponseBody
    public ActionResponse login(@RequestParam String username, @RequestParam String password){
        return new ActionResponse(true);
    }

    @GetMapping("/mock/user")
    @ResponseBody
    public UserResponse getUsers(){
        User user1 = new User(
                123763456,
                "向东伟",
                new Date(97, 4, 15),
                "M",
                "xingaondwe@pku.edu.cn",
                "15000000234",
                2);
        User user2 = new User(
                456356345,
                "甄有钱",
                new Date(100, 12, 15),
                "M",
                "zhenyq@pku.edu.cn",
                "150021300234",
                0);
        User user3 = new User(
                956782563,
                "贾名牌",
                new Date(1990, 9, 8),
                "F",
                "mingpaijia@pku.edu.cn",
                "15000000234",
                1);
        User user4 = new User(
                456356345,
                "王黑户",
                new Date(0, 1, 1),
                "M",
                "heihuw@pku.edu.cn",
                "150021300234",
                -1);
        User user5 = new User(
                956782563,
                "李享麦",
                new Date(90, 9, 20),
                "F",
                "xiangmai@pku.edu.cn",
                "15000000234",
                3);
        return new UserResponse(Arrays.asList(user1, user2, user3, user4, user5));
    }

    @GetMapping("/related")
    @ResponseBody
    public List<ProductResponse> getOrders() {
        return Arrays.asList(getProducts(), getProducts());
    }

    @GetMapping("/cart")
    @ResponseBody
    public ProductResponse getCart() {
        return getProducts();
    }

    @GetMapping("/product")
    @ResponseBody
    public ProductResponse getProducts() {
        Product product1 = new Product(
                8365793,
                new ProductInfo(
                        0,
                        "麻霖原创女装2019冬新品 内蒙古阿拉善 100%软糯细腻山羊绒衫",
                        "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=878857037,200431388&fm=26&gp=0.jpg",
                        1399.00,
                        1234124,
                        "品牌: 麻霖适用年龄: 30-34周岁材质: 羊绒尺码: S M L图案: 纯色领子: 圆领风格: 通勤通勤: 文艺颜色分类: 杏色 米白面料材质: 羊绒袖型: 常规组合形式: 单件货号: 19WM1596成分含量: 96%及以上年份季节: 2019年秋季袖长: 长袖款式: 套头厚薄: 加厚衣长: 常规服装版型: 宽松",
                        new Date(119, 12, 05),
                        1
                ),
                Arrays.asList(
                        new Comment(
                                8365793,
                                new Date(119, 11, 30),
                                257255,
                                "五***鸭",
                                "面料舒服 款式大气 上身很有范 超满意 喜欢的赶紧下手吧"
                        ),
                        new Comment(
                                8365793,
                                new Date(119, 12, 05),
                                257255,
                                "伙***0",
                                "质量好，是羊绒的，柔软亲肤，很喜欢，一次买了5件宝贝，以后还会来。"
                        )
                ));
        Product product2 = new Product(
                8365793,
                new ProductInfo(
                        1,
                        "HUAWEI/华为nova 5 Pro麒麟980新品4800万AI四摄超级快充屏内指纹NFC手机",
                        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1575715338590&di=94dbbaeca0758c01902f028c78d54e41&imgtype=0&src=http%3A%2F%2Fclubimg.club.vmall.com%2Fdata%2Fattachment%2Fforum%2F201907%2F02%2F000729yts29urfnynxzpka.jpeg",
                        3099.00,
                        1234124,
                        "证书编号：2019011606187963证书状态：有效产品名称：TD-LTE数字移动电话机3C规格型号：SEA-AL10(开关电源适配器：HW-100400C00，输出：5VDC，2A 或 9VDC，2A...产品名称：HUAWEI nova 5 Pro华为型号: nova 5 Pro机身颜色: 苏音蓝 绮境森林 珊瑚橙 仲夏紫 珊瑚橙·星耀礼盒版 亮黑色运行内存RAM: 8GB存储容量: 8+128GB 8+256GB网络模式: 双卡双待CPU型号: 980",
                        null,
                        1
                ),
                Arrays.asList(
                        new Comment(
                                8365793,
                                new Date(119, 11, 30),
                                257255,
                                "t***2（匿名）",
                                "拍照清晰，有手机壳和耳机，运行速度快，充电更快，电池也耐用，中度一天没问题，苏宁服务很赞，物流很快\n"
                        ),
                        new Comment(
                                8365793,
                                new Date(119, 12, 05),
                                257255,
                                "流***逅",
                                "手感很好，超薄。电量耐用，中度一天吧，充电很快，半小时就差不多快满了。颜值也可以，可以看图片，后面可以当镜子。流畅度我打王者可以。价格目前也能接受。奥，还有拍照，坐在大教室最后一排排黑板，字迹清晰！总之，可以了！\n"
                        )
                ));
        Product product3 = new Product(
                8365793,
                new ProductInfo(
                        1,
                        "阿玛尼唇釉新款口红烂番茄色405 204 400",
                        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1575715673561&di=95e92dae6688eef26b5ff07812efc9d4&imgtype=0&src=http%3A%2F%2Fpic6.58cdn.com.cn%2Fzhuanzh%2Fn_v299d82de24a67488a8d5725ea85b0e1d9.jpg%3Fw%3D750%26h%3D0",
                        56.00,
                        1234124,
                        "转手原因：怀孕了，为了宝宝的健康，化妆品都不用了，年头跟我老公去香港买到两支，还有一支是我生日闺蜜送我的，一共三支低价出了！！！全新的！！我去心心鉴定过了，正品，支持你去坚定的，假了包退，可以直接拍下，点我想要联系我！",
                        null,
                        1
                ),
                Arrays.asList(
                        new Comment(
                                8365793,
                                new Date(119, 11, 30),
                                257255,
                                "t***2（匿名）",
                                "妆滋润，显色度很好，稀饭，闺蜜生日礼物买的，超级无敌爱，朋友都说好看，颜色也持久很满意秋冬非常适合显色度很好不显唇纹，精致又高级滋润不油腻，成膜速度很快，礼盒精美， 刷头很好用，香水味道也很好颜色很"
                        ),
                        new Comment(
                                8365793,
                                new Date(119, 12, 05),
                                257255,
                                "流***逅",
                                "唇色深也能轻松遮盖，持久很满意,包装太精致了，不油腻也不干唇,送的小样很喜欢，持妆效果很好哦，值得推荐,味道也好闻，不挑肤色，经典番茄红，太爱了,果然非常棒，很喜欢小巧方便携带，任何肤色都可以\n"
                        )
                ));
        return new ProductResponse(Arrays.asList(product1, product2, product3));
    }
}
