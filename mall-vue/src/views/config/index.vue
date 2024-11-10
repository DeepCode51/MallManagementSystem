<template>
  <div class="softdiv">
    <div>
      <el-row type="flex" justify="space-between">
        <el-col :span="2">
          <el-button type="primary" style="margin-bottom: 10px" plain icon="el-icon-plus"
                     @click="dialogFormVisible = true">新增
          </el-button>
        </el-col>

        <el-col :span="6">
          <el-input placeholder="名称" v-model="keywords" class="input-with-select">
            <el-button slot="append" icon="el-icon-search" @click="getList('11')"></el-button>
          </el-input>
        </el-col>
      </el-row>
      <el-table :data="cargos" border>
        <el-table-column prop="name"  align="center" label="名称">
        </el-table-column>
        <el-table-column prop="value" label="图片" align="center" >
          <template slot-scope="scope">
            <img v-if="scope.row.value" class="list_img" :src="scope.row.value" />
            <el-empty v-else :image-size="100"></el-empty>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="100">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="edit(scope.row)">编辑</el-button>
            <el-button @click="rowDelete(scope.row)" type="text" size="small">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination backgroundL layout="prev, pager, next"
                     @current-change="handleCurrentChange"
                     :current-page.sync="currentPage"
                     :page-size="10"
                     :total="total">
      </el-pagination>
      <el-dialog title="编辑商品信息" :visible.sync="dialogFormVisible">
        <el-form :model="form" label-width="100px" ref="form">
          <el-row>
            <el-col :span="12">
              <el-form-item label="商品名称" prop="name">
                <el-input v-model="form.name" autocomplete="off"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="图片" style="margin-left: 10px; !important;" prop="image">
                <el-upload class="avatar-uploader" action="/api/file/upload"
                           :show-file-list="false" :on-success="handleAvatarSuccess"
                           :before-upload="beforeAvatarUpload">
                  <img v-if="form.value" :src="form.value" class="avatar">
                  <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                </el-upload>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false,resetData()">取 消</el-button>
          <el-button type="primary" @click="save()">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>

</template>

<script>
	export default {
		name: 'config',
		created() {
			this.getList()
		},
		data() {
			return {
				dialogFormVisible: false,
				keywords: '',
				cargos: [],
				allData: [],
				total: 0,
				// 当前页码设置为1
				currentPage: 1,
				// 每页显示10行数据
				pageSize: 10,
				form: {
					id: '',
					name: '',
					value: ''
				}
			}
		},
		computed: {
			// cargos() {
			//   return this.$store.getters.getCargos
			// }
		},
		methods: {
		  handleCurrentChange(pageIndex) {
			   if (this.allData) {
				   if (pageIndex) {
					   this.currentPage = pageIndex;
				   }
				   this.cargos = this.allData.slice(
					 (this.currentPage - 1) * this.pageSize,
					 this.currentPage * this.pageSize
				   );
				   console.log(this.cargos);
			   } else {}
		  },

			getList(flag) {
				if(flag) {
					// 搜索时分页重置
					this.total = 0	
					this.currentPage = 1
				}
				this.$axios.post('/config/list', {
					name: this.keywords
				}).then(res => {
          res = res.data
          console.log(res)
					if (res.code === 200) {
						this.total = res.data.length
						this.allData = res.data
						this.cargos = this.allData.slice(
						   (this.currentPage - 1) * this.pageSize,
							this.currentPage * this.pageSize
						)
					
					} else {
						this.$message.error(res.msg)
					}
				})
			},
			rowDelete(row) {
				this.$axios.post('/config/delete', row).then(res => {
					res = res.data
					if (res.code === 200) {
						this.getList()
						this.$message.success('删除成功')
					} else {
						this.$message.error(res.msg)
					}
				})
			},
			edit: function(row) {
				this.dialogFormVisible = true
				this.$nextTick(() => {
					Object.assign(this.form, row)
				})
			},
			save() {
				const data = {
					...this.form
				}
				if (this.form.id) {
					this.$axios.post('/config/update', data).then(res => {
						res = res.data
						if (res.code === 200) {
							this.getList()
							this.$message.success('操作成功！')
						} else {
							this.$message.error(res.msg)
						}
					})
				} else {
					this.$axios.post('/config/save', data).then(res => {
						res = res.data
						if (res.code === 200) {
							this.getList()
							this.$message.success('操作成功！')
						} else {
							this.$message.error(res.msg)
						}
					})
				}
				this.dialogFormVisible = false
				this.resetData()
			},
			// 调用接口更新，更新失败将enable值置为先前的值
			changeEnable(row) {
				this.$axios.post('/config/update', row).then(res => {
					res = res.data
					if (res.code === 200) {
						this.getList()
						this.$message.success('操作成功！')
					} else {
						row.enable = !row.enable
						this.$message.error(res.msg)
					}
				})
			},
			handleAvatarSuccess(res, file) {
				this.form.value = res.data
			},
			beforeAvatarUpload(file) {
				const isJPG = file.type.indexOf('image/') != -1

				if (!isJPG) {
					this.$message.error('仅能上传图片!')
				}
				return isJPG;
			},
			resetData() {
				this.form.id = ''
        this.form.name = ''
        this.form.value = ''

        this.$refs.form.resetFields()
			}
		}
	}
</script>

<style>
.softdiv {
  width: 98%;
  height: 90%;
  border: 1px #e3e3e3 solid;
  margin: 10px 10px;
}
	.avatar-uploader .el-upload {
		border: 1px dashed #d9d9d9;
		border-radius: 6px;
		cursor: pointer;
		position: relative;
		overflow: hidden;
		float: left;
	}

	.avatar-uploader .el-upload:hover {
		border-color: #409EFF;
	}

	.avatar-uploader-icon {
		font-size: 28px;
		color: #8c939d;
		width: 178px;
		height: 178px;
		line-height: 178px;
		text-align: center;
	}

	.avatar {
		width: 178px;
		height: 178px;
		display: block;
	}
</style>

<style scoped>
	.list_img {
		width: 100px;
		height: 100px;
	}
</style>
