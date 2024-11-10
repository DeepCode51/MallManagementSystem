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
            const colors = ['#5793f3', '#675bba', '#d14a61'];
            this.column.setOption({
                color: colors,
                title: {
                    text: '折线图',
                    subtext: '',
                },
                tooltip: {
                    trigger: 'axis',
                },
                legend: {
                    data: ['用户数', '订单数', '店铺数'],
                },
                toolbox: {
                    show: true,
                    feature: {
                        dataZoom: {
                            yAxisIndex: 'none',
                        },
                        dataView: { readOnly: false },
                        magicType: { type: ['bar', 'line'] },
                        restore: {},
                    },
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: ['9-21', '9-22', '9-23', '9-24', '9-25', '9-26', '9-27'],
                },
                yAxis: [
                    {
                        type: 'value',
                        name: '用户',
                        min: 0,
                        max: 200,
                        position: 'left',
                        axisLine: {
                            lineStyle: {
                                color: '#999',
                            },
                        },
                        axisLabel: {
                            formatter: '{value}',
                        },
                    },
                    {
                        type: 'value',
                        name: '订单',
                        min: 0,
                        max: 200,
                        position: 'right',
                        axisLine: {
                            lineStyle: {
                                color: '#999',
                            },
                        },
                        axisLabel: {
                            formatter: '{value}',
                        },
                    },
                ],
                series: [
                    {
                        name: '用户数',
                        type: 'line',
                        smooth: true,
                        data: ['23', '35', '22', '38', '37', '88', '89'],
                        yAxisIndex: 1,
                        markPoint: {
                            data: [
                                { type: 'max', name: '最大值' },
                                { type: 'min', name: '最小值' },
                            ],
                        },
                    },
                    {
                        name: '订单数',
                        type: 'line',
                        smooth: true,
                        data: ['58', '88', '188', '199', '27', '100', '200'],
                        yAxisIndex: 1,
                        markPoint: {
                            data: [
                                { type: 'max', name: '最大值' },
                                { type: 'min', name: '最小值' },
                            ],
                        },
                    },
                    {
                        name: '店铺数',
                        type: 'line',
                        smooth: true,
                        data: ['45', '67', '55', '87', '133', '23', '84'],
                        yAxisIndex: 1,
                        markPoint: {
                            data: [
                                { type: 'max', name: '最大值' },
                                { type: 'min', name: '最小值' },
                            ],
                        },
                    },
                ],
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
