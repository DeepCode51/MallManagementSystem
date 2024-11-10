<template>
    <div>
        <div class="softdiv">
            <div id="column" class="" style="width: 100%;height: 630px; margin-top: 20px"></div>
        </div>
    </div>
</template>

<script>
import * as echarts from 'echarts';

export default {
    name: 'Column',
    data() {
        return {
            column: null,
        };
    },
    methods: {
        drawColumn() {
            this.column = echarts.init(document.getElementById('column'));
            this.column.setOption({
                title: {
                    text: '柱型图',
                    subtext: '',
                },
                legend: {},
                tooltip: {},
                dataset: {
                    source: [
                        ['product', '订单数', '店铺数', '用户数'],
                        ['5月', 43.3, 85.8, 200],
                        ['6月', 83.1, 73.4, 55.1],
                        ['7月', 86.4, 65.2, 82.5],
                        ['8月', 72.4, 53.9, 39.1],
                        ['9月', 72.4, 53.9, 39.1],
                        ['10月', 72.4, 53.9, 39.1],
                    ],
                },
                xAxis: { type: 'category' },
                yAxis: {},
                series: [{ type: 'bar' }, { type: 'bar' }, { type: 'bar' }],
            });
        },
    },
    mounted() {
        this.drawColumn();
    },
};
</script>

<style lang="less" scoped>
.softdiv {
    width: 98%;
    height: 90%;
    border: 1px #e3e3e3 solid;
}
</style>
