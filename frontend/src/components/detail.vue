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
                        <el-button size="medium" @click="$emit('addCart', product)" icon="el-icon-circle-plus-outline">购物车</el-button>
                        <el-button size="medium" @click="$emit('purchase', product)">下单<i class="el-icon-shopping-cart-1 el-icon--right"></i></el-button>
                        <!--管理员下架,值得一提的是detail里找不到app的信息，所以userData是null会显示不出来，而且isLogin=false-->
                        <!-- <el-button type = "warning" size="medium" @click="offlineDialogVisible = true" icon="el-icon-delete" v-if="isLogin && userData.status==2">下架</el-button> -->

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

        <el-row style="margin:20px;">
            <el-col :span="24">
                <el-input placeholder="说说你的感受吧！" v-model="message" clearable @keyup.enter.native="$emit('comment', product, message); message=null;" >
                    <el-rate 
                    slot="prepend"
                    v-model="rate"
                    :colors="['#99A9BF', '#F7BA2A', '#FF9900']">
                </el-rate>
                <el-button slot="append" @click="$emit('comment', product, message);" icon="el-icon-chat-dot-square" >评价</el-button>
                </el-input>
            </el-col>
        </el-row>

<!--管理员下架-->
        <!-- <el-dialog
        title="即将永久下架商品"
        :visible.sync="offlineDialogVisible"
        width="500px"
        center>
        <div>将以管理员身份永久下架商品，该操作不可逆</div>
        <div align="left">
                        <div style="font-color: #F56C6C; font-size: 14px; display:inline-block;margin-bottom:10px;" >{{product.productInfo.title}}</div>
                        <div style="margin-bottom:10px;">
                            <div style="display:inline; color: red; font-size: 14px">¥</div>
                            <div style="display:inline; color: red; font-size: 22px">{{product.productInfo.price.toFixed(2)}}</div>
                        </div>
        </div>
        <span slot="footer" class="dialog-footer">
            <el-button @click="offlineDialogVisible = false">取 消</el-button>
            <el-button type="danger" @click="offlineDialogVisible = false">确定下架</el-button>
        </span>
        </el-dialog> -->

    </el-container>
</template>
<script>
import category from '../category.js'
export default{
    name: "detail",
    props: ["product"],
    data() {
        return {
            categoryLabel:category.categoryLabel,
            message: null,
            rate: null
        }
    }   
}
</script>