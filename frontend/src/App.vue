<template>
  <div id="app">
    <el-container style="overflow: scroll;">
      <el-menu mode="horizontal" default-active="1">
          <el-submenu index="0">
            <template slot="title" style="font-size: 40px;"><i class="el-icon-user-solid"></i>{{isLogin?"欢迎回来，" + userData.name:"登录/注册"}}</template>
            <el-menu-item index="0-1" @click="loginDialogVisible = true" v-if="!isLogin">登陆</el-menu-item>
            <el-menu-item index="0-2" @click="registerDialogVisible = true" v-if="!isLogin">注册</el-menu-item>
            <el-menu-item index="0-3" @click="loadUserData();changeUserDataDialogVisible = true" v-if="isLogin">修改信息</el-menu-item>
            <el-menu-item index="0-4" @click="isLogin=false; userData=null;" v-if="isLogin">登出</el-menu-item>
          </el-submenu>
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
          <el-menu-item index="6" v-if="isLogin && userData.status == 2" @click="allUserVisible = true; loadAllUserData();">
            <template slot="title"><i class="el-icon-s-check"></i>管理用户</template>
          </el-menu-item>

        </el-menu>
      <el-header style="font-size: 40px; color: #820010; margin-top:20px">北大有鱼，其名为闲。</el-header>

      <el-container>
        <el-main v-loading="isLoading">
          <el-container>
            <el-input placeholder="请输入关键字" v-model="keyword" clearable @keyup.enter.native="handleSearch" style="margin-left:20px; margin-right:20px; margin-bottom:20px;">
              <el-select v-model="categorySelect" slot="prepend" placeholder="分类" >
                <el-option v-for="(label, index) in categoryLabel" :label="label" :key="index" :value="index"></el-option>
              </el-select>
              <el-button slot="append" icon="el-icon-search" @click="handleSearch"></el-button>
            </el-input> 
          </el-container>
          <el-row :gutter="20">
            <el-col :gutter="15" :span="6" v-for="(product, index) in products" :key='index'>
              <el-card shadow="hover" body-style="padding: 0px" style="height: 350px; margin: 15px">
                <img style="width: 100%; height: 220px; object-fit: cover;" v-bind:src="product.productInfo.imgsrc" @click="handleProduct(product)"/>  
                <div style="margin-left: 10px; margin-right: 10px">
                  <div align="left">
                    <div style="display:inline; color: red; font-size: 14px">¥</div>
                    <div style="display:inline; color: red; font-size: 22px">{{product.productInfo.price.toFixed(2)}}</div>
                  </div>
                  <div class="title" align="left" style="font-color: #F56C6C; font-size: 14px" @click="handleProduct(product)">{{product.productInfo.title}}</div>
                  <div style="margin: 5px"/>

                  <el-button size="medium" style="margin: 5px" @click="handleProduct(product)" icon="el-icon-search" class="hidden-lg-and-down">详情</el-button>
                  <el-button size="medium" style="margin: 5px" @click="addCart(product)" icon="el-icon-circle-plus-outline">购物车</el-button>
                  <el-popover
                    style="margin: 5px"
                    placement="top-start"
                    width="500px"
                    trigger="hover"
                    class="hidden-sm-and-down">
                    <el-table height="200px" :data="product.comments.slice(0,Math.min(5,product.comments.length))">
                      <el-table-column width="100" property="buyer_name" show-overflow-tooltip label="姓名"></el-table-column>
                      <el-table-column width="70" property="time" label="日期"></el-table-column>
                      <el-table-column width="330" property="content" :show-overflow-tooltip="true" label="评论"></el-table-column>
                    </el-table>
                    <el-badge class="hidden-md-and-down" v-bind:value="product.comments.length" style="margin-right: 7px" slot="reference">
                      <el-button size="medium" icon="el-icon-chat-dot-square">评论</el-button>
                    </el-badge>
                  </el-popover>
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
        <el-button type="primary" @click="submitRegisterForm('registerRuleForm')">注 册</el-button>
      </span>
    </el-dialog>

    <!--修改个人信息-->
    <el-dialog
        title="修改个人信息"
        :visible.sync="changeUserDataDialogVisible"
        width="500px"
        center>
      <el-form  :rules="changeUserDataRules" ref="changeUserDataRuleForm" :model="changeUserDataRuleForm" label-width="100px">
        <el-form-item label="用户名" prop="name">
          <el-input  placeholder="起个什么名字好呢" v-model = "changeUserDataRuleForm.name" size="small"></el-input>
        </el-form-item>
          <el-form-item label="密码" prop="password">
          <el-input placeholder="密码要好好想想" v-model = "changeUserDataRuleForm.password" size="small" show-password></el-input>
        </el-form-item>
          <el-form-item label="出生日期" prop="birthday">
          <el-date-picker type="date" placeholder="日期是多少呢" v-model= "changeUserDataRuleForm.birthday" style="width: 100%;"></el-date-picker>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-radio-group v-model= "changeUserDataRuleForm.sex">
            <el-radio label="女"></el-radio>
            <el-radio label="男"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input placeholder="电子邮箱会梦见真实邮箱吗" v-model = "changeUserDataRuleForm.email" size="small"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input placeholder="怎么样才能联系到你呢" v-model = "changeUserDataRuleForm.phone" size="small"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitRegisterForm('changeUserDataForm')">注 册</el-button>
      </span>
    </el-dialog>

    <!--详情-->
    <el-dialog title="商品详情" :visible.sync="selectProductVisible">
      <detail 
        v-bind:product="selectProduct"
        v-on:addCart="addCart"
      />
    </el-dialog>
    
    <!--卖家修改/发布商品-->
    <el-dialog title="编辑商品" :visible.sync="editVisible">
    </el-dialog>

    <!--购物车-->
    <el-drawer title="购物车" :visible.sync="cartVisible" size="600px">
      <el-table
        :data="cartData"
        style="width: 100%">
        <el-table-column
          label="图片"
          width="80px">
          <template slot-scope="scope">
              <el-card style="width: 70px; height: 70px;" body-style="padding: 0px">
                <img style="width: 70px; height: 70px; object-fit: cover;" v-bind:src="scope.row.productInfo.imgsrc" @click="handleProduct(product)"/> 
              </el-card>
          </template>
        </el-table-column>
        <el-table-column
          label="名称"
          width="300px">
          <template slot-scope="scope">
            <div @click="handleProduct(scope.row)">{{scope.row.productInfo.title}}</div>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
              size="mini"
              @click="$alert(scope.row)">下单</el-button>
            <el-button
              size="mini"
              type="danger"
              @click="deleteCart(scope.row)">删除</el-button>
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
          width="80px">
          <template slot-scope="scope">
              <el-card style="width: 70px; height: 70px;" body-style="padding: 0px">
                <img style="width: 70px; height: 70px; object-fit: cover;" v-bind:src="scope.row.productInfo.imgsrc" @click="handleProduct(product)"/> 
              </el-card>
          </template>
        </el-table-column>
        <el-table-column
          label="名称"
          width="300px">
          <template slot-scope="scope">
            <div @click="handleProduct(scope.row)">{{scope.row.productInfo.title}}</div>
          </template>
        </el-table-column>
        <el-table-column
          label="下单日期"
          prop="productInfo.update_time"
          width="200px"/>  
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
              size="mini"
              @click="$alert(scope.$index)">评价</el-button>
            <el-button
              size="mini"
              @click="$alert(scope.$index)">退货</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-drawer>

    <!--宝贝管理-->
    <el-drawer title="自家宝贝" :visible.sync="ownVisible" size="800px">
      <el-table
        :data="ownData"
        style="width: 100%">
        <el-table-column
          label="图片"
          width="80px">
          <template slot-scope="scope">
              <el-card style="width: 70px; height: 70px;" body-style="padding: 0px">
                <img style="width: 70px; height: 70px; object-fit: cover;" v-bind:src="scope.row.productInfo.imgsrc"/> 
              </el-card>
          </template>
        </el-table-column>
        <el-table-column
          label="名称"
          prop="productInfo.title"
          width="300px"/>
        <el-table-column
          label="金额"
          prop="productInfo.price"
          width="100px"/>
        <el-table-column
          label="状态"
          prop="productInfo.statusText"
          width="100px"/>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
              size="mini"
              prop="名字"
              :disabled="scope.row.productInfo.actionDisable"
              @click="$alert(scope.$index)">{{scope.row.productInfo.actionText}}</el-button>
            <el-button
              size="mini"
              @click="$alert(scope.$index)">编辑</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-row>
        <el-col :offset="9">
          <el-button style="margin:15px" align="right" type="primary" icon="el-icon-plus" circle @click="editVisible=true;"></el-button>
        </el-col>
      </el-row>
    </el-drawer>

    <!--用户管理-->
    <el-drawer title="所有用户" :visible.sync="allUserVisible" size="900px">
      <el-table
        :data="allUserData"
        style="width: 100%">
        <el-table-column
          label="用户名"
          prop="name"
          width="100px">
        </el-table-column>
        <el-table-column
          label="出生日期"
          prop="birthday"
          width="100px"/>  
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
              size="mini"
              @click="$alert(scope.$index)">评价</el-button>
            <el-button
              size="mini"
              @click="$alert(scope.$index)">退货</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-drawer>

  </div>
