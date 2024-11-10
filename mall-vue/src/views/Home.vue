<template>
    <div>
        <!-- 头部导航栏 -->
        <div>
            <p class="title">商城</p>
            <el-menu class="el-menu-demo" mode="horizontal" background-color="transparent" text-color="#FFF" active-text-color router>
                <!-- transparent:透明色 -->
                <el-menu-item index="/" style="margin-left: 400px">首页</el-menu-item>
<!--                <el-menu-item index="/journalism">所有商品</el-menu-item>-->

                <el-submenu index="2">
                    <template slot="title">我的商品</template>
                    <el-menu-item index="/mysoftware">已购商品</el-menu-item>
                    <el-menu-item index="/mycollection">收藏商品</el-menu-item>
                    <!-- <el-menu-item v-if="hasAuth('1')" index="/mysoftware">我发售的商品</el-menu-item> -->
                </el-submenu>

                <el-submenu index="3">
                    <template slot="title">我的订单</template>
                    <!-- <el-menu-item index="/myorder">待处理订单</el-menu-item> -->
                    <el-menu-item index="/myorder">订单列表</el-menu-item>
                </el-submenu>

                <el-menu-item index="/shoppingcart">购物车</el-menu-item>

                <el-submenu index="5">
                    <template slot="title">我的收藏</template>
                    <el-menu-item index="/mycollection">收藏商品</el-menu-item>
                    
                </el-submenu>
                <el-menu-item index="/myAddress">我的地址</el-menu-item>
                <el-menu-item @click="dialogVisible = true; getList()">在线客服</el-menu-item>

            </el-menu>

            <el-button class="loginbtn" @click="login()">{{ userInfo.name }}</el-button>
            <!-- admin用户才能登录 -->
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

        <!-- 分割线 -->
        <el-divider class="fen"></el-divider>
        <!-- 首页主体 -->
        <div class="main">
            <!-- 网站宗旨 -->
            <div class="text-p">
                <p class="xin">=================</p>
                <div class="animated swing">
                    ===========================================
                    <br />
                    ===========================================
                    <br />
                    =============================
                    <br />
                    ================
                </div>
            </div>

            <div class="xiaol"></div>

            <!-- 搜索框 -->
            <div class="soubtn">
                <el-input placeholder="请输入你要搜索的内容" v-model="sousuo" class="input-with-select">
                    <el-button slot="append" icon="el-icon-search" @click="select()"></el-button>
                </el-input>
            </div>

            <!-- 搜索结果 -->
            <el-drawer title="你可能想找的商品" :visible.sync="drawer" direction="rtl" size="50%">
                <el-row :gutter="20">
                    <el-col :span="8" style="padding: 0" v-for="(selectFor, index) in selectForm" :key="index">
                        <div class="box">
                            <img class="tu" :src="selectFor.image" alt />
                            <a href @click.prevent="toDetail(selectFor.id)" class="describe">
                                {{ selectFor.operdescribe }}
                            </a>
                            <div class="price" type="primary " size="small">￥{{ selectFor.price }}</div>
                            <div class="shop" type="danger" size="small">{{ selectFor.shop }}</div>
                        </div>
                    </el-col>
                </el-row>
            </el-drawer>

            <h2 class="xingwen">推荐商品</h2>
            <!-- 走马灯 -->
            <div class="zouma">
                <el-carousel :interval="3000" type="card" height="500px">
                    <el-carousel-item v-for="(img, index) in imgList" :key="index">
                        <a href="/journalism"><img :src="img.url" height="100%" width="100%" /></a>
                    </el-carousel-item>
                </el-carousel>
            </div>

            <!-- 公司的描述 -->
            <div class="miao" ref="qwe">
                <h2>我们的公司概况</h2>
                <p>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    公司成立于2001年10月，立志为客户提供“省钱、好用、可靠”的解决方案。
                    </p>
            </div>

            <!-- 分割线 -->
            <el-divider class="fen1"></el-divider>

            <div class="miao1" ref="qwe1">
                <h2>我们的公司发展</h2>
                <p>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    &nbsp;&nbsp;&nbsp;&nbsp; 推广秘诀很简单
                    推广其实没什么诀窍，简单地说就是找人多的地方，然后给出跟别人不一样的东西，让大家都来围观。等围观的群体达到一定数量后，再把自己需要做的宣传内容丢进去。”
                    续签一年的皇冠会员，是必须的。
                     </p>
            </div>

            <!-- 分割线 -->
            <el-divider class="fen2"></el-divider>

            <div class="miao2" ref="qwe2">
                <h2>我们的公司文化</h2>
                <p>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    文化关乎维护小企业的利益。我们的生态体系的所有参与者，包括消费者、商家、第三方服务供应商和其他人士，都享有成长或获益的机会。
                    我们的业务成功和快速增长有赖于我们尊崇企业家精神和创新精神，并且始终如一地关注和满足客户的需求。
                    <br />
                    &nbsp;&nbsp;&nbsp;&nbsp; 我们相信，无论公司成长到哪个阶段，强大的共同价值观都可以让我们维持一贯的企业文化以及我们的公司的凝聚力。
                    <br />
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    六个价值观对于我们如何经营业务、招揽人才、考核员工以及决定员工报酬扮演着重要的角色，该六个价值观为：
                    客户第一，员工第二，股东第三。 这就是我们的选择，是我们的优先级。只有持续为客户创造价值，员工才能成长，股东才能获得长远利益。
                    <br />
                    &nbsp;&nbsp;&nbsp;&nbsp; 因为信任，所以简单
                    世界上最宝贵的是信任，最脆弱的也是信任。成长的历史是建立信任、珍惜信任的历史。你复杂，世界便复杂；你简单，世界也简单。人真实不装，互相信任，没那么多顾虑猜忌，问题就简单了，事情也因此高效。
                    <br />
                    &nbsp;&nbsp;&nbsp;&nbsp; 唯一不变的是变化
                    无论你变不变化，世界在变，客户在变，竞争环境在变。我们要心怀敬畏和谦卑，避免“看不见、看不起、看不懂、追不上”。改变自己，创造变化，都是最好的变化。拥抱变化是我们最独特的DNA。
                    <br />
                    &nbsp;&nbsp;&nbsp;&nbsp; 今天最好的表现是明天最低的要求
                    在最困难的时候，正是这样的精神，帮助我们渡过难关，活了下来。逆境时，我们懂得自我激励；顺境时，我们敢于设定具有超越性的目标。面向未来，不进则退，我们仍要敢想敢拼，自我挑战，自我超越。
                    <br />
                    &nbsp;&nbsp;&nbsp;&nbsp; 认真生活， 快乐工作
                    工作只是一阵子，生活才是一辈子。工作属于你，而你属于生活，属于家人。像享受生活一样快乐工作，像对待工作一样认真地生活。只有认真对待生活，生活才会公平地对待你。我们每个人都有自己的工作和生活态度，我们尊重每个解忧人的选择。这条价值观的考核，留给生活本身。
                </p>
            </div>

            <!-- 分割线 -->
            <el-divider class="fen3"></el-divider>

            <div class="miao3" ref="qwe3">
                <h2>我们的售后</h2>
                <p>
                    顾客后还有顾客，服务的开始才是销售的开始。 客户满意，人脉延伸，良性循环，回报一生客户服务，重在回访。仔细倾听，你认心情。
                    提高售后服务质量，提升客户满意程度 回馈客户，从我做起，心中有情，客户有心

                </p>
            </div>

            <!-- 分割线 -->
            <el-divider class="fen4"></el-divider>

            <!-- 商品展示 -->
            <div class="goods">
                <p class="rem">热门商品</p>

                <el-row :gutter="20">
                    <el-col :span="4" style="padding: 0" v-for="(good, index) in goods" :key="index">
                        <div class="box">
                            <img class="tu" :src="good.image" alt />
                            <a href @click.prevent="toDetail(good.id)" class="describe">{{ good.operdescribe }}</a>
                            <div class="price">￥{{ good.price }}</div>
                            <div class="shop">{{ good.shop }}</div>
                        </div>
                    </el-col>
                </el-row>
                <el-button class="genduobtn"  @click="tosoftware()">
                    更多
                </el-button>
            </div>
        </div>

        <!-- animated bounceInRight:anime.css动画 -->
        <!-- 尾部友情链接 -->
        <div class="footer">
            <div class="alt">
                <div class="foot">
                    <p>关于我们</p>
                    <a href="###">企业简介</a>
                    <a href="###">企业文化</a>
                    <a href="###">组织机构</a>
                    <a href="###">发展历程</a>
                    <a href="###">企业荣誉</a>
                    <a href="###">诚聘英才</a>
                    <a href="###">联系我们</a>
                </div>
                <div class="foot">
                    <p>新闻中心</p>
                    <a href="###">公司新闻</a>
                    <a href="###">行业新闻</a>
                </div>
                <div class="foot">
                    <p>产品及解决方案</p>
                    <a href="###">产品系列</a>
                    <a href="###">解决方案</a>
                </div>
                <div class="foot">
                    <p>研发与创新</p>
                    <a href="###">核心技术</a>
                    <a href="###">技术创新</a>
                </div>
                <div class="foot">
                    <p>经典案例</p>
                    <a href="###">智慧城市</a>
                    <a href="###">智慧高速</a>
                    <a href="###">智慧家居</a>
                    <a href="###">智慧网络</a>
                </div>
                <div class="foot">
                    <p>服务支持</p>
                    <a href="###">技术支持</a>
                    <a href="###">服务网络</a>
                    <a href="###">资料下载</a>
                    <a href="###">公司理念</a>
                </div>
                <div class="foot">
                    <p>关注我们</p>
                    <img src="https://img1.baidu.com/it/u=318258528,1264863502&fm=26&fmt=auto" />
                </div>
            </div>
            <div style="border-top:1px #1F1F1F solid;">
                <p class="alt" style="color:#999; line-height:0;">
                    ==============
                    <span>==============</span>
                    <a href="###" target="_blank" style="color:#999;">============</a>
                    <span style="float:right;">============</span>
                </p>
            </div>
        </div>
    </div>
