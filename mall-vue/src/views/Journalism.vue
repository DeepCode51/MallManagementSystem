<template>
    <div>
        <navigation-bar></navigation-bar>
        <!-- 主体 -->
        <div class="mian">
            <p>推荐商品</p>
            <div style="border-top:1px #e3e3e3 solid;"></div>
            <el-row :gutter="20">
                <el-col :span="5" style="padding: 0" v-for="(commodity, index) in commoditys" :key="index">
                    <div class="box">
                        <img class="tu" :src="commodity.image" alt />
                        <a href @click.prevent="toDetail(commodity.id)" class="describe">
                            {{ commodity.operdescribe }}
                        </a>
                        <div class="price">￥{{ commodity.price }}</div>
                        <div class="shop">{{ commodity.shop }}</div>
                    </div>
                </el-col>
            </el-row>
            <!-- <el-button class="addbtn" v-if="hasAuth('1')" @click="addSoftware()">添加商品</el-button> -->

            <!-- 添加商品框 -->
            <el-dialog title="添加商品" :visible.sync="visible" width="40%" center>
                <el-form
                    style="margin-left: 70px"
                    label-position="left"
                    :model="infoForm"
                    :rules="rules"
                    ref="infoForm"
                    label-width="110px"
                    hide-required-asterisk
                >
                    <el-form-item label="商品名称" prop="name" style="width: 380px;">
                        <el-input v-model="infoForm.name"></el-input>
                    </el-form-item>
                    <el-form-item label="店铺名" prop="shop" style="width: 380px;">
                        <el-input v-model="infoForm.shop"></el-input>
                    </el-form-item>
                    <el-form-item label="商品基本描述" prop="operdescribe" style="width: 380px;">
                        <el-input v-model="infoForm.operdescribe"></el-input>
                    </el-form-item>
                    <el-form-item label="商品图片" prop="image" style="width: 380px;">
                        <el-input v-model="infoForm.image"></el-input>
                    </el-form-item>
                    <el-form-item label="价格" prop="price" style="width: 380px;">
                        <el-input v-model="infoForm.price"></el-input>
                    </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="resetForm('infoForm')">重 置</el-button>
                    <el-button type="primary" @click="adddSoftware()">上 架</el-button>
                </span>
            </el-dialog>
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
            visible: false,
            commoditys: [],
            infoForm: {},
            rules: {
                name: [{ required: true, message: '请输入商品名', trigger: 'blur' }],
                shop: [{ required: true, message: '请输入店铺名', trigger: 'blur' }],
                operdescribe: [{ required: true, message: '请输入商品的基本信息', trigger: 'blur' }],
                image: [{ required: true, message: '请输入商品图', trigger: 'blur' }],
                price: [{ required: true, message: '请输入商品价格', trigger: 'blur' }],
            },
        };
    },
    methods: {
        getCommoditys() {
            this.$axios.get('/commodityChild/list').then(res => {
                this.commoditys = res.data.data;
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
        addSoftware() {
            this.visible = true;
        },
        adddSoftware() {
            this.$axios.post('/commodityChild/save', this.infoForm).then(res => {
                this.$message({
                    showClose: true,
                    message: '上架成功',
                    type: 'success',
                });
                this.getCommoditys();
                this.visible = false;
            });
        },
        resetForm(formName) {
            this.$refs[formName].resetFields();
        },
    },
    created() {
        this.getCommoditys();
    },
};
</script>

<style lang="less" scoped>
.footer {
    background-color: #2b2e33;
    height: 360px;
    width: 100vw;
    position: absolute;
    top: 3800px;
    left: 0;
    > * {
        font-size: 14px;
    }
}
.mian {
    width: 80%;
    height: 2600px;
    border: 1px #e3e3e3 solid;
    position: absolute;
    top: 200px;
    left: 50%;
    margin-left: -40%;
    > p {
        color: #cc1515;
        font-family: '楷体';
        font-size: 25px;
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
.price {
    color: red;
    position: absolute;
    bottom: 4px;
    left: 4px;
}
.shop {
    position: absolute;
    bottom: 4px;
    right: 4px;
}
.addbtn {
    margin-top: 60px;
    width: 150px;
    position: relative;
    left: 50%;
    transform: translate(-50%);
}
</style>
