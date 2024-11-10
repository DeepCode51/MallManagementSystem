<template>
    <div>
        <div class="softdiv">
            <div id="visitorpie" class="" style="width: 100%; height:750px;"></div>
        </div>
    </div>
</template>

<script>
import * as echarts from 'echarts';

export default {
    name: 'Stats',
    data() {
        return {
            visitorpie: null,
        };
    },
    methods: {
        drawVisitorpie() {
            this.visitorpie = echarts.init(document.getElementById('visitorpie'));
            this.visitorpie.setOption({
                title: {
                    text: '用户分布',
                    subtext: '',
                    x: 'center',
                },
                tooltip: {
                    trigger: 'item',
                    formatter: '{a} <br/>{b} : {c} ({d}%)',
                },
                legend: {
                    top: 'bottom',
                },
                toolbox: {
                    show: true,
                    feature: {
                        mark: { show: true },
                        dataView: { show: true, readOnly: false },
                        restore: { show: true },
                        saveAsImage: { show: true },
                    },
                },
                series: [
                    {
                        name: '访问来源',
                        type: 'pie',
                        radius: [50, 250],
                        center: ['50%', '50%'],
                        roseType: 'area',
                        itemStyle: {
                            borderRadius: 8,
                        },
                        data: [
                            { value: 38, name: '上海' },
                            { value: 32, name: '深圳' },
                            { value: 30, name: '杭州' },
                            { value: 28, name: '太原' },
                            { value: 40, name: '其他' },
                            { value: 26, name: '长沙' },
                            { value: 22, name: '广东' },
                            { value: 18, name: '北京' },
                        ],
                    },
                ],
            });
        },
    },
    mounted() {
        this.drawVisitorpie();
    },
    updated() {
        this.drawVisitorpie();
    },
};
</script>

<style lang="less" scoped>
.softdiv {
    width: 98%;
    height: 90%;
    border: 1px #e3e3e3 solid;
    margin: 10px 10px;
}
</style>
