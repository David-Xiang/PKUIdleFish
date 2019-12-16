<template>
    <el-container direction="vertical">
        <el-row>
            <el-col :span="8">
                <el-card style="width: 100%; height: 200px; margin-bottom:20px" body-style="padding: 0px">
                    <img style="width: 100%; height: 200px; object-fit: cover;" v-bind:src="product.productInfo.imgsrc" @click="handleProduct(product)"/> 
                </el-card>
            </el-col>
            <el-col :span="12" :offset="1">
                <div align="left">
                    <div style="font-color: #F56C6C; font-size: 14px; display:inline-block;margin-bottom:10px;" >{{product.productInfo.title}}</div>
                    <div style="margin-bottom:10px;">
                        <div style="display:inline; color: red; font-size: 14px">¥</div>
                        <div style="display:inline; color: red; font-size: 22px">{{product.productInfo.price.toFixed(2)}}</div>
                    </div>
                    <el-button-group>
                        <el-button size="medium" icon="el-icon-chat-dot-square">评论</el-button>
                        <el-button size="medium" @click="addCart(product.productInfo.title)" icon="el-icon-circle-plus-outline">购物车</el-button>
                        <el-button size="medium" @click="purchase(product.productInfo.title)">下单<i class="el-icon-shopping-cart-1 el-icon--right"></i></el-button>
                    </el-button-group>
                </div>
            </el-col>
        </el-row>

        <el-collapse accordion>
            <el-collapse-item title="品类 Category">
                <div align="left">{{categoryLabel[product.productInfo.category]}}</div>
            </el-collapse-item>
            <el-collapse-item title="卖家 Seller">
                <div align="left">{{product.productInfo.seller_name}}</div>
            </el-collapse-item>
            <el-collapse-item title="描述 Description">
                <div align="left">{{product.productInfo.description}}</div>
            </el-collapse-item>
            <el-collapse-item title="评价 Comments">
                <el-table :data="product.comments">
                    <el-table-column width="120" property="buyer_name" show-overflow-tooltip label="姓名"></el-table-column>
                    <el-table-column width="100" property="time" label="日期"></el-table-column>
                    <el-table-column property="content" :show-overflow-tooltip="true" label="评论"></el-table-column>
                </el-table>
            </el-collapse-item>
        </el-collapse>
    </el-container>
</template>
<script>
export default{
    name: "detail",
    props: ["product"],
    data() {
        return {
            categoryLabel:[
                "全部分类", "穿戴服饰", "手机数码", "美容化妆"
            ]
        }
    }   
}
</script>