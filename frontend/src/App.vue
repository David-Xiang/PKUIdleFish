<template>
  <div id="app">
    <el-container>
      <el-menu mode="horizontal" default-active="1">
          <el-menu-item index="0" @click="loginDialogVisible = true">
            <template slot="title" style="font-size: 40px;"><i class="el-icon-user-solid"></i>登录/注册</template>
          </el-menu-item>
          <el-menu-item index="1">
            <template slot="title"><i class="el-icon-s-home"></i>首页</template>
          </el-menu-item>
          <el-menu-item index="2" @click="cartVisible = true; loadCart();" :disabled="!isLogin"> 
            <template slot="title"><i class="el-icon-s-goods"></i>购物车</template>
          </el-menu-item>
          <el-menu-item index="3" @click="orderVisible = true; loadRelated(0);" :disabled="!isLogin"> 
            <template slot="title"><i class="el-icon-s-order"></i>订单</template>
          </el-menu-item>
          <el-menu-item index="4" @click="ownVisible = true; loadRelated(1);" :disabled="!isLogin||!isSeller"> 
            <template slot="title"><i class="el-icon-s-management"></i>自家宝贝</template>
          </el-menu-item>
          <el-menu-item index="5">
            <template slot="title"><i class="el-icon-s-data"></i>分析</template>
          </el-menu-item>
        </el-menu>
      <el-header style="font-size: 40px; color: #820010; margin-top:20px">北大有鱼，其名为闲。</el-header>

      <el-container>
        <el-main v-loading="isLoading">
          <el-container>
            <el-input placeholder="请输入关键字" v-model="keyword" clearable @keyup.enter.native="handleSearch" style="margin:20px">
              <el-select v-model="categorySelect" slot="prepend" placeholder="分类">
                <el-option label="家用电器" value="1"></el-option>
                <el-option label="衣装服饰" value="2"></el-option>
                <el-option label="图书文娱" value="3"></el-option>
              </el-select>
              <el-button slot="append" icon="el-icon-search" @click="handleSearch"></el-button>
            </el-input> 
          </el-container>
          <el-row :gutter="20">
            <el-col :gutter="15" :span="6" v-for="(product, index) in products" :key='index'>
              <el-card shadow="hover" body-style="padding: 0px" style="height: 350px; margin: 15px">
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
                  <el-button size="medium" style="margin-left: 5px" @click="addCart(product.productInfo.title)">加购<i class="el-icon-shopping-cart-1 el-icon--right"></i></el-button>
                </div>
              </el-card>
            </el-col>
          </el-row>
          <div/>
          <el-pagination
            background
            layout="prev, pager, next"
            @current-change="handlePage"
            :total="100">
          </el-pagination>
        </el-main>
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
    <el-drawer title="购物车" :visible.sync="cartVisible" size="600px">
      <el-table
        :data="cartData"
        style="width: 100%">
        <el-table-column
          label="图片"
          prop="productInfo.title"
          width="80px">
          <template slot-scope="scope">
            <el-image
              style="width: 70px; height: 70px"
              :src="scope.row.productInfo.imgsrc"
              fit="cover"/>
          </template>
        </el-table-column>
        <el-table-column
          label="名称"
          prop="productInfo.title"
          width="300px"/>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
              size="mini"
              @click="$alert(scope.$index)">下单</el-button>
            <el-button
              size="mini"
              type="danger"
              @click="$alert(scope.$index)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-drawer>


    <!--订单管理-->
    <el-drawer title="订单" :visible.sync="orderVisible" size="800px">
      <el-table
        :data="orderData"
        style="width: 100%">
        <el-table-column
          label="图片"
          prop="productInfo.title"
          width="80px">
          <template slot-scope="scope">
            <el-image
              style="width: 70px; height: 70px"
              :src="scope.row.productInfo.imgsrc"
              fit="cover"/>
          </template>
        </el-table-column>
        <el-table-column
          label="名称"
          prop="productInfo.title"
          width="300px"/>
        <el-table-column
          label="下单日期"
          prop="productInfo.sellTime"
          width="200px"/>  
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
              size="mini"
              @click="$alert(scope.$index)">评价</el-button>
            <el-button
              size="mini"
              type="danger"
              @click="$alert(scope.$index)">退货</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-drawer>

    <!--宝贝管理-->
    <el-drawer title="自家宝贝" :visible.sync="ownVisible" size="600px">
      <el-table
        :data="cartData"
        style="width: 100%">
        <el-table-column
          label="图片"
          prop="productInfo.title"
          width="80px">
          <template slot-scope="scope">
            <el-image
              style="width: 70px; height: 70px"
              :src="scope.row.productInfo.imgsrc"
              fit="cover"/>
          </template>
        </el-table-column>
        <el-table-column
          label="名称"
          prop="productInfo.title"
          width="300px"/>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
              size="mini"
              @click="$alert(scope.$index)">下单</el-button>
            <el-button
              size="mini"
              type="danger"
              @click="$alert(scope.$index)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-drawer>
  </div>
