<template>
  <div id="app">
    <el-container>
      <el-header style="font-size: 40px; color: #820010">北大有鱼，其名为闲。</el-header>
      <el-container>
        <el-aside width="100px">
          <el-menu>
            <el-menu-item :index="'login'" @click="loginDialogVisible = true">
              <template slot="title"><i class="el-icon-location-outline"></i>登录</template>
            </el-menu-item>
            <el-menu-item :index="'index'">
              <template slot="title"><i class="el-icon-goods"></i>首页</template>
            </el-menu-item>
            <el-menu-item :index="'cart'" @click="cartVisible = true"> 
              <template slot="title"><i class="el-icon-goods"></i>购物车</template>
            </el-menu-item>
            <el-menu-item :index="'analysis'">
              <template slot="title"><i class="el-icon-info"></i>分析</template>
            </el-menu-item>
          </el-menu>
        </el-aside>
        <el-container>
          <el-main v-loading="isLoading">
            <el-row :gutter="20">
              <el-col :gutter="15" :span="6" v-for="(product, index) in products" :key='index'>
                <el-card shadow="hover" body-style="padding: 0px" style="height: 350px; margin-bottom: 20px">
                  <el-image style="width: 100%; height: 220px" v-bind:src="product.productInfo.imgsrc" fit="cover"/>  
                  <div style="margin-left: 10px; margin-right: 10px">
                    <div align="left">
                      <div style="display:inline; color: red; font-size: 14px">¥</div>
                      <div style="display:inline; color: red; font-size: 22px">{{product.productInfo.price.toFixed(2)}}</div>
                    </div>
                    <div class="title" align="left" style="font-color: #F56C6C; font-size: 14px">{{product.productInfo.title}}</div>
                    <div style="margin: 5px"/>
                    <el-popover
                      placement="top-start"
                      width="500px"
                      trigger="hover">
                      <el-table height="200px" :data="product.comments.slice(0,Math.min(5,product.comments.length))">
                        <el-table-column width="100" property="buyerName" show-overflow-tooltip label="姓名"></el-table-column>
                        <el-table-column width="70" property="time" label="日期"></el-table-column>
                        <el-table-column width="330" property="content" :show-overflow-tooltip="true" label="评论"></el-table-column>
                      </el-table>
                      <el-badge class="hidden-md-and-down" v-bind:value="product.comments.length" style="margin-right: 7px" slot="reference">
                        <el-button size="medium" icon="el-icon-chat-dot-square">评论</el-button>
                      </el-badge>
                    </el-popover>
                    <el-button size="medium" style="margin-left: 5px" @click="addcart(product.productInfo.title)">加购<i class="el-icon-shopping-cart-1 el-icon--right"></i></el-button>
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
    <!--在这里加入了注册登录的弹出窗口。暂时让所有登录都失败，因为不知道怎么存放登录状态-->
    <el-dialog
      title="欢迎使用北大闲鱼"
      :visible.sync="loginDialogVisible"
      width="500px"
      center>
    <el-form :rules="loginRules" ref="loginRuleForm" :model="loginRuleForm" label-width="100px">
      <el-form-item label="用户名" prop="name">
      <el-input placeholder="请输入用户名" v-model = "loginRuleForm.name" size="small"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
      <el-input placeholder="请输入密码" v-model = "loginRuleForm.password" size="small" show-password></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="loginDialogVisible = false;registerDialogVisible = true">注 册</el-button>
      <el-button type="primary" @click="submitLoginForm('loginRuleForm')">登 录</el-button>
    </span>
  </el-dialog>
  <el-dialog
      title="注册北大闲鱼"
      :visible.sync="registerDialogVisible"
      width="500px"
      center>
    <el-form :rules="registerRules" ref="registerRuleForm" :model="registerRuleForm" label-width="100px">
      <el-form-item label="用户名" prop="name">
        <el-input placeholder="起个什么名字好呢" v-model = "registerRuleForm.name" size="small"></el-input>
      </el-form-item>
        <el-form-item label="密码" prop="password">
        <el-input placeholder="密码要好好想想" v-model = "registerRuleForm.password" size="small" show-password></el-input>
      </el-form-item>
        <el-form-item label="出生日期" prop="birthday">
        <el-date-picker type="date" placeholder="日期是多少呢" v-model= "registerRuleForm.birthday" style="width: 100%;"></el-date-picker>
      </el-form-item>
      <el-form-item label="性别" prop="sex">
        <el-radio-group v-model= "registerRuleForm.sex">
          <el-radio label="女"></el-radio>
          <el-radio label="男"></el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input placeholder="电子邮箱会梦见真实邮箱吗" v-model = "registerRuleForm.email" size="small"></el-input>
      </el-form-item>
      <el-form-item label="电话" prop="phone">
        <el-input placeholder="怎么样才能联系到你呢" v-model = "registerRuleForm.phone" size="small"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitLoginForm('registerRuleForm')">注 册</el-button>
    </span>
  </el-dialog>
  <!--购物车-->
  <el-dialog
      title="购物车"
      :visible.sync="cartVisible"
      width="800px"
      center>
    <el-card shadow="hover" body-style="padding: 0px" style="height: 350px; margin-bottom: 20px">
      <el-row type="flex" justify="center" align="middle" gutter="20"> 
        <el-col :span="12">
          <el-image style="width: 100%; height: 220px" :src="testUrl" fit="cover"/> 
        </el-col>
        <el-col type="flex" justify="center" align="middle" :span="12">
                              <div class="title" align="left" style="font-color: #F56C6C; font-size: 14px">{{"product.productInfo.title"}}</div>

          <el-button type="danger">删 除</el-button>
          <el-button type="primary">购 买</el-button>
        </el-col>
      </el-row>
    </el-card>
  </el-dialog>
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
      products: Array(20).fill(null).map((_, h)=>mock_products.products[h%3]),
      commentData: [],
      loginDialogVisible: false,
      loginRuleForm:{
        name:'',
        password:'',
     },
      loginRules:
      {
        name:[{required:true,message:'用户名不能为空', trigger:'blur'}],
        password:[{required:true,message:'密码不能为空', trigger:'blur'}]
      },
      registerDialogVisible: false,
      registerRuleForm:{
        name:'',
        password:'',
        birthday:"",
        sex:"",
        email:"",
        phone:"",
     },
      registerRules:
      {
        name:[{required:true,message:'用户名不能为空', trigger:'blur'}],
        password:[{required:true,message:'密码不能为空', trigger:'blur'}],
        birthday:[{type:'date',required: true, message:'请选择出生日期', trigger:'change'}],
        sex:[{required:true,message:'请选择性别', trigger:'change'}],
        email:[{required:true,message:'电子邮件不能为空', trigger:'blur'}],
        phone:[{required:true,message:'电话号码不能为空', trigger:'blur'}],
      },
      cartVisible: false,
      testUrl: 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1576077318175&di=26d19907bdb7582e0c4f18279bb036aa&imgtype=0&src=http%3A%2F%2Fwww.nyasama.com%2Fbsup%2Fnyaup%2Fattachment%2Fforum%2F201307%2F13%2F133141nvycyyvv11v81vt0.jpg'
      //不知道怎么获取图片
    };
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
    addcart(title) {
      this.$notify({
        title: '成功',
        message: title + " 已成功放入购物车，快去看看吧～",
        type: 'success'
      });
    } ,
    //<!--注册登录函数-->
     submitLoginForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            //TODO:成功登录需要做什么？
            this.loginDialogVisible = false;
          } else {
            return false;
          }
        });
      },
      submitRegisterForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            //TODO:成功注册需要做什么？
            this.registerDialogVisible = false;
          } else {
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      }
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
