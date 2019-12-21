<template>
    <el-container direction="vertical">
        <el-row :gutter="20" style="margin:5px">
            <el-col :span="3">名称</el-col>
            <el-col :span="20">
                <el-input
                    placeholder="请输入内容"
                    v-model="title">
                </el-input>
            </el-col>
        </el-row>

        <el-row :gutter="20" style="margin:5px">
            <el-col :span="3">分类</el-col>
            <el-col :span="5" align="left">
                <el-select v-model="labelNum">
                    <el-option v-for="(label, index) in categoryLabel" :label="label" :key="index" :value="index"></el-option>
                </el-select>
            </el-col>
        </el-row>
        
        <el-row :gutter="20" style="margin:5px">
            <el-col :span="3">描述</el-col>
            <el-col :span="20">
                <el-input
                    type="textarea"
                    autosize
                    placeholder="请输入内容"
                    v-model="description">
                </el-input>
            </el-col>
        </el-row>
        <el-row :gutter="20" style="margin:5px">
            <el-col :span="3">图片链接</el-col>
            <el-col :span="20">
                <el-input
                    type="textarea"
                    autosize
                    placeholder="请输入内容"
                    v-model="imgsrc">
                </el-input>
            </el-col>
        </el-row>
        <el-row :gutter="20" style="margin:5px">
            <el-col :span="3">价格</el-col>
            <el-col :span="20" align="left">
                <el-input-number v-model="price" :precision="2" :step="0.1"></el-input-number>
            </el-col>
        </el-row>
        <el-row :gutter="20" style="margin:5px">
            <el-button icon="el-icon-check" @click="$emit('modify', getInfo())">提交</el-button>
        </el-row>
    </el-container>
</template>
<script>
import category from "../category.js"

export default{
    name: "newProduct",
    data() {
        return {
            product_id: 0,
            title: "",
            description: "",
            imgsrc: "",
            price: 0,
            labelNum: 0,
            categoryLabel: category.categoryLabel,
            getInfo() {
                return {
                    product_id : this.product_id,
                    productInfo: {
                        title : this.title,
                        description : this.description,
                        imgsrc : this.imgsrc,
                        price : this.price,
                        category: this.labelNum,
                    },
                    comments: []
                }
            }
        }
    }   
}
</script>