</template>

<script>
export default {
  name: 'app',
  data(){
    return {
      domain: "http://localhost",
      isLogin: true, // TODO
      isLoading: false,
      isSeller: false,
      username: "dong", // TODO
      products: [],
      cartData: [],
      orderData: [],
      ownData: [],
      commentData: [],
      loginDialogVisible: false,
      registerDialogVisible: false,
      cartVisible: false,
      orderVisible: false,
      ownVisible: false,
      keyword:"",
      categorySelect: "",
      loginRuleForm:{
        name:'',
        password:'',
      },
      loginRules:{
        name:[{required:true,message:'用户名不能为空', trigger:'blur'}],
        password:[{required:true,message:'密码不能为空', trigger:'blur'}]
      },
      registerRuleForm:{
        name:'',
        password:'',
        birthday:"",
        sex:"",
        email:"",
        phone:"",
      },
      registerRules:{
        name:[{required:true,message:'用户名不能为空', trigger:'blur'}],
        password:[{required:true,message:'密码不能为空', trigger:'blur'}],
        birthday:[{type:'date',required: true, message:'请选择出生日期', trigger:'change'}],
        sex:[{required:true,message:'请选择性别', trigger:'change'}],
        email:[{required:true,message:'电子邮件不能为空', trigger:'blur'}],
        phone:[{required:true,message:'电话号码不能为空', trigger:'blur'}],
      },
      testUrl: 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1576077318175&di=26d19907bdb7582e0c4f18279bb036aa&imgtype=0&src=http%3A%2F%2Fwww.nyasama.com%2Fbsup%2Fnyaup%2Fattachment%2Fforum%2F201307%2F13%2F133141nvycyyvv11v81vt0.jpg'
      //不知道怎么获取图片
    };
  }, 
  created(){
    this.isLoading = true;
    this.loadProduct(0, "");
  },
  methods: {
    formUrl(action, params) {
      let url = this.domain + "/" + action;
      if (Object.keys(params).length > 0) {
        let i = 0;
        for (let key in params) {
          if (i == 0){
            url += "?";
          } else {
            url += "&";
          }
          i++;
          url += key + "=" + params[key];
        }
      }
      window.console.log("[formUrl] " + url);
      return url;
    },
    addCart(title) {
      // TODO
      this.$notify({
        title: '成功',
        message: title + " 已成功放入购物车，快去看看吧～",
        type: 'success'
      });
    },
    loadProduct(category, keyword, page=1) {
      let params = {
        "category": category,
        "page": page-1
      };
      if (keyword.length >0) {
        params["keyword"] = keyword;
      }
      let url = this.formUrl("product", params);
      this.$axios({
        method: 'GET',
        url: url,
      }).then((res)=>{
        this.products = Array(20).fill(null).map((_, h)=>res.data.products[h%3]);
        this.isLoading = false;
      });
    },
    loadCart() {
      let url = this.formUrl("cart", {
        "username": this.username
      });
      this.$axios({
        method: 'GET',
        url: url,
      }).then((res)=>{
        this.cartData = res.data.products;
      });
    },
    loadRelated(type) {
      let url = this.formUrl("related", {
        "username": this.username
      });
      this.$axios({
        method: 'GET',
        url: url,
      }).then((res)=>{
        if (type == 0){
          // 订单
          this.orderData = res.data[0].products;
        } else if (type == 1) {
          // 自家宝贝
          this.ownData = res.data[1].products;
        }
      });
    },
    // 注册登录函数
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
    },
    handleSearch() {
      window.console.log("[handleSearch] " + this.keyword);
      this.loadProduct(this.categorySelect, this.keyword);
    },
    handlePage(page) {
      window.console.log("[handlePage] " + page);
      this.loadProduct(this.categorySelect, this.keyword, page);
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
.el-select .el-input {
    width: 130px;
}
</style>
