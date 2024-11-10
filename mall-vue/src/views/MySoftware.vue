<template>
    <div>
        <navigation-bar></navigation-bar>
        <!-- 主体 -->
        <div class="mian">
            <p>我的商品</p>
            <div style="border-top:1px #e3e3e3 solid;"></div>
            <el-row :gutter="20">
                <el-col :span="5" style="padding: 0" v-for="(good, index) in goods" :key="index">
                    <div class="box">
                        <img class="tu" :src="good.image" alt />
                        <a href @click.prevent="toDetail(good.id)" class="describe">{{ good.operdescribe }}</a>

                        <el-button class="jiabtn" type="success" @click="toPayOrder()" size="small">确认收货</el-button>

                        <el-button class="delbtn" type="danger" icon="el-icon-delete" size="small" @click="todelete(good.id)"></el-button>
                    </div>
                </el-col>
            </el-row>
        </div>

        <tail-bar></tail-bar>
    </div>
</template>

<script>
import NavigationBar from './common/NavigationBar.vue';
import TailBar from './common/TailBar.vue';

export default {
    name: 'mysoftware',
    components: {
        NavigationBar,
        TailBar,
    },
    data() {
        return {
            goods: [],
        };
    },
    methods: {
        getMySoftwareInfo() {
            this.$axios.get('/userCommodity/list').then(res => {
                this.goods = res.data.data;
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
        toPayOrder() {
            const h = this.$createElement;
            this.$notify({
                title: '收货成功',
                message: h('i', { style: 'color: teal' }, '确认收货成功，订单已完成可在订单页面查看详情'),
                position: 'bottom-right',
                type: 'success',
            });
        },
        todelete(commodityId) {
            this.$axios.post('/userCommodity/delete/' + commodityId).then(res => {
                this.$message({
                    showClose: true,
                    message: '删除成功',
                    type: 'success',
                });
                this.getMySoftwareInfo();
            });
        },
    },
    created() {
        this.getMySoftwareInfo();
    },
};
</script>

<style lang="less" scoped>
.footer {
    background-color: #2b2e33;
    height: 360px;
    width: 100vw;
    position: absolute;
    top: 1900px;
    left: 0;
    > * {
        font-size: 14px;
    }
}
.mian {
    width: 80%;
    height: 1500px;
    border: 1px #e3e3e3 solid;
    position: absolute;
    top: 200px;
    left: 50%;
    margin-left: -40%;
    > p {
        font-family: '楷体';
        font-size: 20px;
        text-align: center;
    }
}

.el-col {
    height: 320px;
    border-radius: 5px;
    margin: 15px 10px;
    background-color: white;
    position: relative;
    top: 20px;
    left: 55px;
    overflow: hidden; //不让溢出
    transition: all 0.6s;
}
.el-col:hover {
    box-shadow: 0px 15px 30px rgba(0, 0, 0, 0.5);
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
.jiabtn {
    position: absolute;
    bottom: 4px;
    left: 4px;
}
.delbtn {
    position: absolute;
    bottom: 4px;
    right: 4px;
}
</style>
