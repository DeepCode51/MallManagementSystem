<template>
  <div class="ew">
    <h1>Scroll to see the animation</h1>
    <div class="box">
      <h2>Content</h2>
    </div>
    <div class="box">
      <h2>Content</h2>
    </div>
    <div class="box">
      <h2>Content</h2>
    </div>
    <div class="box">
      <h2>Content</h2>
    </div>
    <div class="box">
      <h2>Content</h2>
    </div>
    <div class="box" ref="foo">
      <h2>Content</h2>
    </div>
    <div class="box">
      <h2>Content</h2>
    </div>
    <div class="box">
      <h2>Content</h2>
    </div>
    <div class="box">
      <h2>Content</h2>
    </div>
    <div class="box">
      <h2>Content</h2>
    </div>
    <div class="box">
      <h2>Content</h2>
    </div>
    <div class="box">
      <h2>Content</h2>
    </div>
    <div class="box">
      <h2>Content</h2>
    </div>
  </div>
</template>


<script>
export default {
  data() {
    return {};
  },
  methods: {
    handleScroll() {
      //盒子的总高度
      //var documentScrollHeight =this.$refs.foo.getBoundingClientRect().top;
      //浏览器窗口的高度

      var heg = this.$refs.foo.getBoundingClientRect().top;
      var getWindowHeight = document.documentElement.clientHeight * 0.8;

      
      if (heg < getWindowHeight) {
        this.$refs.foo.classList.add("show");
      } else {
        this.$refs.foo.classList.remove("show");
      }
    }
  },

  mounted() {
    //监听scroll事件
    window.addEventListener("scroll", this.handleScroll);
  },
  destroyed() {
    //页面离开后销毁，防止切换路由后上一个页面监听scroll滚动事件会在新页面报错问题
    window.removeEventListener("scroll", this.handleScroll);
  }
};
</script>


<style lang="less" scoped>
.ew{
    width: 100%;
    overflow-x: hidden;
}
* {
  box-sizing: border-box;
}

h1 {
  margin: 10px;
}

.box {
  background-color: steelblue;
  color: #fff;
  //display: flex;
  align-items: center;
  justify-content: center;
  width: 400px;
  height: 200px;
  margin: 10px;
  border-radius: 10px;
  box-shadow: 2px 4px 5px rgba(0, 0, 0, 0.3);
  transform: translateX(800%);
  transition: transform 0.4s ease-in-out 0s;
}

/* .box:nth-of-type(even) {
    transform: translateX(-400%);
} */

.box.show {
  transform: translateX(0);
}

.box h2 {
  font-size: 45px;
}
</style>