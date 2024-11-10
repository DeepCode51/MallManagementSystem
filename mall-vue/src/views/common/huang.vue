<template >
  <div>
    <el-container>
      <!-- 头部 -->
      <el-header style="height: 725px">
        <el-menu
          style="height: 725px"
          :default-active="activeIndex"
          class="el-menu-demo"
          mode="horizontal"
        >
          <el-menu-item index="1" style="margin-left: 100px">首页</el-menu-item>
          <el-submenu index="2">
            <template slot="title">我的软件</template>
            <el-menu-item index="2-1">已买软件</el-menu-item>
            <el-menu-item index="2-2">我的足迹</el-menu-item>
            <el-submenu index="2-3">
              <template slot="title">我的发布</template>
              <el-menu-item index="2-3-1">电商</el-menu-item>
              <el-menu-item index="2-3-2">教育</el-menu-item>
              <el-menu-item index="2-3-3">娱乐</el-menu-item>
            </el-submenu>
          </el-submenu>
          <el-submenu index="4">
            <template slot="title">我的收藏</template>
            <el-menu-item index="4-1">收藏软件</el-menu-item>
            <el-menu-item index="4-2">收藏店家</el-menu-item>
          </el-submenu>
          <el-submenu index="5">
            <template slot="title">购物车</template>
            <el-menu-item index="5-1">电商</el-menu-item>
            <el-menu-item index="5-2">教育</el-menu-item>
          </el-submenu>
          <el-submenu index="6">
            <template slot="title">我的订单</template>
            <el-menu-item index="6-1">电商</el-menu-item>
            <el-menu-item index="6-2">教育</el-menu-item>
          </el-submenu>
          <el-row :gutter="20" class="roww">
            <el-col :span="4">
              <el-input style="padding-left: 470px" placeholder="请输入内容">
                <el-button slot="append" icon="el-icon-search"></el-button>
              </el-input>
            </el-col>
            <el-col :span="4">
              <el-button class="login-a" type="text">登录</el-button>
            </el-col>
          </el-row>
        </el-menu>
        <div class="text-p">
          <p>-----------------</p>
          <div>
            =========================================、
            <br />==============================================
            <br />=============================
          </div>
        </div>
        <div class="dong"></div>
      </el-header>

      <el-divider></el-divider>
      <!-- 主体 -->
      <el-main style="margin-top:300px">
        <!-- <el-carousel :interval="3000" type="card" height="400px">
          <el-carousel-item v-for="item in imgList" :key="item">
            <img :src="item.img" class="medium" style="height:100%;width:100%;">
          </el-carousel-item>
        </el-carousel> -->
      </el-main>

      <!-- 尾部 -->
      <el-footer>Footer</el-footer>
    </el-container>
  </div>
</template>

<script>
export default {
  data() {
    return{
        imgList: [
        ]
    }
  }
}
</script>



<style lang="less" scoped>

.el-header {
  padding: 0;
}
.el-container {
  width: 100%;
}
.el-menu-demo {
  background-repeat: no-repeat;
  //background-attachment: fixed;固定
  background-size: 100% 100%;
  background-image: url(../../assets/back.jpg);
}
.roww {
  margin-top: 10px;
  padding-left: 100px;
}
.login-a {
  color: #909399;
  padding-left: 470px;
}
.text-p {
  position: absolute;
  top: 150px;
  left: 100px;
  > p{
    font-size: 50px;
    color: azure;
  }
  > div{
    font-size: 20px;
    color: #909399;
  }
}



.el-carousel__item img {
    color: #475669;
    font-size: 14px;
    opacity: 0.75;
    line-height: 200px;
    margin: 0;
  }
  
  .el-carousel__item:nth-child(2n) {
    background-color: #d3dce6;
  } 
  .el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
  }
  
</style>