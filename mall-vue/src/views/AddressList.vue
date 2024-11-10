<template>
    <div>
        <navigation-bar></navigation-bar>
        <!-- 主体 -->
        <div class="mian">
            <p>我的地址</p>
            <div style="border-top:1px #e3e3e3 solid;"></div>

            <el-button type="primary" @click="addvisible = true">添加地址</el-button>


			<!-- 当el-table元素中注入data对象数组后，在el-table-column中用prop属性来对应对象中的键名即可填入数据，
			用label属性来定义表格的列名。可以使用width属性来定义列宽。-->
			
			<!--实现多选非常简单: 手动添加一个el-table-column，设type属性为selection即可；默认情况下若内容过多会折行显示，
			若需要单行显示可以使用show-overflow-tooltip属性，它接受一个Boolean，当内容过长被隐藏时显示 tooltip
			为true时多余的内容会在 hover 时以 tooltip 的形式显示出来。 
			selection-change	当选择项发生变化时会触发该事件-->
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
                <el-table-column prop="userid" label="用户id" width="70" align="center"></el-table-column>
                <el-table-column prop="address" label="地址"  align="center"></el-table-column>
                <el-table-column prop="name" label="收货人" width="200" align="center"></el-table-column>
                <el-table-column prop="phone" label="手机号" width="250" align="center"></el-table-column>
                <el-table-column prop="isdefault" label="是否默认地址" width="150" align="center"></el-table-column>
                <el-table-column prop label="操作" align="center" show-overflow-tooltip>
				
					<!--通过 Scoped slot 可以获取到 row, column, $index 和 store（table 内部的状态管理）的数据 -->
                    <template slot-scope="scope">
                        <el-button style="margin-right: 7px" type="success" size="small" @click="edit(scope.row)">
                            编辑
                        </el-button>

					<!--点击元素，弹出气泡确认框
					在 Popconfirm 中，只有 title 属性可用，content 属性不会被展示
					 reference	触发 Popconfirm 显示的 HTML 元素
					 还可以嵌套操作，这相比 Dialog 更为轻量-->
                        <template>
                            <el-popconfirm title="确定删除该地址吗？" @confirm="delHandle(scope.row.id)">
                                <el-button type="danger" size="small" slot="reference">删除</el-button>
                            </el-popconfirm>
                        </template>
                    </template>
                </el-table-column>
            </el-table>

            <!-- 批量操作 -->
<!--            <template>-->
<!--                <el-popconfirm title="确定删除所选择用户吗？" @confirm="delHandle(null)">-->
<!--                    <el-button style="margin: 20px 20px; float: right" :disabled="selectionstatus" type="danger" slot="reference">批量删除</el-button>-->
<!--                </el-popconfirm>-->
<!--            </template>-->

            <!-- 编辑框 -->
			<!--Dialog 对话框   在保留当前页面状态的情况下，告知用户并承载相关操作。
			 需要设置visible属性，它接收Boolean，当为true时显示 Dialog。Dialog 分为两个部分：body和footer，
			 footer需要具名为footer的slot。title属性用于定义标题，它是可选的，默认值为空。
			 -->
            <el-dialog title="编辑地址" :visible.sync="visible" width="40%" center>
                <el-form
                    style="margin-left: 70px"
                    label-position="left"
                    :model="infoForm"
                    :rules="rules"
                    ref="infoForm"
                    label-width="110px"
                    hide-required-asterisk
                >
                    <el-form-item label="地址" prop="address" style="width: 380px;">
                        <el-input v-model="infoForm.address"></el-input>
                    </el-form-item>
                    <el-form-item label="收货人" prop="name" style="width: 380px;">
                        <el-input v-model="infoForm.name"></el-input>
                    </el-form-item>
                    <el-form-item label="手机号" prop="phone" style="width: 380px;">
                        <el-input v-model="infoForm.phone"></el-input>
                    </el-form-item>
                    <el-form-item label="是否默认地址" prop="isdefault" style="width: 380px;">
                        <el-radio-group v-model="infoForm.isdefault">
                            <el-radio label="是">是</el-radio>
                            <el-radio label="否">否</el-radio>
                        </el-radio-group>
                    </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                    <el-button type="primary" @click="update()">修 改</el-button>
                </span>
            </el-dialog>

            <!-- 添加用户框 -->
            <el-dialog title="添加用户" :visible.sync="addvisible" width="40%" center>
                <el-form
                    style="margin-left: 70px"
                    label-position="left"
                    :model="addinfoForm"
                    :rules="rules"
                    ref="addinfoForm"
                    label-width="110px"
                    hide-required-asterisk
                >
                    <el-form-item label="地址" prop="address" style="width: 380px;">
                        <el-input v-model="addinfoForm.address"></el-input>
                    </el-form-item>
                    <el-form-item label="收货人" prop="name" style="width: 380px;">
                        <el-input v-model="addinfoForm.name"></el-input>
                    </el-form-item>
                    <el-form-item label="手机号" prop="phone" style="width: 380px;">
                        <el-input v-model="addinfoForm.phone"></el-input>
                    </el-form-item>
                    <el-form-item label="是否默认地址" prop="isdefault" style="width: 380px;">
                        <el-radio-group v-model="addinfoForm.isdefault">
                            <el-radio label="是">是</el-radio>
                            <el-radio label="否">否</el-radio>
                        </el-radio-group>
                    </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="resetForm('addinfoForm')">重 置</el-button>
                    <el-button type="primary" @click="addbtn()">添 加</el-button>
                </span>
            </el-dialog>
        </div>
        <tail-bar></tail-bar>
    </div>
</template>

<script>
import NavigationBar from './common/NavigationBar';
import TailBar from './common/TailBar';

export default {
    name: 'AddressList',
    components: {
        NavigationBar,
        TailBar,
    },
    data() {
        return {
            input: '',
            visible: false,
            addvisible: false,
            drawer: false,
            multipleSelection: [],
            selectionstatus: true,
            tableData: [],
            infoForm: {
                address: '',
                isdefault: '否',
                name: '',
                phone: ''
            },
            addinfoForm: {
                address: '',
                isdefault: '否',
                name: '',
                phone: ''
            },
            rules: {
                
            },
        };
    },
    methods: {
        edit(row) {
            this.infoForm = {
                isdefault: '否'
            }
            this.infoForm = {... this.infoForm, ...row}
            // Object.assign(this.infoForm, row)
            this.visible = true
        },
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
            this.$axios.get('/address/list').then(res => {
                this.tableData = res.data.data;
            });
        },
        addbtn() {
            this.$axios.post('/address/save', this.addinfoForm).then(res => {
                console.log(res)
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
            this.$axios.post('/address/update', this.infoForm).then(res => {
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
            this.$axios.post('/address/delete', ids).then(res => {
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
