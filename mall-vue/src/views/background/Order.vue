<template>
    <div>
        <div class="softdiv">
            <el-input clearable placeholder="请输入要查找的订单号" style="width: 300px; margin: 20px 20px;" v-model="input" class="input-with-select">
                <el-button slot="append" icon="el-icon-search" @click="selectUser()"></el-button>
            </el-input>

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
                <el-table-column prop="price" label="价格" align="center"></el-table-column>
                <el-table-column prop label="操作" align="center" show-overflow-tooltip>
                    <template slot-scope="scope">
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
        </div>
    </div>
</template>

<script>
export default {
    name: 'Order',
    data() {
        return {
            input: '',
            multipleSelection: [],
            selectionstatus: true,
            tableData: [],
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
        getTableData() {
            this.$axios.get('/orderMaster/allOrder').then(res => {
                this.tableData = res.data.data;
            });
        },
        selectUser() {
            this.$axios.get('/orderMaster/select/' + this.input).then(res => {
                this.tableData = res.data.data;
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
            this.$axios.post('/orderMaster/delete', ids).then(res => {
                this.$message({
                    showClose: true,
                    message: '删除成功',
                    type: 'success',
                });
                this.getTableData();
            });
        },
    },
    created() {
        this.getTableData();
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
