<template>
  <div id="app">
    <el-container style="overflow: scroll;">
      <el-menu mode="horizontal" default-active="1">
          <el-submenu index="0">
            <template slot="title" style="font-size: 40px;"><i class="el-icon-user-solid"></i>{{isLogin?"欢迎回来，" + userData.username:"登录/注册"}}</template>
            <el-menu-item index="0-1" @click="loginDialogVisible = true" v-if="!isLogin">登录</el-menu-item>
            <el-menu-item index="0-2" @click="registerDialogVisible = true" v-if="!isLogin">注册</el-menu-item>
            <el-menu-item index="0-3" @click="loadUserData();changeUserDataDialogVisible = true" v-if="isLogin">修改信息</el-menu-item>
            <el-menu-item index="0-4" @click="logout()" v-if="isLogin">登出</el-menu-item>
          </el-submenu>
          <el-menu-item index="1">
            <template slot="title"><i class="el-icon-s-home"></i>首页</template>
          </el-menu-item>
          <el-menu-item index="2" @click="cartVisible = true; loadCart();" :disabled="!isLogin"> 
            <template slot="title"><i class="el-icon-s-goods"></i>购物车</template>
          </el-menu-item>
          <el-menu-item index="3" @click="orderVisible = true; loadOrder();" :disabled="!isLogin"> 
            <template slot="title"><i class="el-icon-s-order"></i>订单</template>
          </el-menu-item>
          <el-menu-item index="4" @click="ownVisible = true; loadOwn();" :disabled="!isLogin||!isSeller"> 
            <template slot="title"><i class="el-icon-s-management"></i>自家宝贝</template>
          </el-menu-item>
          <el-menu-item index="5">
            <template slot="title"><i class="el-icon-s-data"></i>分析</template>
          </el-menu-item>
          <el-menu-item index="6" v-if="isLogin && userData.account_status == 2" @click="allUserVisible = true; loadAllUserData();">
            <template slot="title"><i class="el-icon-s-check"></i>用户管理</template>
          </el-menu-item>
          <!-- <el-menu-item index="7" v-if="isLogin && userData.account_status == 2" @click="allOrderVisible = true; loadAllOrderData();">
            <template slot="title"><i class="el-icon-s-check"></i>订单管理</template>
          </el-menu-item> -->
          <el-menu-item index="8" v-if="isLogin && userData.account_status == 2" @click="allProductVisible = true; loadAllProductData(1);">
            <template slot="title"><i class="el-icon-s-check"></i>商品管理</template>
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
                    <img style="width: 30px; height: 16px; object-fit: cover; margin-left:5px" v-if="product.productInfo.hot>2" src="./assets/hot.png"/>  
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
                      <el-table-column width="330" property="content" :show-overflow-tooltip="true" label="评价"></el-table-column>
                    </el-table>
                    <el-badge class="hidden-md-and-down" v-bind:value="product.comments.length" style="margin-right: 7px" slot="reference">
                      <el-button size="medium" @click="handleProduct(product)" icon="el-icon-chat-dot-square">评价</el-button>
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

    <el-dialog
      title="欢迎使用北大闲鱼"
      :visible.sync="loginDialogVisible"
      width="500px"
      center>
      <el-form :rules="loginRules" ref="loginForm" :model="loginForm" label-width="100px">
        <el-form-item label="用户名" prop="username">
          <el-input placeholder="请输入用户名" v-model = "loginForm.username" size="small"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input placeholder="请输入密码" v-model = "loginForm.password" size="small" show-password></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="loginDialogVisible = false;registerDialogVisible = true">注 册</el-button>
        <el-button type="primary" @click="submitLoginForm(loginForm)">登 录</el-button>
      </span>
    </el-dialog>
    
    <el-dialog
        title="注册北大闲鱼"
        :visible.sync="registerDialogVisible"
        width="500px"
        center>
      <el-form :rules="registerRules" ref="registerForm" :model="registerForm" label-width="100px">
        <el-form-item label="用户名" prop="username">
          <el-input placeholder="起个什么名字好呢" v-model = "registerForm.username" size="small"></el-input>
        </el-form-item>
          <el-form-item label="密码" prop="password">
          <el-input placeholder="密码要好好想想" v-model = "registerForm.password" size="small" show-password></el-input>
        </el-form-item>
          <el-form-item label="出生日期" prop="birth">
          <el-date-picker type="date" placeholder="日期是多少呢" v-model= "registerForm.birth" style="width: 100%;"></el-date-picker>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-radio-group v-model= "registerForm.sex">
            <el-radio label="女"></el-radio>
            <el-radio label="男"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input placeholder="电子邮箱会梦见真实邮箱吗" v-model = "registerForm.email" size="small"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input placeholder="怎么样才能联系到你呢" v-model = "registerForm.phone" size="small"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitRegisterForm(registerForm, 'register')">注 册</el-button>
      </span>
    </el-dialog>

    <!--修改个人信息-->
    <el-dialog
        title="修改个人信息"
        :visible.sync="changeUserDataDialogVisible"
        width="500px"
        center>
      <el-form  :rules="changeUserDataRules" ref="changeUserDataForm" :model="changeUserDataForm" label-width="100px">
          <el-form-item label="密码" prop="password">
          <el-input placeholder="密码要好好想想" v-model = "changeUserDataForm.password" size="small" show-password></el-input>
        </el-form-item>
          <el-form-item label="出生日期" prop="birth">
          <el-date-picker type="date" placeholder="日期是多少呢" v-model= "changeUserDataForm.birth" style="width: 100%;"></el-date-picker>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-radio-group v-model= "changeUserDataForm.sex">
            <el-radio label="女"></el-radio>
            <el-radio label="男"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input placeholder="电子邮箱会梦见真实邮箱吗" v-model = "changeUserDataForm.email" size="small"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input placeholder="怎么样才能联系到你呢" v-model = "changeUserDataForm.phone" size="small"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitRegisterForm(changeUserDataForm, 'modify')">修 改</el-button>
      </span>
    </el-dialog>

    <!--详情-->
    <el-dialog title="商品详情" :visible.sync="selectProductVisible">
      <detail 
        v-bind:product="selectProduct"
        v-on:addCart="addCart"
        v-on:comment="comment"
        v-on:purchase="purchase"
      />
    </el-dialog>
    
    <!--卖家修改/发布商品-->
    <el-dialog title="编辑商品" :visible.sync="editVisible">
      <modifyProduct
        v-bind:product="selectProduct"
        v-on:modify="updateProduct"
      />
    </el-dialog>

    <!--卖家修改/发布商品-->
    <el-dialog title="编辑商品" :visible.sync="newProductVisible">
      <newProduct
        v-on:modify="updateProduct"
      />
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
              @click="purchase(scope.row)">下单</el-button>
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
              @click="handleProduct(scope.row)">评价</el-button>
            <el-button
              size="mini"
              @click="returnProduct(scope.row)">退货</el-button>
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
              @click="handleAction(scope.row)">{{scope.row.productInfo.actionText}}</el-button>
            <el-button
              size="mini"
              @click="selectProduct=scope.row; editVisible=true">编辑</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-row>
        <el-col :offset="9">
          <el-button style="margin:15px" align="right" type="primary" icon="el-icon-plus" circle @click="newProductVisible=true;"></el-button>
        </el-col>
      </el-row>
    </el-drawer>

    <!--管理员商品管理-->
    <el-drawer title="商品管理" :visible.sync="allProductVisible" size="1000px">
      <el-table
        :data="allProductData"
        style="width: 100%">
        <el-table-column
          label="卖家昵称"
          prop="productInfo.seller_name"
          width="100px"/>
        <el-table-column
          label="名称"
          prop="productInfo.title"
          width="300px"/>
        <el-table-column
          label="类目"
          width="100px">
          <template slot-scope="scope">
              {{categoryLabel[scope.row.productInfo.category]}}
          </template>
        </el-table-column>
        <el-table-column
          label="价格"
          prop="productInfo.price"
          width="100px"/>
        <el-table-column
          label="交易状态"
          width="100px">
          <template slot-scope="scope">
              {{getTransactionStatus(scope.row)}}
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
              size="mini"
              @click="adminAuditProduct(scope.row)"
              :disabled="scope.row.productInfo.product_status!=1">强制下架</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
            background
            layout="prev, pager, next"
            @current-change="handleAdminPage"
            :total="100"
            style="margin-top:10px"/>

    </el-drawer>

    <!--用户管理-->
    <el-drawer title="所有用户" :visible.sync="allUserVisible" size="1100px">
      <el-table
        :data="allUserData"
        style="width: 100%">
        <el-table-column
          label="账号"
          prop="id"
          width="70px">
        </el-table-column>
        <el-table-column
          label="用户名"
          prop="username"
          width="100px">
        </el-table-column>
        <el-table-column
          label="出生日期"
          prop="birth"
          width="100px">
        </el-table-column>
        <el-table-column
          label="性别"
          prop="sex"
          width="50px">
        </el-table-column>
        <el-table-column
          label="邮箱"
          prop="email"
          width="150px">
        </el-table-column>
        <el-table-column
          label="手机号"
          prop="phone"
          width="120px">
        </el-table-column>
        <el-table-column
          label="用户状态"
          width="100px">
          <template slot-scope="scope">
              {{getUserStatus(scope.row)}}
          </template>
        </el-table-column>
        <el-table-column
          label="用户状态最后变化时间"
          prop="update_time"
          width="200px">
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
              size="mini"
              @click="adminAuditUser(scope.row)"
              :disabled="scope.row.account_status!=4"
              >通过审核
            </el-button>
            <el-button
              size="mini"
              @click="adminDeleteUser(scope.row)">删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-drawer>

  </div>
