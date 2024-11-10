<template>
    <div>
        <div class="mm">
            <img src="https://img1.baidu.com/it/u=318258528,1264863502&fm=26&fmt=auto" alt="" />
            <p>关注我们</p>
        </div>
    </div>
</template>

<script>
export default {
    name: 'Aboutus',
    data() {
        return {};
    },
    created() {},
    methods: {},
};
</script>

<style lang="less" scoped>
.mm {
    width: 600px;
    height: 500px;
    position: absolute;
    top: 200px;
    left: 50%;
    > p {
        margin-left: 40px;
        font-family: '楷体';
        font-size: 30px;
    }
    img {
        width: 200px;
    }
}
</style>
