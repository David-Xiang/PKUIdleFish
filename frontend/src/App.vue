<template>
  <div id="app">
    <el-container>
      <el-header style="font-size: 40px; color: #820010">北大有鱼，其名为闲。</el-header>
      <el-container>
        <el-aside width="100px">
          <el-menu>
            <el-menu-item :index="'index'">
              <template slot="title"><i class="el-icon-menu"></i>首页</template>
            </el-menu-item>
            <el-menu-item :index="'analysis'">
              <template slot="title"><i class="el-icon-menu"></i>分析</template>
            </el-menu-item>
          </el-menu>
        </el-aside>
        <el-container>
          <el-main v-loading="isLoading">
            <el-row :gutter="20">
              <el-col :gutter="15" :span="6" v-for="(o, index) in 20" :key="o" >
                <el-card shadow="hover" body-style="padding: 0px" style="height: 350px; margin-bottom: 20px">
                  <el-image style="width: 100%; height: 220px" v-bind:src="products[index%3].productInfo.imgsrc" fit="cover"/>  
                  <div style="margin-left: 10px; margin-right: 10px">
                    <div align="left">
                      <div style="display:inline; color: red; font-size: 14px">¥</div>
                      <div style="display:inline; color: red; font-size: 22px">{{products[index%3].productInfo.price.toFixed(2)}}</div>
                    </div>
                    <div class="title" align="left" style="font-color: #F56C6C; font-size: 14px">{{products[index%3].productInfo.title}}</div>
                    <div style="margin: 5px"/>
                    <el-popover
                      placement="top-start"
                      width="500px"
                      trigger="hover">
                      <el-table height="200px" :data="products[index%3].comments.slice(0,Math.min(5,products[index%3].comments.length))">
                        <el-table-column width="100" property="buyerName" show-overflow-tooltip label="姓名"></el-table-column>
                        <el-table-column width="70" property="time" label="日期"></el-table-column>
                        <el-table-column width="330" property="content" show-overflow-tooltip="true" label="评论"></el-table-column>
                      </el-table>
                      <el-badge class="hidden-md-and-down" v-bind:value="products[index%3].comments.length" style="margin-right: 7px" slot="reference">
                        <el-button size="medium" icon="el-icon-chat-dot-square">评论</el-button>
                      </el-badge>
                    </el-popover>
                    <el-button size="medium" style="margin-left: 5px">加购<i class="el-icon-shopping-cart-1 el-icon--right"></i></el-button>
                  </div>
                </el-card>
              </el-col>
            </el-row>
          </el-main>
        </el-container>
      </el-container>
      <el-footer style="color: black; font-size: 12px">
        版权所有©北京大学数据库概论课程 | 地址：北京市海淀区颐和园路5号第二教学楼316 | 邮编：100871 | xdw@pku.edu.cn | 京ICP备05065075号-1 | 京公网安备 110402430047 号
      </el-footer>
    </el-container>
  </div>
</template>

<script>
import mock_products from './assets/mock_products.js'

export default {
  name: 'app',
  data(){
    return {
      isLogin: false,
      isLoading: false,
      products: mock_products.products,
      commentData: []
    }
  }, 
  created(){
  
    // this.$axios({
    //   method: 'GET',
    //   url: 'http://localhost/mock/login',
    //   }).then((res)=>{
    //   window.console.log(res)
    //   this.xml = res.data;
    // })

  },
  methods: {
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
.title{
      width: 100%;
      overflow: hidden;
      text-overflow: ellipsis; /* 文本溢出时显示省略号来代表被修剪的文本 */
      display: -webkit-box; /* 必须结合的属性 ，将对象作为弹性伸缩盒子模型显示 */
      -webkit-box-orient: vertical; /* 必须结合的属性 ，设置或检索伸缩盒对象的子元素的排列方式 */
      -webkit-line-clamp: 2; /* 文本需要显示多少行 */
}
</style>
