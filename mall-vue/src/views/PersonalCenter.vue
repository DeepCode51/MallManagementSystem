<template>
    <div>
        <navigation-bar></navigation-bar>
        <!-- 主体 -->
        <div class="mian">
            <p>个人中心</p>
            <div style="border-top:1px #e3e3e3 solid;"></div>
            <div class="information">
                <el-descriptions class="margin-top" title="个人信息" label="" :column="1" border>
                    <el-descriptions-item>
                        <template slot="label">
                            <i class="el-icon-user"></i>
                            用户名
                        </template>
                        {{ userInfoForm.name }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                        <template slot="label">
                            <i class="el-icon-mobile-phone"></i>
                            手机号
                        </template>
                        {{ userInfoForm.phone }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                        <template slot="label">
                            <i v-if="false" class="el-icon-male"></i>
                            <i v-if="true" class="el-icon-female"></i>
                            性别
                        </template>
                        {{ userInfoForm.sex }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                        <template slot="label">
                            <i class="el-icon-odometer"></i>
                            年龄
                        </template>
                        {{ userInfoForm.age }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                        <template slot="label">
                            <i class="el-icon-location-outline"></i>
                            居住地
                        </template>
                        {{ userInfoForm.address }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                        <template slot="label">
                            <i class="el-icon-tickets"></i>
                            账户角色
                        </template>
                        <el-tag size="medium" v-if="userInfoForm.isBusiness == 1" type="warning" style="margin-right: 10px">店铺</el-tag>
                        <el-tag size="medium" v-else-if="userInfoForm.isBusiness == 0" type="success">买家</el-tag>
                        <el-tag size="medium" v-if="hasAuth('ROLE_admin')">管理员</el-tag>
                    </el-descriptions-item>
                    <el-descriptions-item>
                        <template slot="label">
                            <i class="el-icon-sold-out"></i>
                            收货邮箱地址
                        </template>
                        {{ userInfoForm.email }}
                    </el-descriptions-item>
                </el-descriptions>
                <el-button type="info" style="margin: 40px 0px; float: right" @click="visible2 = true">修改基本信息</el-button>
                <el-button type="warning" style="margin: 40px 20px; float: right" @click="visible1 = true">修改密码</el-button>
                <el-button type="primary" style="margin: 40px 20px; float: left" v-if="hasAuth('1')" @click="addvisible = true">添加商品</el-button>
            </div>

            <!-- 修改密码框 -->
            <el-dialog title="密码" :visible.sync="visible1" width="40%" center>
                <el-form
                    style="margin-left: 70px"
                    label-position="left"
                    :model="passForm"
                    :rules="rules"
                    ref="passForm"
                    label-width="110px"
                    hide-required-asterisk
                >
                    <el-form-item label="旧密码" prop="pass" style="width: 380px;">
                        <el-input v-model="passForm.pass"></el-input>
                    </el-form-item>
                    <el-form-item label="新密码" prop="newPass" style="width: 380px;">
                        <el-input v-model="passForm.newPass" type="password"></el-input>
                    </el-form-item>
                    <el-form-item label="确认新密码" prop="checkpass" style="width: 380px;">
                        <el-input v-model="passForm.checkpass" type="password"></el-input>
                    </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="resetForm('passForm')">重 置</el-button>
                    <el-button type="primary" @click="updatePass()">修 改</el-button>
                </span>
            </el-dialog>

            <!-- 修改基本信息框 -->
            <el-dialog title="基本信息" :visible.sync="visible2" width="40%" center>
                <el-form
                    style="margin-left: 70px"
                    label-position="left"
                    :model="infoForm"
                    :rules="rules"
                    ref="infoForm"
                    label-width="110px"
                    hide-required-asterisk
                >
                    <el-form-item label="用户名" prop="name" style="width: 380px;">
                        <el-input v-model="infoForm.name"></el-input>
                    </el-form-item>
                    <el-form-item label="手机号" prop="phone" style="width: 380px;">
                        <el-input v-model="infoForm.phone"></el-input>
                    </el-form-item>
                    <el-form-item label="性别" prop="sex" style="width: 380px;">
                        <el-input v-model="infoForm.sex"></el-input>
                    </el-form-item>
                    <el-form-item label="年龄" prop="age" style="width: 380px;">
                        <el-input v-model="infoForm.age"></el-input>
                    </el-form-item>
                    <el-form-item label="居住地" prop="address" style="width: 380px;">
                        <el-input v-model="infoForm.address"></el-input>
                    </el-form-item>
                    <el-form-item label="是否修改为店铺" prop="isBusiness" style="width: 380px;">
                        <el-input placeholder="1为店家，0为买家" v-model="infoForm.isBusiness"></el-input>
                    </el-form-item>
                    <el-form-item label="收货邮箱" prop="email" style="width: 380px;">
                        <el-input v-model="infoForm.email"></el-input>
                    </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="resetForm('infoForm')">重 置</el-button>
                    <el-button type="primary" @click="updateInfo()">修 改</el-button>
                </span>
            </el-dialog>

            <!-- 添加商品框 -->
            <el-dialog title="添加商品" :visible.sync="addvisible" width="40%" center>
                <el-form
                    style="margin-left: 70px"
                    label-position="left"
                    :model="addinfoForm"
                    :rules="rules"
                    ref="addinfoForm"
                    label-width="110px"
                    hide-required-asterisk
                >
                    <el-form-item label="商品名称" prop="name" style="width: 380px;">
                        <el-input v-model="addinfoForm.name"></el-input>
                    </el-form-item>
                    <el-form-item label="店铺名" prop="shop" style="width: 380px;">
                        <el-input v-model="addinfoForm.shop"></el-input>
                    </el-form-item>
                    <el-form-item label="商品基本描述" prop="operdescribe" style="width: 380px;">
                        <el-input v-model="addinfoForm.operdescribe"></el-input>
                    </el-form-item>
                    <el-form-item label="商品图片" prop="image" style="width: 380px;">
                        <el-input v-model="addinfoForm.image"></el-input>
                    </el-form-item>
                    <el-form-item label="价格" prop="price" style="width: 380px;">
                        <el-input v-model="addinfoForm.price"></el-input>
                    </el-form-item>
                    <el-form-item label="商品分类" prop="commodityType" style="width: 380px;">
                        <el-input v-model="addinfoForm.commodityType"></el-input>
                    </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="resetForm('addinfoForm')">重 置</el-button>
                    <el-button type="primary" @click="addbtn()">添 加</el-button>
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
    name: 'PersonalCenter',
    components: {
        NavigationBar,
        TailBar,
    },
    data() {
        var validatePass = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请再次输入密码'));
            } else if (value !== this.passForm.newPass) {
                callback(new Error('两次输入密码不一致!'));
            } else {
                callback();
            }
        };
        return {
            visible1: false,
            visible2: false,
            addvisible: false,
            addinfoForm: {},
            passForm: {},
            infoForm: {},
            userInfoForm: {},
            rules: {
                pass: [{ required: true, message: '请输入旧密码', trigger: 'blur' }],
                newPass: [{ required: true, message: '请输入新密码', trigger: 'blur' }],
                checkpass: [{ validator: validatePass, trigger: 'blur' }],
                name: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
                phone: [{ required: true, message: '请输入手机号', trigger: 'blur' }],
                sex: [{ required: true, message: '请输入性别', trigger: 'blur' }],
                age: [{ required: true, message: '请输入年龄', trigger: 'blur' }],
                address: [{ required: true, message: '请输入居住地', trigger: 'blur' }],
                business: [{ required: true, message: '请输入1或0', trigger: 'blur' }],
                email: [{ required: true, message: '请输入收货邮箱', trigger: 'blur' }],
            },
        };
    },
    methods: {
        getUserInfoForm() {
            this.$axios.get('/user/info').then(res => {
                this.userInfoForm = res.data.data;
            });
        },
        resetForm(formName) {
            this.$refs[formName].resetFields();
        },
        addbtn() {
            this.$axios.post('/commodity/save', this.addinfoForm).then(res => {
                this.$message({
                    showClose: true,
                    message: '添加成功',
                    type: 'success',
                });
                this.getTableData();
            });
            this.addvisible = false;
        },
        updateInfo() {
            this.$axios.post('/user/update', this.infoForm).then(res => {
                this.$message({
                    showClose: true,
                    message: '修改成功',
                    type: 'success',
                });
                this.visible2 = false;
                this.getUserInfoForm();
            });
        },
        updatePass() {
            this.$axios.post('/user/updatePass', this.passForm).then(res => {
                this.$message({
                    showClose: true,
                    message: '修改成功',
                    type: 'success',
                });
                this.visible1 = false;
            });
        },
    },
    created() {
        this.getUserInfoForm();
    },
};
</script>

<style lang="less" scoped>
.footer {
    background-color: #2b2e33;
    height: 360px;
    width: 100vw;
    position: absolute;
    top: 1400px;
    left: 0;
    > * {
        font-size: 14px;
    }
}
.mian {
    width: 80%;
    height: 1060px;
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
.information {
    width: 80%;
    position: absolute;
    top: 100px;
    left: 50%;
    margin-left: -40%;
}
</style>
