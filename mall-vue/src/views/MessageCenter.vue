<template>
    <div>
        <navigation-bar></navigation-bar>
        <!-- 主体 -->
        <div class="mian">
            <p>消息中心</p>
            <div style="border-top:1px #e3e3e3 solid;"></div>
            <div>
                <el-input size="mini" placeholder="请输入消息标题" class="mailTitle" v-model="emailDto.title" clearable></el-input>

                <el-input
                    type="textarea"
                    :rows="21"
                    placeholder="请输入需要发送的内容"
                    maxlength="300"
                    show-word-limit
                    v-model="emailDto.text"
                ></el-input>

                <el-autocomplete
                    style="width: 800px"
                    size="mini"
                    class="inline-input"
                    v-model="emailDto.toEmail"
                    :fetch-suggestions="querySearch"
                    placeholder="请输入接收邮箱地址"
                    @select="handleSelect"
                ></el-autocomplete>

                <el-button type="success" plain round size="mini" @click="sendMail()">发送</el-button>
            </div>
        </div>

        <tail-bar></tail-bar>
    </div>
</template>

<script>
import NavigationBar from './common/NavigationBar.vue';
import TailBar from './common/TailBar.vue';

export default {
    name: 'MessageCenter',
    components: {
        NavigationBar,
        TailBar,
    },
    data() {
        return {
            restaurants: [],
            emailDto: {},
        };
    },
    methods: {
        querySearch(queryString, cb) {
            var restaurants = this.restaurants;
            var results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants;
            // 调用 callback 返回建议列表的数据
            cb(results);
        },
        createFilter(queryString) {
            return restaurant => {
                return restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0;
            };
        },
        loadAll() {
            return [
                { value: '2536747828@qq.com' },
                { value: 'huanglei19990829@163.com' },
                { value: 'liupeng20000202@163.com' },
                { value: '234223@qq.com' },
                { value: '887723@qq.com' },
                { value: '625523@qq.com' },
                { value: '233322@qq.com' },
                { value: '2233322@qq.com' },
                { value: 'liupeng20000202@163.com' },
                { value: 'hysdhyh65@163.com' },
                { value: '2536747828@qq.com' },
                { value: '87723@163.com' },
                { value: '65s6623@163.com' },
                { value: '22333s2@163.com' },
            ];
        },
        handleSelect(item) {
            //console.log(item);
        },
        sendMail() {
            this.$axios.post('/mail/send', this.emailDto).then(res => {
                const h = this.$createElement;
                this.$notify({
                    title: '成功消息',
                    message: h('i', { style: 'color: teal' }, '发送成功'),
                    position: 'bottom-right',
                    type: 'success',
                });
                this.emailDto = {};
            });
        },
    },
    mounted() {
        this.restaurants = this.loadAll();
    },
};
</script>

<style lang="less" scoped>
.footer {
    background-color: #2b2e33;
    height: 360px;
    width: 100vw;
    position: absolute;
    top: 1200px;
    left: 0;
    > * {
        font-size: 14px;
    }
}
.mian {
    width: 80%;
    height: 960px;
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

.mailTitle {
    width: 500px;
    margin: 10px 380px;
}
.inline-input {
    margin-top: 20px;
    margin-left: 170px;
    margin-right: 20px;
}
</style>
