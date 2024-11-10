<template>
    <div>
        <!-- 头部导航栏 -->
        <div>
            <p class="title">商城</p>
            <el-menu class="el-menu-demo" mode="horizontal" background-color="hsla(0,0%,100%,.4)" text-color="#2c3e50" active-text-color router>
                <el-menu-item index="/" style="margin-left: 400px">首页</el-menu-item>
<!--                <el-menu-item index="/journalism">所有商品</el-menu-item>-->

                <el-submenu index="2">
                    <template slot="title">我的商品</template>
                    <el-menu-item index="/mysoftware">已购商品</el-menu-item>
                    <el-menu-item index="/mycollection">收藏商品</el-menu-item>
                    <!-- <el-menu-item index="/mysoftware" v-if="hasAuth('1')">我发售的软件</el-menu-item> -->
                </el-submenu>

                <el-submenu index="3">
                    <template slot="title">我的订单</template>
                    <!-- <el-menu-item index="/myorder">待处理订单</el-menu-item> -->
                    <el-menu-item index="/myorder">订单列表</el-menu-item>
                </el-submenu>

                <el-menu-item index="/shoppingcart">购物车</el-menu-item>

                <el-submenu index="5">
                    <template slot="title">我的收藏</template>
                    <el-menu-item index="/mycollection">收藏软件</el-menu-item>
                    <!-- <el-menu-item index="/mycollection">收藏店家</el-menu-item> -->
                </el-submenu>
                <el-menu-item index="/myAddress">我的地址</el-menu-item>

              <el-menu-item @click="dialogVisible = true; getList()">在线客服</el-menu-item>

            </el-menu>

            <el-button class="loginbtn" @click="login()">{{ userInfo.name }}</el-button>
            <el-button class="registerbtn" v-if="hasAuth('ROLE_admin')" type="text" @click="reg()">后台入口</el-button>
            <el-button class="registerbtn" v-else-if="hasAuth('0')" type="text" @click="logout()">退出</el-button>
            <el-button class="registerbtn" v-else-if="hasAuth('1')" type="text" @click="logout()">退出</el-button>
        </div>
      <el-dialog
          title="在线客服"
          :visible.sync="dialogVisible"
          width="40%">
        <el-form style="height: 300px" ref="ruleForm" label-width="0">
            <div v-bind:key="item.id" v-for="item in dataList">
              <el-row>
              <div v-if="item.ask" class="left-content">
                <el-alert class="text-content" :title="item.ask" :closable="false" type="success"></el-alert>
              </div>
              <div v-else class="right-content">
                <el-alert class="text-content" :title="item.reply" :closable="false" type="warning"></el-alert>
              </div>
              </el-row>
              <div class="clear-float"></div>
            </div>
        </el-form>
        <span slot="footer" class="dialog-footer">
                    <div class="btn-input">
            <el-input style="flex: 1;margin-right: 10px;" v-model="ask" placeholder="发布" clearable></el-input>
            <el-button type="primary" @click="onSubmit">发布</el-button>
          </div>
<!--    <el-button @click="dialogVisible = false">取 消</el-button>-->
<!--    <el-button type="primary" @click="dialogVisible = false">确 定</el-button>-->
  </span>
      </el-dialog>
        <div class="backtu"></div>
    </div>
</template>

<script>
export default {
    name: 'NavigationBar',
    data() {
        return {
          dialogVisible: false,
          ask: '',
          dataList: [],
            userInfo: {},
        };
    },
    methods: {
      getList() {
        this.$axios.get('/chat/list').then(res => {
          console.log(res)
          this.dataList = res.data.data;
        })
      },
// 提交
      onSubmit() {
        if (!this.ask) {
          this.$message.error('请输入内容', {
            time: 2000,
            icon: 5
          });
          return
        }
        this.$axios.post('/chat/add', {
          userid: localStorage.getItem('userid'),
          ask: this.ask
        }).then(res => {
          this.getList();
        });
        this.ask="";
      },
        login() {
            this.$message({
                showClose: true,
                message: '已进入个人中心',
                type: 'success',
            });
            this.$router.push('/personalcenter');
        },
        reg() {
            this.$message({
                showClose: true,
                message: '已进入后台系统',
                type: 'success',
            });
            this.$router.push('/bcakstage');
        },
        logout() {
            this.$axios.post('/logout').then(res => {
                localStorage.clear();
                sessionStorage.clear();
                this.$store.commit('resetState');
                this.$message({
                    showClose: true,
                    message: '退出成功',
                    type: 'success',
                });
                this.$router.push('/login');
            });
        },
        getUserInfo() {
            this.$axios.get('/user/info').then(res => {
                this.userInfo = res.data.data;
            });
        },
    },
    created() {
        this.getUserInfo();
    },
};
</script>

<style lang="less" scoped>
.title {
    z-index: 3;
    display: inline;
    vertical-align: middle;
    font-family: '楷体';
    font-size: 20px;
    color: #2c3e50;
    position: absolute;
    left: 50px;
}
.avatar {
    position: absolute;
    top: 10px;
    left: 1300px;
}
.backtu {
    z-index: -1;
    height: 180px;
    width: 100vw;
    position: absolute;
    top: 0;
    left: 0;
    background: url(../../assets/daohang.png) no-repeat;
    background-size: cover;
}
a {
    text-decoration: none;
}
.loginbtn {
    color: #2c3e50;
    position: absolute;
    top: 10px;
    left: 86vw;
    background: transparent;
}
.registerbtn {
    color: #2c3e50;
    position: absolute;
    top: 10px;
    left: 92vw;
}
.chat-form {
  height: 100%;
  display: flex;
  flex-direction: column;
}
.chat-content {
  overflow-y: scroll;
  border: 1px solid #eeeeee;
  margin: 0;
  padding: 0;
  width: 100%;
  flex: 1;
}
.left-content {
  float: left;
  margin-bottom: 10px;
  padding: 10px;
}
.right-content {
  float: right;
  margin-bottom: 10px;
  padding: 10px;
}
.btn-input {
  margin-left: 0px;
  display: flex;
  width: 100%;
  padding: 10px 12px;
  box-sizing: border-box;
}
</style>
