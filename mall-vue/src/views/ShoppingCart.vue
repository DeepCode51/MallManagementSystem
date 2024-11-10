<template>
    <div>
        <navigation-bar></navigation-bar>
        <!-- 主体 -->
        <div class="mian">
            <p>购物车</p>
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
                <el-table-column prop="name" label="商品名" width="120" align="center"></el-table-column>
                <el-table-column prop="shop" label="店铺名" width="120" align="center"></el-table-column>
                <el-table-column prop="price" label="价格" align="center"></el-table-column>
                <el-table-column prop="id" label="操作" align="center" show-overflow-tooltip>
                    <template slot-scope="scope">
                        <el-button type="success" size="small" @click="payBtn(scope.row.id)">结算</el-button>
                        <el-divider direction="vertical"></el-divider>

                        <el-button type="primary" size="small" @click="addColl(scope.row.id)">移入收藏</el-button>
                        <el-divider direction="vertical"></el-divider>

                        <template>
                            <el-popconfirm title="确定删除该商品吗？" @confirm="delHandle(scope.row.id)">
                                <el-button type="danger" size="small" slot="reference">删除</el-button>
                            </el-popconfirm>
                        </template>
                    </template>
                </el-table-column>
            </el-table>

            <!-- 批量操作 -->

            <template>
                <el-popconfirm title="确定删除所选择商品吗？" @confirm="delHandle(null)">
                    <el-button style="margin: 20px 20px; float: right" :disabled="selectionstatus" type="danger" slot="reference">批量删除</el-button>
                </el-popconfirm>
            </template>
            <!-- <el-button style="margin: 20px 20px; float: right" :disabled="selectionstatus" type="primary">结算</el-button> -->
        </div>

        <!-- 结算弹出框 -->
        <el-dialog title="付款" :visible.sync="payFrame" width="40%" center>
            <p style="font-size: 20px; text-align: center">
                请支付
                <span style="color: red">￥{{ payForm.price }}</span>
            </p>
            <img src="../assets/pay.jpg" style="width: 250px; margin-left: 150px" alt="" />
            <span slot="footer" class="dialog-footer">
                <el-button @click="quXiao()">取 消</el-button>
                <el-button type="primary" @click="settlement(payForm.id)">支 付</el-button>
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
            payFrame: false,
            selectionstatus: true,
            payForm: {},
            tableData: [],
            multipleSelection: [],
            order: {},
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
        getCartInfo() {
            this.$axios.get('/cart/list').then(res => {
                this.tableData = res.data.data;
            });
        },
        payBtn(id) {
            this.payFrame = true;
            this.$axios.get('/cart/info/' + id).then(res => {
                this.payForm = res.data.data;
                this.$axios.post('/orderMaster/save/' + res.data.data.id).then(res => {
                    this.order = res.data.data;
                });
            });
        },
        //结算方法
        settlement(commodityId) {
            this.$axios.post('/orderMaster/paidSave/' + this.order.id).then(res => {
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

                this.$message({
                    showClose: true,
                    message: '支付成功',
                    type: 'success',
                    onClose: () => {
                        this.$axios.get('/cart/infoo/' + commodityId).then(res => {
                            this.delHandle(res.data.data.id);
                        });
                    },
                });
                this.$axios.post('/userCommodity/save/' + commodityId);
                this.payFrame = false;
            });
        },
        quXiao() {
            this.payFrame = false;
            //this.$axios.get('/mail/order');
        },
        addColl(id) {
            this.$axios.get('/cart/info/' + id).then(res => {
                this.$axios.post('/collection/save/' + res.data.data.id);
                this.$message({
                    showClose: true,
                    message: '收藏成功',
                    type: 'success',
                });
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
            console.log(ids);
            this.$axios.post('/cart/delete/' + ids).then(res => {
                this.$message({
                    showClose: true,
                    message: '删除成功',
                    type: 'success',
                });
                this.getCartInfo();
            });
        },
    },
    created() {
        this.getCartInfo();
    },
};
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
    height: 1560px;
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