</template>

<script>
// import mock_products from './assets/mock_products.js'
// import mock_user from './assets/mock_login.js'
import detail from './components/detail.vue'
import modifyProduct from './components/modifyProduct.vue'
import newProduct from './components/newProduct.vue'
import category from './category.js'


export default {
  name: 'app',
  components: {
    detail,
    modifyProduct,
    newProduct
  },
  data(){
    return {
      domain: "http://localhost:8080",
      isLoading: false,
      isLogin: false, // 登出置false
      isSeller: false, // 登出置false
      isCartLoad: false, // 登出置false
      isOwnLoad: false, // 登出置false
      isOrderLoad: false, // 登出置false
      products: [],
      selectProduct: null, // 商品详情/评价展示
      cartData: [],
      orderData: [],
      ownData: [],
      allUserData: [],//所有用户信息
      allOrderData: [],
      allProductData: [],
      userData: null,
      loginDialogVisible: false,
      registerDialogVisible: false,
      changeUserDataDialogVisible: false,
      offlineDialogVisible: false,
      cartVisible: false,
      orderVisible: false,
      ownVisible: false,
      allUserVisible: false,
      allOrderVisible: false,
      allProductVisible: false,
      selectProductVisible: false,
      editVisible: false,
      newProductVisible: false,
      keyword:"",
      categorySelect: 0,
      loginForm:{
        username:'',
        password:'',
      },
      loginRules:{
        username:[{required:true,message:'用户名不能为空', trigger:'blur'}],
        password:[{required:true,message:'密码不能为空', trigger:'blur'}]
      },
      registerForm:{
        username:'',
        password:'',
        birth:"",
        sex:"",
        email:"",
        phone:"",
      },
      changeUserDataForm:{
        password:'',
        birth:'',
        sex:"",
        email:'',
        phone:'',
      },
      registerRules:{
        username:[{required:true,message:'用户名不能为空', trigger:'blur'}],
        password:[{required:true,message:'密码不能为空', trigger:'blur'}],
        birth:[{type:'date',required: true, message:'请选择出生日期', trigger:'change'}],
        sex:[{required:true,message:'请选择性别', trigger:'change'}],
        email:[{required:true,message:'电子邮件不能为空', trigger:'blur'}],
        phone:[{required:true,message:'电话号码不能为空', trigger:'blur'}],
      },
      changeUserDataRules:{
        username:[{required:true,message:'用户名不能为空', trigger:'blur'}],
        password:[{required:true,message:'密码不能为空', trigger:'blur'}],
        birth:[{type:'date',required: true, message:'请选择出生日期', trigger:'change'}],
        sex:[{required:true,message:'请选择性别', trigger:'change'}],
        email:[{required:true,message:'电子邮件不能为空', trigger:'blur'}],
        phone:[{required:true,message:'电话号码不能为空', trigger:'blur'}],
      },
      categoryLabel:category.categoryLabel
    };
  }, 
  created(){
    this.loadProduct(0, "");
  },
  methods: {
    formUrl(action, params=null) {
      let url = this.domain + "/" + action;
      if (params != null && Object.keys(params).length > 0) {
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
    loadProduct(category, keyword, page=1) {
      this.isLoading = true;
      let params = {
        "category": category,
        "page": page
      };
      if (keyword.length >0) {
        params["keyword"] = keyword;
      } else {
        params["keyword"] = "";
      }
      let url = this.formUrl("product", params);
      this.$axios({
        method: 'GET',
        url: url,
      }).then((res)=>{
        this.products = res.data.products;
        this.isLoading = false;
      });
    },
    loadAllProductData(page=1) {//管理员查看所有订单
      let url = this.formUrl("admin/product", {
        page: page
      });
      this.$axios({
        method: 'GET',
        url: url,
      }).then((res)=>{
        this.allProductData = res.data.products;
      });
    },
    loadCart(res=null) {
      if (this.isCartLoad && res==null){
        return;
      } else if (this.isCartLoad && res!= null) {
        res();
        return;
      }
      let url = this.formUrl("cart", {
        "username": this.userData.username
      });
      this.$axios({
        method: 'GET',
        url: url,
      }).then((resp)=>{
        this.cartData = resp.data.products;
        this.isCartLoad = true;
        if (res != null) {
          res();
        }
      });
    },
    loadOrder(res=null) {
      if (this.isOrderLoad && res==null){
        return;
      } else if (this.isOrderLoad && res!= null) {
        res();
        return;
      }
      let url = this.formUrl("bought", {
        "username": this.userData.username
      });
      this.$axios({
        method: 'GET',
        url: url,
      }).then((resp)=>{
        this.orderData = resp.data.products;
        this.isOrderLoad = true;
        if (res != null) {
          res();
        }
      });
    },
    loadOwn(res=null) {
      if (this.isOwnLoad && res==null){
        return;
      } else if (this.isOwnLoad && res!= null) {
        res();
        return;
      }
      let url = this.formUrl("myproduct", { // TODO
        "username": this.userData.username
      });
      this.$axios({
        method: 'GET',
        url: url,
      }).then((resp)=>{
        this.ownData = resp.data.products;
        for (let p of this.ownData){
          this.updateOwnInfo(p);
        }
        this.isOwnLoad = true;
        if (res != null) {
          res();
        }
      });
    },
    updateOwnInfo(product){
      product.productInfo.statusText = this.getOwnStatus(product);
      product.productInfo.actionText = product.productInfo.product_status > 0 && product.productInfo.product_status < 3 ? "下架" : "上架";
      product.productInfo.actionDisable = product.productInfo.product_status == 4;
    },
    // 动作类函数
    addCart(product){
      if(!this.isLogin){
        this.$notify.error({
          title: '失败',
          message: "请先登录！"
        });
        return;
      }
      this.loadCart(() => {
        let url = this.formUrl("addcart", {
          "username": this.userData.username,
          "product_id": product.product_id
        });
        this.$axios({
          method: 'POST',
          url: url,
        }).then((res)=>{
          if (res.data.success == true) {
            this.cartData.push(product);
            this.$notify.info({
              title: '成功',
              message: product.productInfo.title + " 已成功放入购物车，快去看看吧～"
            });
          } else {
            this.$notify.error({
              title: '失败',
              message: product.productInfo.title + " 没能成功放入购物车，这是为什么呢？"
            });
          }
        });
      });
    },
    deleteCart(product) {
      let url = this.formUrl("removecart", {
        "username": this.userData.username,
        "product_id": product.product_id
      });
      this.$axios({
        method: 'POST',
        url: url,
      }).then((res)=>{
        if (res.data.success == true) {
          for(let i = 0; i <  this.cartData.length; i++) {
            if (this.cartData[i].product_id == product.product_id) {
              this.cartData.splice(i, 1);
              continue;
            }
          }
          this.$notify.info({
            title: '成功',
            message: product.productInfo.title + " 已成功从购物车删除"
          });
        } else {
          this.$notify.error({
            title: '失败',
            message: product.productInfo.title + " 没能成功从购物车删除，这是为什么呢？"
          });
        }
      });
    },
    purchase(product){
      if(!this.isLogin){
        this.$notify.error({
          title: '失败',
          message: "请先登录！"
        });
        return;
      }
      this.loadOrder(() => {
        let url = this.formUrl("buy", {
          "username": this.userData.username,
          "product_id": product.product_id
        });
        this.$axios({
          method: 'POST',
          url: url,
        }).then((res)=>{
          if (res.data.success == true) {
            product.productInfo.update_time = (new Date()).toISOString();
            this.orderData.push(product);
            this.$notify.success({
              title: '成功',
              message: product.productInfo.title + " 已成功下单！"
            });
          } else {
            this.$notify.error({
              title: '失败',
              message: product.productInfo.title + " 没能成功下单，这是为什么呢？"
            });
          }
        });
      });
    },
    returnProduct(product){
      this.loadOrder(() => {
        let url = this.formUrl("removecart", {
          "username": this.userData.username,
          "product_id": product.product_id
        });
        this.$axios({
          method: 'POST',
          url: url,
        }).then((res)=>{
          if (res.data.success == true) {
            for(let i = 0; i <  this.orderData.length; i++) {
              if (this.orderData[i].product_id == product.product_id) {
                this.orderData.splice(i, 1);
                break;
              }
            }
            this.$notify.success({
              title: '成功',
              message: product.productInfo.title + " 已成功退货！"
            });
          } else {
            this.$notify.error({
              title: '失败',
              message: product.productInfo.title + " 没能成功退货，这是为什么呢？"
            });
          }
        });
      });
    },
    comment(product, message) {
      if(!this.isLogin){
        this.$notify.error({
          title: '失败',
          message: "请先登录！"
        });
        return;
      }
      let url = this.formUrl("comment");
      this.$axios({
        method: 'POST',
        url: url,
        data: {
          "username": this.userData.username,
          "product_id": product.product_id.toString(),
          "time": this.getDateForComment(),
          "content": message
        }
      }).then((res)=>{
        if (res.data.success == true) {
          this.selectProduct.comments.push({
            "product_id": this.selectProduct.product_id,
            "time": this.getDateForComment(),
            "buyer_name": this.userData.username,
            "content": message
          });
          this.$notify.info({
            title: '成功',
            message: "评价提交成功！"
          });
        } else {
          this.$notify.error({
            title: '失败',
            message: "评价提交失败"
          });
        }
      });
    },
    updateProduct(product){
        let url = this.formUrl("manage");
        this.$axios({
          method: 'POST',
          url: url,
          data: {
            "product_id": product.product_id.toString(),
            "username": this.userData.username,
            "title": product.productInfo.title,
            "category": product.productInfo.category,
            "price": product.productInfo.price,
            "description": product.productInfo.description,
            "imgsrc": product.productInfo.imgsrc
          }
        }).then((res)=>{
          if (res.data.success == true) {
            product.productInfo.seller_name = this.userData.username;
            product.productInfo.update_time = this.getDateForOwn();
            product.productInfo.product_status = 0; // 未上架状态
            this.updateOwnInfo(product);
            if(product.product_id==0){
              this.ownData.push(product);
            } else {
              for(let i = 0; i <  this.ownData.length; i++) {
                if (this.ownData[i].product_id == product.product_id) {
                  this.ownData[i].productInfo.title = product.productInfo.title;
                  this.ownData[i].productInfo.imgsrc = product.productInfo.imgsrc;
                  this.ownData[i].productInfo.description= product.productInfo.description;
                  this.ownData[i].productInfo.product_status= product.productInfo.product_status;
                  this.ownData[i].productInfo.price= product.productInfo.price;
                  this.ownData[i].productInfo.category= product.productInfo.category;
                  this.updateOwnInfo(this.ownData[i]);
                  break;
                }
              }
            }
            this.$notify.info({
              title: '成功',
              message: "编辑成功！"
            });
          } else {
            this.$notify.error({
              title: '失败',
              message: "编辑失败"
            });
          }
        });
    },
    invisible(product){
      let url = this.formUrl("invisible", {
        "product_id": product.product_id
      });
      this.$axios({
        method: 'POST',
        url: url,
      }).then((res)=>{
        if (res.data.success == true) {
          product.productInfo.product_status = 0;
          this.updateOwnInfo(product);
          this.$notify.success({
            title: '成功',
            message: product.productInfo.title + " 已成功下架！"
          });
        } else {
          this.$notify.error({
            title: '失败',
            message: product.productInfo.title + " 没能成功下架，这是为什么呢？"
          });
        }
      });
    },
    visible(product){
      let url = this.formUrl("visible", {
        "product_id": product.product_id
      });
      this.$axios({
        method: 'POST',
        url: url,
      }).then((res)=>{
        if (res.data.success == true) {
          product.productInfo.product_status = 1;
          this.updateOwnInfo(product);
          this.$notify.success({
            title: '成功',
            message: product.productInfo.title + " 已成功上架！"
          });
        } else {
          this.$notify.error({
            title: '失败',
            message: product.productInfo.title + " 没能成功上架，这是为什么呢？"
          });
        }
      });
    },
    adminDeleteUser(user){
      let url = this.formUrl("admin/offline/user", {
          "username": user.username,
        });
        this.$axios({
          method: 'POST',
          url: url,
        }).then((res)=>{
          if (res.data.success == true) {
            for(let i = 0; i <  this.allUserData.length; i++) {
              if (this.allUserData[i].id == user.id) {
                this.orderData.splice(i, 1);
                break;
              }
            }
            this.$notify.success({
              title: '成功',
              message: user.username + " 已成功删除"
            });
          } else {
            this.$notify.error({
              title: '失败',
              message: user.username + " 没能成功删除，这是为什么呢？"
            });
          }
        });
    },
    adminAuditUser(user){
      let url = this.formUrl("admin/seller", {
         "username": user.username
      });
      this.$axios({
        method: 'POST',
        url: url,
      }).then((res)=>{
        if (res.data.success == true) {
          user.account_status = 1;
          this.$notify.success({
            title: '成功',
            message: user.username + " 已成为卖家！"
          });
        } else {
          this.$notify.error({
            title: '失败',
            message: user.username + " 没能成为卖家，这是为什么呢？"
          });
        }
      });
    },
    adminAuditProduct(product){
      let url = this.formUrl("admin/offline/product", {
         "product_id": product.product_id
      });
      this.$axios({
        method: 'POST',
        url: url,
      }).then((res)=>{
        if (res.data.success == true) {
          product.product_status = 4;
          this.$notify.success({
            title: '成功',
            message: product.productInfo.title + " 已强制下架！"
          });
        } else {
          this.$notify.error({
            title: '失败',
            message: product.productInfo.title + " 没能成功下架，这是为什么呢？"
          });
        }
      });
    },
    loadUserData()//加载当前用户信息，用于修改信息
    {
      this.changeUserDataForm.sex = this.userData.sex;  
      this.changeUserDataForm.birth=new Date(this.userData.birth);
      this.changeUserDataForm.phone=this.userData.phone;
      this.changeUserDataForm.email=this.userData.email;
      window.console.log(this.changeUserDataForm);
    },
    // 加载所有用户数据
    loadAllUserData() {
      let url = this.formUrl("admin/user", {});
      this.$axios({
        method: 'GET',
        url: url,
      }).then((res)=>{
          this.allUserData = res.data.users;
          window.console.log("allUserData");
          window.console.log(this.allUserData);
      });
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
        return "强制下架";
      }
    },
    getTransactionStatus(product){
      let status = product.productInfo.product_status;
      if (status == 0) {
        return "未上架";
      } else if (status == 1) {
        return "上架待售";
      } else if (status == 2) {
        return "已成交";
      } else if (status == 3) {
        return "已退货";
      } else if (status == 4) {
        return "已强制下架";
      }
    },
    getUserStatus(user){
      let status = user.account_status;
      if (status == 0) {
        return "买家";
      } else if (status == 1) {
        return "卖家";
      } else if (status == 2) {
        return "管理员";
      } else if (status == 3) {
        return "已删除";
      } else if (status == 4) {
        return "申请卖家";
      }
    },
    getDateForOwn() {
      let d = new Date();
      return [
        d.getFullYear(),
        ('0' + (d.getMonth() + 1)).slice(-2),
        ('0' + d.getDate()).slice(-2)
      ].join('-');
    },
    getDateForComment() {
      let d = new Date();
      return [
        d.getFullYear()%100,
        ('0' + (d.getMonth() + 1)).slice(-2),
        ('0' + d.getDate()).slice(-2)
      ].join('.');
    },
    // 注册登录函数
    submitLoginForm(form) {
      let url = this.formUrl("login", {
        "username": form.username,
        "password": form.password
      });
      this.$axios({
        method: 'POST',
        url: url,
      }).then((res)=>{
        if (res.data.success == true) {
          this.userData = res.data.user;
          window.console.log(this.userData);
          this.isLogin = true;
          this.isSeller = this.userData.account_status == 1;
          this.$notify.info({
            title: '',
            message: '登录成功!'
          });
          this.loginDialogVisible = false;
        } else {
          this.$notify.error({
            title: '',
            message: '用户名或密码错误'
          });
        }
      });
      return form;
    },
    submitRegisterForm(form, type) {
      let url = this.formUrl("register", {
          "type" : type,
          "username" : this.userData.username,
          "password": form.password,
          "birth": form.birth.toISOString().substring(0, 10),
          "sex": form.sex,
          "email": form.email,
          "phone": form.phone,
      });
      this.$axios({
        method: 'POST',
        url: url,
      }).then((res)=>{
        if (res.data.success == true) {
          this.$notify.info({
            title: '',
            message: type == "reigster" ? '注册成功!' : '修改成功!'
          });
          this.registerDialogVisible = false;
        } else {
          this.$notify.error({
            title: '',
            message: type == "reigster" ? '注册失败，用户名已存在' : '修改失败'
          });
        }
      });
    },
    logout(){
      this.isLogin = false;
      this.isSeller = false;
      this.isCartLoad = false;
      this.isOwnLoad = false;
      this.isOrderLoad = false;
      this.userData = null;
      this.ownData = null;
      this.cartData = null;
      this.orderData = null;
    },
    handleSearch() {
      window.console.log("[handleSearch] " + this.keyword);
      this.loadProduct(this.categorySelect, this.keyword);
    },
    handlePage(page) {
      window.console.log("[handlePage] " + page);
      this.loadProduct(this.categorySelect, this.keyword, page);
    },
    handleAdminPage(page) {
      window.console.log("[handleAdminPage] " + page);
      this.loadAllProductData(page);
    },
    handleProduct(product) {
      this.selectProduct = product;
      this.selectProductVisible = true;
    },
    handleAction(product) {
      if (product.productInfo.product_status == 0 || product.productInfo.product_status == 3) {
        this.visible(product);
        this.updateOwnInfo(product);
      } else if (product.productInfo.product_status == 1) {
        this.invisible(product);
        this.updateOwnInfo(product);
      } else {
        window.console.log("[handleAction] wrong action!!");
      }
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