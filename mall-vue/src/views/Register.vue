<template>
    <div class="main">
        <div class="registerFrame">
            <p class="text">用户注册</p>
            <el-form :model="registerForm" :rules="rules" ref="registerForm" label-width="110px" hide-required-asterisk>
                <el-form-item label="用户名" prop="name" style="width: 380px;">
                    <el-input v-model="registerForm.name"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="pass" style="width: 380px;">
                    <el-input v-model="registerForm.pass" type="password"></el-input>
                </el-form-item>
                <el-form-item label="确认密码" prop="checkpass" style="width: 380px;">
                    <el-input v-model="registerForm.checkpass" type="password"></el-input>
                </el-form-item>
                <el-form-item label="性别" prop="sex" style="width: 380px;">
                    <el-input v-model="registerForm.sex"></el-input>
                </el-form-item>
                <el-form-item label="年龄" prop="age" style="width: 380px;">
                    <el-input v-model="registerForm.age"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" prop="email" style="width: 380px;">
                    <el-input v-model="registerForm.email"></el-input>
                </el-form-item>
                <el-form-item label="是否注册为店家" prop="isBusiness" style="width: 380px;">
                    <el-input placeholder="1为店家，0为买家" v-model="registerForm.isBusiness"></el-input>
                </el-form-item>

                <el-form-item>
                    <el-button @click="Register('registerForm')" type="primary">注册</el-button>
                    <el-button @click="resetForm('registerForm')">重置</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
export default {
    name: 'Register',
    data() {
        var validatePass = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请再次输入密码'));
            } else if (value !== this.registerForm.pass) {
                callback(new Error('两次输入密码不一致!'));
            } else {
                callback();
            }
        };
        return {
            registerForm: {},
            rules: {
                name: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
                pass: [{ required: true, message: '请输入密码', trigger: 'blur' }],
                checkpass: [{ validator: validatePass, trigger: 'blur' }],
                sex: [{ required: true, message: '请输入性别', trigger: 'blur' }],
                age: [{ required: true, message: '请输入年龄', trigger: 'blur' }],
                email: [{ required: true, message: '请输入收货邮箱', trigger: 'blur' }],
                isBusiness: [{ required: true, message: '请输入0或1', trigger: 'blur' }],
            },
        };
    },
    methods: {
        Register(formName) {
            this.$refs[formName].validate(valid => {
                if (valid) {
                    this.$axios.post('/user/save', this.registerForm).then(res => {
                        this.$message({
                            showClose: true,
                            message: '注册成功',
                            type: 'success',
                        });
                        this.$router.push('/login');
                    });
                } else {
                    this.$message({
                        showClose: true,
                        message: '注册失败',
                        type: 'error',
                    });
                    return false;
                }
            });
        },
        resetForm(formName) {
            this.$refs[formName].resetFields();
        },
    },
    created() {},
};
</script>

<style scoped>
.main {
    background: url('../assets/back.jpg') no-repeat;
    background-position: center;
    height: 100%;
    width: 100%;
    background-size: cover;
    position: fixed;
}

.text {
    text-align: center;
    font-size: 20px;
    color: aliceblue;
}

.el-button {
    margin-left: 50px;
}

.el-form-item {
    margin-left: 70px;
}

.bcak {
    width: 100vw;
    height: 100vh;
}

/*让框居中*/
.registerFrame {
    width: 600px;
    height: 650px;
    background: rgba(0, 0, 0, 0.3);
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    margin: auto;
    /*添加边框圆角*/
    border-radius: 10px;

    /*添加边距挤下去*/
    padding: 50px 0;
    box-sizing: border-box;
    /*添加阴影*/
    box-shadow: 0 0 5px 5px rgba(0, 0, 0, 0.4);
}
</style>
