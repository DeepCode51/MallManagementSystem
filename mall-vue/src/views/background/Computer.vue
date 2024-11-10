<template>
    <div>
        <div class="softdiv">
            <el-input clearable placeholder="请输入商品名" style="width: 300px; margin: 20px 20px;" v-model="input" class="input-with-select">
                <el-button slot="append" icon="el-icon-search" @click="selectSoft()"></el-button>
            </el-input>
            <el-button type="primary" @click="addvisible = true">添加商品</el-button>
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
                <el-table-column prop="id" label="商品id" width="120" align="center"></el-table-column>
                <el-table-column prop="name" label="商品名" width="120" align="center"></el-table-column>
                <el-table-column prop="shop" label="店铺名" width="120" align="center"></el-table-column>
                <el-table-column prop="price" label="价格" align="center"></el-table-column>
                <el-table-column prop label="操作" align="center" show-overflow-tooltip>
                    <template slot-scope="scope">
                        <el-button style="margin-right: 7px" type="success" size="small" @click="visible = true">
                            编辑
                        </el-button>

                        <template>
                            <el-popconfirm title="确定删除该商品吗？" @confirm="delHandle(scope.row.id)">
                                <el-button type="danger" size="small" slot="reference">删除</el-button>
                            </el-popconfirm>
                        </template>
                    </template>
                </el-table-column>
            </el-table>

            <!-- 分页 -->
            <el-pagination
                style="margin-top: 10px; margin-left: 10px"
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                layout="total, sizes, prev, pager, next, jumper"
                :page-sizes="[10, 20, 50, 100]"
                :current-page="current"
                :page-size="size"
                :total="total"
            ></el-pagination>

            <!-- 批量操作 -->
            <template>
                <el-popconfirm title="确定删除所选择商品吗？" @confirm="delHandle(null)">
                    <el-button style="margin: 20px 20px; float: right" :disabled="selectionstatus" type="danger" slot="reference">批量删除</el-button>
                </el-popconfirm>
            </template>

            <!-- 编辑框 -->
            <el-dialog title="编辑商品" :visible.sync="visible" width="40%" center>
                <el-form
                    style="margin-left: 70px"
                    label-position="left"
                    :model="infoForm"
                    :rules="rules"
                    ref="infoForm"
                    label-width="110px"
                    hide-required-asterisk
                >
                    <el-form-item label="商品ID" prop="id" style="width: 380px;">
                        <el-input placeholder="必填" v-model="infoForm.id"></el-input>
                    </el-form-item>
                    <el-form-item label="商品名称" prop="name" style="width: 380px;">
                        <el-input v-model="infoForm.name"></el-input>
                    </el-form-item>
                    <el-form-item label="店铺名" prop="shop" style="width: 380px;">
                        <el-input v-model="infoForm.shop"></el-input>
                    </el-form-item>
                    <el-form-item label="价格" prop="price" style="width: 380px;">
                        <el-input v-model="infoForm.price"></el-input>
                    </el-form-item>
                    <el-form-item label="商品分类" prop="commodityType" style="width: 380px;">
                        <el-input v-model="infoForm.commodityType"></el-input>
                    </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="resetForm('infoForm')">重 置</el-button>
                    <el-button type="primary" @click="update()">修 改</el-button>
                </span>
            </el-dialog>

            <!-- 添加商品框 -->
            <el-dialog title="添加商品" :visible.sync="addvisible" width="40%" center>
                <el-form
                    style="margin-left: 70px"
                    label-position="left"
                    :model="addinfoForm"
                    :rules="rules"
                    ref="addinfoForm"
                    label-width="110px"
                    hide-required-asterisk
                >
                    <el-form-item label="商品名称" prop="name" style="width: 380px;">
                        <el-input v-model="addinfoForm.name"></el-input>
                    </el-form-item>
                    <el-form-item label="店铺名" prop="shop" style="width: 380px;">
                        <el-input v-model="addinfoForm.shop"></el-input>
                    </el-form-item>
                    <el-form-item label="商品基本描述" prop="operdescribe" style="width: 380px;">
                        <el-input v-model="addinfoForm.operdescribe"></el-input>
                    </el-form-item>
                    <el-form-item label="商品图片" prop="image" style="width: 380px;">
                        <el-input v-model="addinfoForm.image"></el-input>
                    </el-form-item>
                    <el-form-item label="价格" prop="price" style="width: 380px;">
                        <el-input v-model="addinfoForm.price"></el-input>
                    </el-form-item>
                    <el-form-item label="商品分类" prop="commodityType" style="width: 380px;">
                        <el-input v-model="addinfoForm.commodityType"></el-input>
                    </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="resetForm('addinfoForm')">重 置</el-button>
                    <el-button type="primary" @click="addbtn()">添 加</el-button>
                </span>
            </el-dialog>
        </div>
    </div>
</template>

<script>
export default {
    name: 'Computer',
    data() {
        return {
            input: '',
            visible: false,
            addvisible: false,
            selectionstatus: true,
            total: 0,
            size: 10,
            current: 1,
            tableData: [],
            infoForm: {},
            addinfoForm: {},
            rules: {
                id: [{ required: true, message: '请输入商品ID', trigger: 'blur' }],
                name: [{ required: true, message: '请输入商品名', trigger: 'blur' }],
                shop: [{ required: true, message: '请输入店铺名', trigger: 'blur' }],
                operdescribe: [{ required: true, message: '请输入商品的基本信息', trigger: 'blur' }],
                image: [{ required: true, message: '请输入商品图', trigger: 'blur' }],
                price: [{ required: true, message: '请输入商品价格', trigger: 'blur' }],
            },
            commodityType: '电脑',
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
        handleSizeChange(val) {
            //console.log(`每页 ${val} 条`);
            this.size = val;
            this.getTableData();
        },
        handleCurrentChange(val) {
            //console.log(`当前页: ${val}`);
            this.current = val;
            this.getTableData();
        },
        getTableData() {
            this.$axios
                .get('/commodity/backQueryByType', {
                    params: {
                        commodityType: this.commodityType,
                        current: this.current,
                        size: this.size,
                    },
                })
                .then(res => {
                    this.tableData = res.data.data.records;
                    this.size = res.data.data.size;
                    this.current = res.data.data.current;
                    this.total = res.data.data.total;
                });
        },
        selectSoft() {
            this.$axios.get('/commodity/select/' + this.input).then(res => {
                this.tableData = res.data.data;
            });
        },
        addbtn() {
            this.$axios.post('/commodity/save', this.addinfoForm).then(res => {
                this.$message({
                    showClose: true,
                    message: '添加成功',
                    type: 'success',
                });
                this.getTableData();
            });
            this.addvisible = false;
        },
        update() {
            this.$axios.post('/commodity/update', this.infoForm).then(res => {
                this.$message({
                    showClose: true,
                    message: '修改成功',
                    type: 'success',
                });
                this.getTableData();
            });
            this.visible = false;
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
            this.$axios.post('/commodity/delete', ids).then(res => {
                this.$message({
                    showClose: true,
                    message: '删除成功',
                    type: 'success',
                });
                this.getTableData();
            });
        },
        resetForm(formName) {
            this.$refs[formName].resetFields();
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