</template>

<script>
import mock_products from './assets/mock_products.js'
import mock_user from './assets/mock_login.js'
import detail from './components/detail.vue'
import mock_allUser from './assets/mock_allUser.js'

export default {
  name: 'app',
  components: {
    detail
  },
  data(){
    return {
      domain: "http://localhost",
      isLoading: false,
      isLogin: true, // 登出置false
      isSeller: true,
      isCartLoad: false, // 登出置false
      isOwnLoad: false, // 登出置false
      products: [],
      selectProduct: null, // 商品详情展示 
      cartData: [],
      orderData: [],
      ownData: [],
      commentData: [],
      allUserData: [],//所有用户信息
      userData: null,
      loginDialogVisible: false,
      registerDialogVisible: false,
      changeUserDataDialogVisible: false,
      offlineDialogVisible: false,
      cartVisible: false,
      orderVisible: false,
      ownVisible: false,
      allUserVisible: false,
      selectProductVisible: false,
      editVisible: false,
      keyword:"",
      categorySelect: 0,
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
      changeUserDataRuleForm:{
        name:'',
        password:'',
        birthday:'',
        sex:"",
        email:'',
        phone:'',
      },
      registerRules:{
        name:[{required:true,message:'用户名不能为空', trigger:'blur'}],
        password:[{required:true,message:'密码不能为空', trigger:'blur'}],
        birthday:[{type:'date',required: true, message:'请选择出生日期', trigger:'change'}],
        sex:[{required:true,message:'请选择性别', trigger:'change'}],
        email:[{required:true,message:'电子邮件不能为空', trigger:'blur'}],
        phone:[{required:true,message:'电话号码不能为空', trigger:'blur'}],
      },
      changeUserDataRules:{
        name:[{required:true,message:'用户名不能为空', trigger:'blur'}],
        password:[{required:true,message:'密码不能为空', trigger:'blur'}],
        birthday:[{type:'date',required: true, message:'请选择出生日期', trigger:'change'}],
        sex:[{required:true,message:'请选择性别', trigger:'change'}],
        email:[{required:true,message:'电子邮件不能为空', trigger:'blur'}],
        phone:[{required:true,message:'电话号码不能为空', trigger:'blur'}],
      },
      categoryLabel:[
        "全部分类", "穿戴服饰", "手机数码", "美容化妆"
      ]
    };
  }, 
  created(){
    this.loadProduct(0, "");
    this.userData = mock_user.user;
    this.isLogin = mock_user.success;
  },
  methods: {
    // formUrl(action, params) {
    //   let url = this.domain + "/" + action;
    //   if (Object.keys(params).length > 0) {
    //     let i = 0;
    //     for (let key in params) {
    //       if (i == 0){
    //         url += "?";
    //       } else {
    //         url += "&";
    //       }
    //       i++;
    //       url += key + "=" + params[key];
    //     }
    //   }
    //   window.console.log("[formUrl] " + url);
    //   return url;
    // },
    loadProduct(category, keyword, page=1) {
      this.isLoading = true;
      let params = {
        "category": category,
        "page": page-1
      };
      if (keyword.length >0) {
        params["keyword"] = keyword;
      }
      // let url = this.formUrl("product", params);
      // this.$axios({
      //   method: 'GET',
      //   url: url,
      // }).then((res)=>{
      //   this.products = Array(20).fill(null).map((_, h)=>res.data.products[h%3]);
      //   this.isLoading = false;
      // });
      this.products = Array(20).fill(null).map((_, h)=>mock_products.products[h%3]);
      this.isLoading = false;
    },
    loadProductAdmin(sold, page=1) {//管理员查看所有订单
      this.isLoading = true;
      let params = {
        "sold": sold,
        "page": page-1
      };
      if(sold > 5000)//写上这句话，不然报params未使用
      {
          params["0"] = sold;

      }
      // let url = this.formUrl("admin/product", params);
      // this.$axios({
      //   method: 'GET',
      //   url: url,
      // }).then((res)=>{
      //   this.products = Array(20).fill(null).map((_, h)=>res.data.products[h%3]);
      //   this.isLoading = false;
      // });
      this.products = Array(20).fill(null).map((_, h)=>mock_products.products[h%3]);
      this.isLoading = false;
    },
    loadCart(res=null) {
      if (this.isCartLoad){
        return;
      }
      // let url = this.formUrl("cart", {
      //   "username": this.username
      // });
      // this.$axios({
      //   method: 'GET',
      //   url: url,
      // }).then((resp)=>{
      //   this.cartData = resp.data.products;
      //   this.isCartLoad = true;
      //   if (res != null) {
      //     res();
      //   }
      // });
      this.cartData = mock_products.products;
      this.isCartLoad = true;
      if (res != null) {
        res();
      }
    },
    loadBought() {
      // let url = this.formUrl("bought", {
      //   "username": this.username
      // });
      // this.$axios({
      //   method: 'GET',
      //   url: url,
      // }).then((res)=>{
      //   this.orderData = res.data.products;
      // });
      this.orderData = mock_products.products;
    },
    loadOwn() {
      // TODO
    },
    loadRelated(type) {
      // let url = this.formUrl("related", {
      //   "username": this.username
      // });
      // this.$axios({
      //   method: 'GET',
      //   url: url,
      // }).then((res)=>{
      //   if (type == 0){
      //     // 订单
      //     this.orderData = res.data[0].products;
      //   } else if (type == 1) {
      //     // 自家宝贝
      //     this.ownData = res.data[1].products;
      //     window.console.log("ownData");
      //     window.console.log(this.ownData);
      //     for (let p of this.ownData) {
      //       p.productInfo.statusText = this.getOwnStatus(p);
      //       p.productInfo.actionText = p.productInfo.product_status > 0 && p.productInfo.product_status < 3 ? "下架" : "上架";
      //       p.productInfo.actionDisable = p.productInfo.product_status == 4;
      //     }
      //   }
      // });
      if (type == 0){
          // 订单
          this.orderData = mock_products.products;
        } else if (type == 1) {
          // 自家宝贝
          this.ownData = mock_products.products;
          window.console.log("ownData");
          window.console.log(this.ownData);
          for (let p of this.ownData) {
            p.productInfo.statusText = this.getOwnStatus(p);
            p.productInfo.actionText = p.productInfo.product_status > 0 && p.productInfo.product_status < 3 ? "下架" : "上架";
            p.productInfo.actionDisable = p.productInfo.product_status == 4;
          }
        }
    },
    // 动作类函数
    addCart(product){
      this.loadCart(() => {
        // let url = this.formUrl("addcart", {
        //   "username": this.username,
        //   "product_id": product.product_id
        // });
        // this.$axios({
        //   method: 'POST',
        //   url: url,
        // }).then((res)=>{
        //   if (res.data.success == true) {
        //     this.cartData.push(product);
        //     this.$notify.info({
        //       title: '成功',
        //       message: product.productInfo.title + " 已成功放入购物车，快去看看吧～"
        //     });
        //   } else {
        //     this.$notify.error({
        //       title: '失败',
        //       message: product.productInfo.title + " 没能成功放入购物车，这是为什么呢？"
        //     });
        //   }
        // });
        // TODO delete
        this.cartData.push(product);
        this.$notify.info({
          title: '成功',
          message: product.productInfo.title + " 已成功放入购物车，快去看看吧～"
        });
      });
    },
    deleteCart(product) {
      // let url = this.formUrl("deletecart", {
      //   "username": this.username,
      //   "product_id": product.product_id
      // });
      // this.$axios({
      //   method: 'POST',
      //   url: url,
      // }).then((res)=>{
      //   if (res.data.success == true) {
      //     for(let i = 0; i <  this.cartData.length; i++) {
      //       if (this.cartData[i].product_id == product.product_id) {
      //         this.cartData.splice(i, 1);
      //         continue;
      //       }
      //     }
      //     this.$notify.info({
      //       title: '成功',
      //       message: product.productInfo.title + " 已成功从购物车删除"
      //     });
      //   } else {
      //     this.$notify.error({
      //       title: '失败',
      //       message: product.productInfo.title + " 没能成功放入购物车，这是为什么呢？"
      //     });
      //   }
      // });
      // TODO delete
      for(let i = 0; i <  this.cartData.length; i++) {
        if (this.cartData[i].product_id == product.product_id) {
          this.cartData.splice(i, 1);
          break;
        }
      }
      this.$notify.info({
        title: '成功',
        message: product.productInfo.title + " 已成功从购物车删除"
      });
    },
    buy(product){
      this.$notify.error({
            title: '未完工',
            message: product.productInfo.title + " 没能成功放入购物车，这是为什么呢？"
          });
      // TODO XDW stops here
    },
    loadUserData()//加载当前用户信息，用于修改信息
    {
        this.changeUserDataRuleForm.name=this.userData.name;
        this.changeUserDataRuleForm.birthday=this.userData.birthday;
        this.changeUserDataRuleForm.phone=this.userData.phone;
        this.changeUserDataRuleForm.email=this.userData.email;
        window.console.log(this.changeUserDataRuleForm);


    },
    //加载所有用户数据
        loadAllUserData() {
      // let url = this.formUrl("admin/user", {
      // });
      // this.$axios({
      //   method: 'GET',
      //   url: url,
      // }).then((res)=>{
      //     this.allUserData = res.allUserData.user;
      //     window.console.log("allUserData");
      //     window.console.log(this.allUserData);
      //   }
      // });
          this.allUserData = mock_allUser.user;
          window.console.log("allUserData");
          window.console.log(this.allUserData);
          // for (let p of this.ownData) {
          //   p.productInfo.statusText = this.getOwnStatus(p);
          //   p.productInfo.actionText = p.productInfo.product_status > 0 && p.productInfo.product_status < 3 ? "下架" : "上架";
          //   p.productInfo.actionDisable = p.productInfo.product_status == 4;
          // }
    },
    getOwnStatus(product){
      let status = product.productInfo.product_status;
      if (status == 0) {
        return "下架";
      } else if (status == 1) {
        return "待售";
      } else if (status == 2) {
        return "已售";
      } else if (status == 3) {
        return "退货";
      } else if (status == 4) {
        return "删除";
      }
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
          if(!this.isLogin)
            this.registerDialogVisible = false;
            this.changeUserDataDialogVisible = false;
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
    },
    handleProduct(product) {
      this.selectProduct = product;
      this.selectProductVisible = true;
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