package cn.edu.pku.course.database.idlefish.controller;

import cn.edu.pku.course.database.idlefish.entity.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

@Controller
public class MockController {
    @GetMapping("/mock")
    @ResponseBody
    public XML getMethod() {
        XML xml = new XML();
        Comment comment = new Comment();
        comment.buyerName = "如花";
        comment.content = "衣服很好看，穿上显瘦";
        GoodBasicInfo basicInfo = new GoodBasicInfo();
        basicInfo.name = "衬衫";
        basicInfo.describe = "爆款女士衬衫，网红最爱";
        basicInfo.label = "衣服";
        basicInfo.price = 100.0;
        basicInfo.sellerID = 111;
        GoodInfo info = new GoodInfo();
        info.basicInfo = basicInfo;
        info.comments = Arrays.asList(comment);
        Good good = new Good();
        good.info = info;
        good.goodID = 0;
        xml.goods = Arrays.asList(good);
        return xml;
    }

}
