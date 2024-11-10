<template>
    <div>
        <navigation-bar></navigation-bar>
        <!-- 主体 -->
        <div class="mian">
            <p>功能模块</p>
            <div style="border-top:1px #e3e3e3 solid;"></div>



            
        </div>
        

        <tail-bar></tail-bar>
    </div>
</template>


<script>
import NavigationBar from "./common/NavigationBar.vue";
import TailBar from "./common/TailBar.vue";

export default {
    name: "PaymentSuccessful",
    components: {
        NavigationBar,
        TailBar
    },
    data(){
        return{

        }
    }
}
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
  height: 2060px;
  border: 1px #e3e3e3 solid;
  position: absolute;
  top: 200px;
  left: 50%;
  margin-left: -40%;
  > p {
    font-family: "楷体";
    font-size: 20px;
    text-align: center;
  }
}
</style>