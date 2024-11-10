<template>
  <!-- 尾部友情链接 -->
    <div class="footer">
      <div class="alt">
        <div class="foot">
          <p>关于我们</p>
          <a href="###">企业简介</a>
          <a href="###">企业文化</a>
          <a href="###">组织机构</a>
          <a href="###">发展历程</a>
          <a href="###">企业荣誉</a>
          <a href="###">诚聘英才</a>
          <a href="###">联系我们</a>
        </div>
        <div class="foot">
          <p>新闻中心</p>
          <a href="###">公司新闻</a>
          <a href="###">行业新闻</a>
        </div>
        <div class="foot">
          <p>产品及解决方案</p>
          <a href="###">产品系列</a>
          <a href="###">解决方案</a>
        </div>
        <div class="foot">
          <p>研发与创新</p>
          <a href="###">核心技术</a>
          <a href="###">技术创新</a>
        </div>
        <div class="foot">
          <p>经典案例</p>
          <a href="###">智慧城市</a>
          <a href="###">智慧高速</a>
          <a href="###">智慧家居</a>
          <a href="###">智慧网络</a>
        </div>
        <div class="foot">
          <p>服务支持</p>
          <a href="###">技术支持</a>
          <a href="###">服务网络</a>
          <a href="###">资料下载</a>
          <a href="###">公司理念</a>
        </div>
        <div class="foot">
          <p>关注我们</p>
          <img src="https://img1.baidu.com/it/u=318258528,1264863502&fm=26&fmt=auto" />
        </div>
      </div>
      <div style="border-top:1px #1F1F1F solid;">
        <p class="alt" style="color:#999; line-height:0;">
          ========================================
          ========================================
          <span>技术支持：</span>
          <a href="###" target="_blank" style="color:#999;">================</a>
          <span style="float:right;">================</span>
        </p>
      </div>
    </div>
</template>

<script>
export default {
  name: "TailBar",
  data() {
    return {};
  },
  methods: {
    
  }
};
</script>

<style lang="less" scoped>

a {
  text-decoration: none;
}
img {
  border: none;
}

.footer {
  background-color: #2b2e33;
  height: 360px;
  width: 100vw;
  position: absolute;
  top: 5000px;
  left: 0;
  > * {
    font-size: 14px;
  }
}

.footer .alt {
  padding: 20px 0;
  width: 1160px;
  margin: auto;
  overflow: hidden;
}

.footer .alt .foot {
  width: 138px;
  float: left;
}

.footer .alt .foot p {
  color: #fff;
  line-height: 28px;
}

.footer .alt .foot a {
  color: #999;
  display: block;
  line-height: 24px;
}

.footer .alt .foot a:hover {
  color: #ccc;
}

.footer .alt .foot img {
  width: 200px;
  height: 200px;
  padding-top: 10px;
}


</style>