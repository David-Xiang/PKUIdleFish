<template>
    <el-container direction="vertical">
        <el-row :gutter="20" style="margin:5px">
            <el-col :span="3">名称</el-col>
            <el-col :span="20">
                <el-input
                    placeholder="请输入内容"
                    v-model="product.productInfo.title">
                </el-input>
            </el-col>
        </el-row>

        <el-row :gutter="20" style="margin:5px">
            <el-col :span="3">分类</el-col>
            <el-col :span="5" align="left">
                <el-select v-model="product.productInfo.category">
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
                    v-model="product.productInfo.description">
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
                    v-model="product.productInfo.imgsrc">
                </el-input>
            </el-col>
        </el-row>
        <el-row :gutter="20" style="margin:5px">
            <el-col :span="3">价格</el-col>
            <el-col :span="20" align="left">
                <el-input-number v-model="product.productInfo.price" :precision="2" :step="0.1"></el-input-number>
            </el-col>
        </el-row>
        <el-row :gutter="20" style="margin:5px">
            <el-button icon="el-icon-check" @click="$emit('modify', product)">提交</el-button>
        </el-row>
    </el-container>
</template>
<script>
import category from "../category.js"

export default{
    name: "modifyProduct",
    props: ["product"],
    data() {
        return {
            categoryLabel: category.categoryLabel
        }
    }   
}
</script>