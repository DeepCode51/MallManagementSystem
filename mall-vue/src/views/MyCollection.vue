<template>
    <div>
        <navigation-bar></navigation-bar>
        <!-- 主体 -->
        <div class="mian">
            <p>我的收藏</p>
            <div style="border-top:1px #e3e3e3 solid;"></div>
            <el-row :gutter="20">
                <el-col :span="5" style="padding: 0" v-for="(collection, index) in collections" :key="index">
                    <div class="box">
                        <img class="tu" :src="collection.image" alt />
                        <a href @click.prevent="toDetail(collection.id)" class="describe">
                            {{ collection.operdescribe }}
                        </a>
                        <el-button class="jiabtn" type="primary " size="small" @click="addCart(collection.id)">加入购物车</el-button>
                        <el-button class="delbtn" type="danger" icon="el-icon-delete" size="small" @click="todelete(collection.id)"></el-button>
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
    name: 'mycollection',
    components: {
        NavigationBar,
        TailBar,
    },
    data() {
        return {
            collections: [],
        };
    },
    methods: {
        getcollections() {
            this.$axios.get('/collection/list').then(res => {
                this.collections = res.data.data;
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
        todelete(id) {
            this.$axios.get('/collection/info/' + id).then(res => {
                var collectionId = res.data.data.id;
                this.$axios.post('/collection/delete/' + collectionId).then(res => {
                    this.$message({
                        showClose: true,
                        message: '删除成功',
                        type: 'success',
                    });
                    this.getcollections();
                });
            });
        },
    },
    created() {
        this.getcollections();
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