</template>

<script>
import anime from 'animejs';
export default {
    name: 'Home',
    data() {
        return {
          dialogVisible: false,
          ask: '',
          dataList: [],
            drawer: false,
            userInfo: {},
            imgList: [
                { url: require('@/assets/back.jpg') },
                { url: require('@/assets/backiee-86446.jpg') },
                { url: require('@/assets/backiee-88224.jpg') },
                { url: require('@/assets/backiee-91323.jpg') },
                { url: require('@/assets/backiee-119162.jpg') },
            ],
            sousuo: '',
            goods: [],
            selectForm: [],
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
        getConfigList() {
          // 获取轮播图 数据
          this.$axios.get('/config/list',  {
            page: 1,
            limit: 5
          }).then(res => {
            console.log(res)
            res = res.data
            if (res.data.length > 0) {
              let imgList = [];
              res.data.forEach(element => {
                if (element.value != null) {
                  imgList.push({
                    url: element.value
                  });
                }
              });

              this.imgList = imgList;



            }
          })
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
        select() {
            this.$axios.get('/commodity/select/' + this.sousuo).then(res => {
                this.selectForm = res.data.data;
                this.drawer = true;
            });
        },
        toDetail(id) {
            this.$message({
                showClose: true,
                message: '已进入详情页面',
                type: 'success',
            });
            this.$router.push({
                name: 'detail',
                params: {
                    commodityId: id,
                },
            });
        },
        tosoftware() {
            this.$message({
                showClose: true,
                message: '成功进入更多商品页面',
                type: 'success',
            });
            this.$router.push('/software');
        },
        // 首页笑脸动画
        listAnimate() {
            anime({
                targets: '.xiaol',
                translateX: [
                    { value: 250, duration: 1000, delay: 500 },
                    { value: 0, duration: 1000, delay: 500 },
                ],
                translateY: [
                    { value: -100, duration: 500 },
                    { value: 100, duration: 500, delay: 1000 },
                    { value: 0, duration: 500, delay: 1000 },
                ],
                scaleX: [
                    { value: 4, duration: 100, delay: 500, easing: 'easeOutExpo' },
                    { value: 1, duration: 900 },
                    { value: 4, duration: 100, delay: 500, easing: 'easeOutExpo' },
                    { value: 1, duration: 900 },
                ],
                scaleY: [
                    { value: [1.75, 1], duration: 500 },
                    { value: 2, duration: 50, delay: 1000, easing: 'easeOutExpo' },
                    { value: 1, duration: 450 },
                    { value: 1.75, duration: 50, delay: 1000, easing: 'easeOutExpo' },
                    { value: 1, duration: 450 },
                ],
                easing: 'easeOutElastic(1, .8)',
                loop: true,
            });
        },
        getGoodsInfo() {
            this.$axios.get('/commodity/homeList').then(res => {
                this.goods = res.data.data;
            });
        },
        getUserInfo() {
            this.$axios.get('/user/info').then(res => {
                this.userInfo = res.data.data;
            });
        },

        //滚轮入场动画
        handleScroll() {
            //盒子的总高度
            var heg = this.$refs.qwe.getBoundingClientRect().top;
            //浏览器窗口的高度
            var getWindowHeight = document.documentElement.clientHeight * 0.8;

            if (heg < getWindowHeight) {
                this.$refs.qwe.classList.add('show');
            } else {
                this.$refs.qwe.classList.remove('show');
            }

            //盒子1的总高度
            var heg1 = this.$refs.qwe1.getBoundingClientRect().top;

            if (heg1 < getWindowHeight) {
                this.$refs.qwe1.classList.add('show');
            } else {
                this.$refs.qwe1.classList.remove('show');
            }

            //盒子2的总高度
            var heg2 = this.$refs.qwe2.getBoundingClientRect().top;

            if (heg2 < getWindowHeight) {
                this.$refs.qwe2.classList.add('show');
            } else {
                this.$refs.qwe2.classList.remove('show');
            }

            //盒子3的总高度
            var heg3 = this.$refs.qwe3.getBoundingClientRect().top;

            if (heg3 < getWindowHeight) {
                this.$refs.qwe3.classList.add('show');
            } else {
                this.$refs.qwe3.classList.remove('show');
            }
        },
    },
    //创建页面之前执行的方法
    created() {
      this.getConfigList()
      this.$nextTick(() => {
            this.listAnimate();
        });
        this.getGoodsInfo();
        this.getUserInfo();
    },
    //在模板渲染成html后调用，通常是初始化页面完成后，再对html的dom节点进行一些需要的操作。
    mounted() {
        //监听scroll事件
        window.addEventListener('scroll', this.handleScroll);
    },
    destroyed() {
        //页面离开后销毁，防止切换路由后上一个页面监听scroll滚动事件会在新页面报错问题
        window.removeEventListener('scroll', this.handleScroll);
    },
};
</script>

<style lang="less" scoped>
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
.title {
    display: inline;
    vertical-align: middle;
    font-family: '楷体';
    font-size: 20px;
    color: white;
    position: absolute;
    left: 50px;
}
.avatar {
    position: absolute;
    top: 10px;
    left: 1300px;
}
.a {
    text-decoration: none;
}
.backtu {
    z-index: -1;
    height: 800px;
    width: 100vw;
    position: absolute;
    top: 0;
    left: 0;
    background: url(../assets/backiee-130807.jpg) no-repeat;
    background-size: cover;
    border-bottom-left-radius: 30px;
    border-bottom-right-radius: 30px;
}

.xiaol {
    width: 50px;
    height: 50px;
    border-radius: 50%;
    background: url(../assets/xiaol.jpeg) no-repeat;
    background-size: cover;
    position: absolute;
    top: 400px;
    left: 1100px;
}

.zouma {
    width: 100vw;
    height: 800px;
    position: absolute;
    top: 1020px;
}
.xingwen {
    font-family: '楷体';
    size: 30px;
    color: rgb(68, 148, 44);
    position: absolute;
    top: 900px;
    left: 46%;
}

.fen {
    width: 1300px;
    position: absolute;
    top: 880px;
    left: 100px;
}
.fen1 {
    width: 1300px;
    position: absolute;
    top: 1903px;
    left: 100px;
}
.fen2 {
    width: 1300px;
    position: absolute;
    top: 2332px;
    left: 100px;
}
.fen3 {
    width: 1300px;
    position: absolute;
    top: 2842px;
    left: 100px;
}
.fen4 {
    width: 1300px;
    position: absolute;
    top: 3280px;
    left: 100px;
}
.loginbtn {
    position: absolute;
    top: 10px;
    left: 86vw;
    background: transparent;
}
.registerbtn {
    color: aliceblue;
    position: absolute;
    top: 10px;
    left: 92vw;
}
.main {
    height: 400px;
    width: 100%;
}
.text-p {
    font-family: '楷体';
    position: absolute;
    top: 150px;
    left: 100px;
    > p {
        font-family: '楷体';
        font-size: 50px;
        color: azure;
    }
    > div {
        font-family: '楷体';
        font-size: 20px;
        color: #909399;
    }
}
.soubtn {
    height: 60px;
    width: 500px;
    position: absolute;
    top: 450px;
    left: 100px;
}
.goods {
    height: 1600px;
    width: 100%;
    border: 1px #e3e3e3 solid;
    border-radius: 20px;
    overflow: hidden;
    position: absolute;
    top: 3400px;
    left: 0;
}
.rem {
    font-family: '楷体';
    font-size: 30px;
    color: #cc1515;
    text-align: center;
}
.el-col {
    height: 320px;
    border-radius: 5px;
    margin: 15px 10px;
    background-color: white;
    position: relative;
    top: 20px;
    left: 100px;
    overflow: hidden; //不让溢出
    transition: all 0.6s;
}
.el-col:hover {
    box-shadow: 0px 15px 30px rgba(0, 0, 0, 0.4);
}
.box {
    width: 100%;
    height: 100%;
}
.tu {
    border-radius: 5px;
    width: 100%;
    height: 200px;
    margin-bottom: 10px;
    transition: all 0.6s; //变化速度
}
.tu:hover {
    transform: scale(1.1); //变化大小
}

.describe {
    font-size: 6px;
    margin: 10px;
    color: black;
    text-decoration: none; //去a标签下划线
}
.describe:hover {
    color: #409f60;
}
.price {
    display: inline;
    color: #cc1515;
    position: absolute;
    bottom: 4px;
    left: 4px;
}
.shop {
    display: inline;
    position: absolute;
    bottom: 4px;
    right: 4px;
}

.miao {
    width: 100vw;
    height: 300px;
    border-radius: 35px;
    background-image: linear-gradient(to right, #8360c3, #2ebf91);
    transform: translateX(400%);
    transition: transform 0.8s ease-in-out;
    > h2 {
        text-align: center;
        color: aquamarine;
        font-family: '楷体';
    }
    > p {
        padding: 10px 30px;
        text-align: left;
        font-family: '楷体';
        font-size: 20px;
        color: #fff;
    }
    position: absolute;
    top: 1600px;
    left: 0;
}
.miao.show {
    transform: translateX(0);
}

.miao1 {
    width: 100vw;
    height: 350px;
    border-radius: 35px;
    background-image: linear-gradient(to right, #0f0c29, #302b63, #24243e);
    transform: translateX(-400%);
    transition: transform 0.8s ease-in-out;
    > h2 {
        text-align: center;
        color: aquamarine;
        font-family: '楷体';
    }
    > p {
        padding: 10px 30px;
        text-align: left;
        font-family: '楷体';
        font-size: 20px;
        color: #fff;
    }
    position: absolute;
    top: 1960px;
    left: 0;
}
.miao1.show {
    transform: translateX(0);
}

.miao2 {
    width: 100vw;
    height: 420px;
    border-radius: 35px;
    background-image: linear-gradient(to right, #16222a, #3a6073);
    transform: translateX(400%);
    transition: transform 0.8s ease-in-out;
    > h2 {
        text-align: center;
        color: aquamarine;
        font-family: '楷体';
    }
    > p {
        padding: 10px 30px;
        text-align: left;
        font-family: '楷体';
        font-size: 20px;
        color: #fff;
    }
    position: absolute;
    top: 2400px;
    left: 0;
}
.miao2.show {
    transform: translateX(0);
}

.miao3 {
    width: 100vw;
    height: 300px;
    border-radius: 35px;
    background-image: linear-gradient(to right, #8360c3, #2ebf91);
    transform: translateX(400%);
    transition: transform 0.8s ease-in-out;
    > h2 {
        text-align: center;
        color: aquamarine;
        font-family: '楷体';
    }
    > p {
        padding: 10px 30px;
        text-align: left;
        font-family: '楷体';
        font-size: 20px;
        color: #fff;
    }
    position: absolute;
    top: 2920px;
    left: 0;
}
.miao3.show {
    transform: translateX(0);
}

.genduobtn {
    margin-top: 40px;
    width: 150px;
    position: relative;
    left: 50%;
    transform: translate(-50%);
}

a {
    text-decoration: none;
}
img {
    border: none;
}

.footer {
    background-color: #2b2e33;
    height: 360px;
    width: 100vw;
    position: absolute;
    top: 5100px;
    left: 0;
    > * {
        font-size: 14px;
    }
}

.footer .alt {
    padding: 20px 0;
    width: 1160px;
    margin: auto;
    overflow: hidden;
}

.footer .alt .foot {
    width: 138px;
    float: left;
}

.footer .alt .foot p {
    color: #fff;
    line-height: 28px;
}

.footer .alt .foot a {
    color: #999;
    display: block;
    line-height: 24px;
}

.footer .alt .foot a:hover {
    color: #ccc;
}

.footer .alt .foot img {
    width: 200px;
    height: 200px;
    padding-top: 10px;
}
</style>
