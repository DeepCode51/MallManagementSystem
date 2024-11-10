<template>
  <div class="main">
    <!--登录框-->
    <div class="loginFrame">
      <p class="text">用户登录</p>
      <el-form
        :model="loginForm"
        :rules="rules"
        ref="loginForm"
        label-width="80px"
        hide-required-asterisk
      >
        <el-form-item label="用户名" prop="username" style="width: 380px;">
          <el-input v-model="loginForm.username"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password" style="width: 380px;">
          <el-input v-model="loginForm.password" type="password"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitForm('loginForm')">登录</el-button>
          <el-button @click="regis()">注册</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import qs from "qs";

export default {
  username: "Login",
  data() {
    return {
      loginForm: {
        username: "",
        password: "",
        token: ""
      },
      rules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" }
        ],
        password: [{ required: true, message: "请输入密码", trigger: "blur" }]
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.$axios
            .post("/login?" + qs.stringify(this.loginForm))
            .then(res => {
              console.log(res);

              const jwt = res.headers["authorization"];

              this.$store.commit("SET_TOKEN", jwt);

              this.$message({
                showClose: true,
                message: "登录成功",
                type: "success"
              });
              this.$router.push("/");
            });
        } else {
          this.$message({
            showClose: true,
            message: "登录失败",
            type: "error"
          });
          return false;
        }
      });
    },
    regis() {
      this.$message({
        showClose: true,
        message: "已进入注册页面",
        type: "success"
      });
      this.$router.push("/register");
    }
  },
  created() {}
};
</script>


<style scoped>
.main {
  background: url("../assets/back.jpg") no-repeat;
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
  margin-left: 90px;
}

.bcak {
  width: 100vw;
  height: 100vh;
}

/*让登录框居中*/
.loginFrame {
  width: 600px;
  height: 350px;
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
