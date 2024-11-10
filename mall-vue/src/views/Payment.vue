<template>
    <div>
        <navigation-bar></navigation-bar>
        <!-- 主体 -->
        <div class="mian">
            <div class="mind">
                <p>
                    请支付
                    <span style="color: red">￥{{ good.price }}</span>
                </p>
                <img src="../assets/pay.jpg" style="width: 250px; margin-left: 350px; margin-bottom: 20px" alt="" />
                <el-button class="shou" type="primary" @click="confirmPay()">确认支付</el-button>
                <el-button type="info" style="margin-right: 60px;" @click="toFail()">取消支付</el-button>
                <el-button type="success" plain @click="returnHome()">返回首页</el-button>
            </div>
        </div>

        <tail-bar></tail-bar>
    </div>
</template>

<script>
import NavigationBar from './common/NavigationBar.vue';
import TailBar from './common/TailBar.vue';

export default {
    name: 'Payment',
    components: {
        NavigationBar,
        TailBar,
    },
    data() {
        return {
            commodityId: this.$route.params.commodityId,
            good: {},
            orderId: this.$route.params.orderId,
        };
    },
    methods: {
        getgood() {
            this.$axios.get('commodity/info/' + this.commodityId).then(res => {
                //console.log('订单Id');
                //console.log(this.orderId);
                this.good = res.data.data;
            });
        },
        confirmPay() {
            // this.$axios.post('/orderMaster/alipay/' + this.commodityId).then(res => {
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
            this.$axios.post('/orderMaster/paidSave/' + this.orderId);
            this.$axios.post('/userCommodity/save/' + this.commodityId);
            this.$message({
                showClose: true,
                message: '支付成功',
                type: 'success',
            });
            this.$router.push('/paymentsuccessful');
        },
        toFail() {
            //this.$axios.get('/mail/order');
            this.$message({
                showClose: true,
                message: '成功取消支付',
                type: 'success',
            });
            this.$router.push('/paymentfail');
        },
        returnHome() {
            this.$message({
                showClose: true,
                message: '返回成功',
                type: 'success',
            });
            this.$router.push('/');
        },
    },
    created() {
        this.getgood();
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
    height: 800px;
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
    top: 20px;
    left: 50%;
    margin-left: -40%;
}
.shou {
    margin-left: 250px;
    margin-right: 60px;
}
</style>
