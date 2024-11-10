<template>
    <div class="zuida">
        <NavigationBar></NavigationBar>

        <!-- 主体 -->
        <div class="mian">
            <!-- 商品展示 -->
            <div>
                <!-- 轮播图盒子 -->
                <div class="lunbo">
                    <el-carousel :interval="1000" indicator-position="outside" :autoplay="false" height="500px">
                        <el-carousel-item v-for="(img, index) in imgList" :key="index">
                            <img style="border-radius: 5px" :src="img.url" height="100%" width="100%" />
                        </el-carousel-item>
                    </el-carousel>
                </div>

                <!-- 商品描述购买盒子 -->
                <div class="goumai">
                    <div class="wenzi">&nbsp;&nbsp;&nbsp;{{ good.operdescribe }}</div>
                    <div class="jiage">
                        价格:&nbsp;&nbsp;&nbsp;
                        <span style="color:red">￥{{ good.price }}</span>
                    </div>
                    <el-divider></el-divider>
                    <div class="block">
                        <span>店铺评价</span>
                        <el-rate v-model="value" :colors="colors" show-text></el-rate>
                    </div>
                    <el-divider></el-divider>
                    <el-button class="goubtn" @click="toPay(good.id)">立即购买</el-button>
                    <el-button class="goubtn" @click="addCart(good.id)">加入购物车</el-button>
                    <el-button class="goubtn" @click="addColl(good.id)">收藏</el-button>
                </div>
            </div>

            <el-divider class="fenxian"></el-divider>
            <!-- 商品评论 -->
            <div class="comment">
                <h2>商品评论</h2>
                <el-input
                    style="width: 950px; margin: 0px 20px;"
                    type="textarea"
                    autosize
                    placeholder="请发表一条友善的内容"
                    v-model="textarea"
                    maxlength="240"
                    show-word-limit
                ></el-input>
                <el-button type="success" plain round size="small" @click="toComment()">发表评论</el-button>
                <el-collapse v-for="(comment, index) in comments" :key="index" style="margin: 10px 20px;">
                    <el-collapse-item :title="comment.userName">
                        <div>{{ comment.content }}</div>
                        <div>评论时间: {{ comment.commentTime }}</div>
                    </el-collapse-item>
                </el-collapse>
            </div>

            <!-- 商品详情图 -->
            <!-- <div class="goodt">
                <h2>服务详情</h2>
                <div class="goodtu">
                    <img src="https://homesitetask.zbjimg.com/homesite/task/1.jpg/origine/a0a6785f-9d7d-46b4-be56-e3cb2466d31f" alt />
                </div>
                <div class="goodtu">
                    <img src="https://homesitetask.zbjimg.com/homesite/task/2.jpg/origine/a1425040-60e2-4d7c-982e-b211fe87563e" alt />
                </div>
                <div class="goodtu">
                    <img src="https://homesitetask.zbjimg.com/homesite/task/3.jpg/origine/ef04e515-aad4-4b3d-aa22-6ab0f0e0a0eb" alt />
                </div>
                <div class="goodtu">
                    <img src="https://homesitetask.zbjimg.com/homesite/task/4.jpg/origine/70d11fe1-f385-4928-950a-7687b8972ccf" alt />
                </div>
                <div class="goodtu">
                    <img src="https://homesitetask.zbjimg.com/homesite/task/5.jpg/origine/fe06cfd7-eb15-4db7-85dc-e0155280cd81" alt />
                </div>
                <div class="goodtu">
                    <img src="https://homesitetask.zbjimg.com/homesite/task/6.jpg/origine/6160197b-0306-403f-bb72-10effce8de8b" alt />
                </div>
                <div class="goodtu">
                    <img src="https://homesitetask.zbjimg.com/homesite/task/7.jpg/origine/107e1c13-643d-462f-bc25-9a80f1687f66" alt />
                </div>
                <div class="goodtu">
                    <img src="https://homesitetask.zbjimg.com/homesite/task/8.jpg/origine/ea4103c3-d62f-4b9e-93a0-7b62714f8fed" alt />
                </div>
                <div class="goodtu">
                    <img src="https://homesitetask.zbjimg.com/homesite/task/9.jpg/origine/34eece3b-50e7-4b03-b142-9adff8555a6d" alt />
                </div>
            </div> -->
        </div>

        <tail-bar></tail-bar>
    </div>
</template>

<script>
import NavigationBar from './common/NavigationBar.vue';
import TailBar from './common/TailBar.vue';

