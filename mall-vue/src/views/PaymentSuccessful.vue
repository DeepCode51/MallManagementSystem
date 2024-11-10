<template>
    <div>
        <navigation-bar></navigation-bar>
        <!-- 主体 -->
        <div class="mian">
            <div class="mind">
                <p>支付成功</p>
                <el-button class="shou" type="primary" @click="returnHome()">返回首页</el-button>
                <el-button type="success" plain @click="toMySoftware()">查看我的商品</el-button>
            </div>
        </div>

        <tail-bar></tail-bar>
    </div>
</template>

<script>
import NavigationBar from './common/NavigationBar.vue';
import TailBar from './common/TailBar.vue';

export default {
    name: 'PaymentSuccessful',
    components: {
        NavigationBar,
        TailBar,
    },
    data() {
        return {};
    },
    methods: {
        toMySoftware() {
            //this.$axios.get('/mail/purchase');
            this.$message({
                showClose: true,
                message: '已进入我的商品',
                type: 'success',
            });
            this.$router.push('/mysoftware');
        },
        returnHome() {
            //this.$axios.get('/mail/purchase');
            this.$message({
                showClose: true,
                message: '返回成功',
                type: 'success',
            });
            this.$router.push('/');
        },
    },
};
</script>

<style lang="less" scoped>
.footer {
    background-color: #2b2e33;
    height: 360px;
    width: 100vw;
    position: absolute;
    top: 1100px;
    left: 0;
    > * {
        font-size: 14px;
    }
}

.mian {
    width: 80%;
    height: 500px;
    border: 1px #e3e3e3 solid;
    position: absolute;
    top: 200px;
    left: 50%;
    margin-left: -40%;
    > .mind > p {
        font-family: '楷体';
        font-size: 40px;
        text-align: center;
    }
}
.mind {
    width: 80%;
    height: 200px;
    position: absolute;
    top: 100px;
    left: 50%;
    margin-left: -40%;
}
.shou {
    margin-left: 350px;
    margin-right: 60px;
}
</style>
