<template>
    <div>
        <navigation-bar></navigation-bar>
        <!-- 主体 -->
        <div class="mian">
            <p>我的订单</p>
            <div style="border-top:1px #e3e3e3 solid;"></div>

            <el-table
                ref="multipleTable"
                :data="tableData"
                tooltip-effect="dark"
                style="width: 100%;margin-top: 20px;"
                border
                stripe
                @selection-change="handleSelectionChange"
            >
                <el-table-column type="selection" align="center" width="55"></el-table-column>
                <el-table-column prop="orderId" label="订单号" width="200" align="center"></el-table-column>
                <el-table-column prop="name" label="商品名" width="120" align="center"></el-table-column>
                <el-table-column prop="shop" label="店铺名" width="120" align="center"></el-table-column>
                <el-table-column prop="status" label="订单状态" width="120" align="center">
                    <template slot-scope="scope">
                        <el-tag type="success" v-if="scope.row.status == 1">已付款</el-tag>
                        <el-tag type="danger" v-else-if="scope.row.status == 0">未付款</el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="email" label="收货邮箱" align="center"></el-table-column>
                <el-table-column prop="price" label="价格" align="center"></el-table-column>
                <el-table-column prop label="操作" align="center" show-overflow-tooltip>
                    <template slot-scope="scope">
                        <el-button
                            style="margin-right: 7px"
                            type="success"
                            size="small"
                            v-if="scope.row.status == 0"
                            @click="payFrametrue(scope.row.id)"
                        >
                            付款
                        </el-button>

                        <template>
                            <el-popconfirm title="确定删除该订单吗？" @confirm="delHandle(scope.row.id)">
                                <el-button type="danger" size="small" slot="reference">删除</el-button>
                            </el-popconfirm>
                        </template>
                    </template>
                </el-table-column>
            </el-table>

            <!-- 批量操作 -->
            <template>
                <el-popconfirm title="确定删除所选择订单吗？" @confirm="delHandle(null)">
                    <el-button style="margin: 20px 20px; float: right" :disabled="selectionstatus" type="danger" slot="reference">批量删除</el-button>
                </el-popconfirm>
            </template>
            <el-button style="margin: 20px 20px; float: right" :disabled="selectionstatus" type="primary">结算</el-button>
        </div>

        <!-- 结算弹出框 -->
        <el-dialog title="付款" :visible.sync="payFrame" width="40%" center>
            <p style="font-size: 20px; text-align: center">
                请支付
                <span style="color: red">￥{{ payForm.price }}</span>
            </p>
            <img src="../assets/pay.jpg" style="width: 250px; margin-left: 150px" alt="" />
            <span slot="footer" class="dialog-footer">
                <el-button @click="payFrame = false">取 消</el-button>
                <el-button type="primary" @click="settlement(payForm.id, payForm.commodityId)">支 付</el-button>
            </span>
        </el-dialog>

        <tail-bar></tail-bar>
    </div>
</template>

<script>
import NavigationBar from './common/NavigationBar.vue';
import TailBar from './common/TailBar.vue';

export default {
    name: 'myorder',
    components: {
        NavigationBar,
        TailBar,
    },
    data() {
        return {
            tableData: [],
            multipleSelection: [],
            selectionstatus: true,
            payFrame: false,
            payForm: {},
        };
    },
    methods: {
        toggleSelection(rows) {
            if (rows) {
                rows.forEach(row => {
                    this.$refs.multipleTable.toggleRowSelection(row);
                });
            } else {
                this.$refs.multipleTable.clearSelection();
            }
        },
        handleSelectionChange(val) {
            this.multipleSelection = val;
            this.selectionstatus = val.length == 0;
        },
        getOrderInfo() {
            this.$axios.get('/orderMaster/list').then(res => {
                this.tableData = res.data.data;
            });
        },
        payFrametrue(id) {
            this.payFrame = true;
            this.$axios.get('/orderMaster/info/' + id).then(res => {
                this.payForm = res.data.data;
            });
        },
        //结算方法
        settlement(id, commodityId) {
            this.$axios.post('/orderMaster/paidSave/' + id).then(res => {
                this.$message({
                    showClose: true,
                    message: '支付成功',
                    type: 'success',
                });
                this.getOrderInfo();
                this.$axios.post('/userCommodity/save/' + commodityId);
                this.payFrame = false;
                // this.$axios.post('/orderMaster/alipay/' + commodityId).then(res => {
                //     // 添加之前先删除一下，如果单页面，页面不刷新，添加进去的内容会一直保留在页面中，二次调用form表单会出错
                //     const divForm = document.getElementsByTagName('div');
                //     if (divForm.length) {
                //         document.body.removeChild(divForm[0]);
                //     }
                //     const div = document.createElement('div');
                //     div.innerHTML = res.data.data; // data就是接口返回的form 表单字符串
                //     document.body.appendChild(div);
                //     document.forms[0].setAttribute('target', '_blank'); // 新开窗口跳转
                //     document.forms[0].submit();
                // });
            });
        },
        delHandle(id) {
            var ids = [];
            if (id) {
                ids.push(id);
            } else {
                this.multipleSelection.forEach(row => {
                    ids.push(row.id);
                });
            }
            //console.log(ids);
            this.$axios.post('/orderMaster/delete', ids).then(res => {
                this.$message({
                    showClose: true,
                    message: '删除成功',
                    type: 'success',
                });
                this.getOrderInfo();
            });
        },
    },
    created() {
        this.getOrderInfo();
    },
};
</script>

<style lang="less" scoped>
.mian {
    width: 80%;
    height: 2060px;
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
</style>