export default {
    name: 'Detail',
    components: {
        NavigationBar,
        TailBar,
    },
    data() {
        return {
            textarea: '',
            commodityId: this.$route.params.commodityId,
            good: {},
            comments: [],
            imgList: [
                { url: '' },
                // { url: require('@/assets/backiee-86446.jpg') },
                // { url: require('@/assets/backiee-88224.jpg') },
                // { url: require('@/assets/backiee-91323.jpg') },
                // { url: require('@/assets/backiee-119162.jpg') },
            ],
            value: null,
            colors: ['#99A9BF', '#F7BA2A', '#FF9900'],
        };
    },
    methods: {
        getgood() {
            this.$axios.get('/commodity/info/' + this.commodityId).then(res => {
                this.good = res.data.data;
                this.imgList[0].url = res.data.data.image;
            });
        },
        getComments() {
            this.$axios.get('/comment/list/' + this.commodityId).then(res => {
                this.comments = res.data.data;
            });
        },
        toComment() {
            var commentDto = {};
            commentDto.commodityId = this.commodityId;
            commentDto.content = this.textarea;
            this.$axios.post('/comment/save', commentDto).then(res => {
                this.$message({
                    showClose: true,
                    message: '发布成功',
                    type: 'success',
                });
                this.getComments();
                this.textarea = '';
            });
        },
        toPay(id) {
            this.$axios.post('/orderMaster/save/' + id).then(res => {
                this.$message({
                    showClose: true,
                    message: '已进入支付页面',
                    type: 'success',
                });
                // this.$axios.post('/orderMaster/alipay/' + id).then(res => {
                //     // 添加之前先删除一下，如果单页面，页面不刷新，添加进去的内容会一直保留在页面中，二次调用form表单会出错
                //     const divForm = document.getElementsByTagName('div');
                //     if (divForm.length) {
                //         document.body.removeChild(divForm[0]);
                //     }
                //     const div = document.createElement('div');
                //     div.innerHTML = res.data.data; // data就是接口返回的form 表单字符串
                //     document.body.appendChild(div);
                //     document.forms[0].setAttribute('target', '_blank'); // 新开窗口跳转
                //     document.forms[0].submit();
                // });
                // this.$axios.get('/mail/purchase');
                this.$router.push({
                    name: 'payment',
                    params: {
                        commodityId: id,
                        orderId: res.data.data.id,
                    },
                });
            });
        },
        addCart(commodityId) {
            this.$axios.post('/cart/save/' + commodityId).then(res => {
                this.$message({
                    showClose: true,
                    message: '成功加入购物车',
                    type: 'success',
                });
            });
        },
        addColl(commodityId) {
            this.$axios.post('/collection/save/' + commodityId);
            this.$message({
                showClose: true,
                message: '收藏成功',
                type: 'success',
            });
        },
    },
    created() {
        this.getgood();
        this.getComments();
    },
};
</script>

<style lang="less" scoped>
.footer {
    background-color: #2b2e33;
    height: 360px;
    width: 100vw;
    position: absolute;
    top: 2200px;
    left: 0;
    > * {
        font-size: 14px;
    }
}
.zuida {
    position: relative;
}
.mian {
    width: 80%;
    height: 1800px;
    border: 1px #e3e3e3 solid;
    position: absolute;
    top: 200px;
    left: 50%;
    margin-left: -40%;
}
//position: relative;/*父元素通过定位，成为后代元素的参考点*/
/*position: absolute;
width: 120px;
height:50px;必须指定宽高
left:50%;
top:50%;
margin-left: -60px;
margin-top: -25px; 
水平：left:50%;margin-left:-w/2
垂直：top:50%;margin-top:-h/2
*/
.lunbo {
    border-radius: 5px;
    margin-left: 10px;
    margin-top: 30px;
    width: 60%;
    float: left;
}
.goumai {
    margin-top: 30px;
    width: 38%;
    float: right;
}
.wenzi {
    font-family: '楷体';
    font-size: 20px;
    letter-spacing: 5px;
    margin-top: 30px;
    margin-left: 20px;
    width: 400px;
}
.jiage {
    font-family: '楷体';
    margin-top: 50px;
    margin-left: 20px;
    font-size: 35px;
}
.block {
    margin-top: 60px;
    font-size: 20px;
    font-family: '楷体';
    margin-left: 20px;
}
.goubtn {
    margin-top: 30px;
}
.fenxian {
    position: absolute;
    top: 600px;
}
.comment {
    width: 90%;
    height: auto;
    padding-bottom: 10px;
    border: 1px #e3e3e3 solid;
    position: absolute;
    top: 640px;
    left: 50%;
    margin-left: -45%;
    h2 {
        font-family: '楷体';
        color: rgb(15, 15, 15);
        text-align: center;
    }
}
.goodt {
    width: 90%;
    height: auto;
    position: absolute;
    top: 1640px;
    left: 50%;
    margin-left: -45%;
    h2 {
        color: rgb(43, 114, 161);
        text-align: center;
    }
}
.el-carousel__item h3 {
    color: #3365a7;
    font-size: 18px;
    opacity: 0.75;
    line-height: 300px;
    margin: 0;
}
.goodtu {
    img {
        width: 100%;
        height: auto;
    }
}
</style>
