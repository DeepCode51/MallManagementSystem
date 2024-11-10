<template>
  <div class="softdiv">
    <div>
      <el-row type="flex" justify="space-between">
        <el-col :span="2">

        </el-col>

        <el-col :span="6">

        </el-col>
      </el-row>
      <el-table :data="cargos" border>
        <el-table-column prop="ask" align="center" label="新消息" >
        </el-table-column>
        <el-table-column prop="" align="center" label="状态" >
          <template slot-scope="scope">
            <el-tag size="medium">未回复</el-tag>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="100">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="dialogVisible = true;form=scope.row; getChatList(scope.row)">回复</el-button>
<!--            <el-button @click="rowDelete(scope.row)" type="text" size="small">删除</el-button>-->
          </template>
        </el-table-column>
      </el-table>
      <el-pagination backgroundL layout="prev, pager, next"
                     @current-change="handleCurrentChange"
                     :current-page.sync="currentPage"
                     :page-size="10"
                     :total="total">
      </el-pagination>
      <el-dialog
          title="在线客服"
          :visible.sync="dialogVisible"
          width="40%">
        <el-form style="height: 300px" ref="ruleForm" label-width="0">
          <div v-bind:key="item.id" v-for="item in dataList">
            <el-row>
              <div v-if="item.ask" class="left-content">
                <el-alert class="text-content" :title="item.ask" :closable="false" type="success"></el-alert>
              </div>
              <div v-else class="right-content">
                <el-alert class="text-content" :title="item.reply" :closable="false" type="warning"></el-alert>
              </div>
            </el-row>
            <div class="clear-float"></div>
          </div>
        </el-form>
        <span slot="footer" class="dialog-footer">
                    <div class="btn-input">
            <el-input style="flex: 1;margin-right: 10px;" v-model="reply" placeholder="回复" clearable></el-input>
            <el-button type="primary" @click="onSubmit">回复</el-button>
          </div>
          <!--    <el-button @click="dialogVisible = false">取 消</el-button>-->
          <!--    <el-button type="primary" @click="dialogVisible = false">确 定</el-button>-->
  </span>
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
        dialogVisible: false,
        reply: '',
        dataList: [],
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
					operdescribe: '',
					shop: '',
					image: '',
					price: '',
					commodityType: '',
					stock: 0
				}
			}
		},
		computed: {
			// cargos() {
			//   return this.$store.getters.getCargos
			// }
		},
		methods: {
      // 提交
      onSubmit() {
        if (!this.reply) {
          this.$message.error('请输入内容', {
            time: 2000,
            icon: 5
          });
          return
        }
        var params = {
          ...this.form,
          ...{reply: this.reply}
        }
        params.id = ''
        params.addtime = ''
        params.ask = ''
        this.$axios.post('/chat/save', params).then(res => {
          this.getList();
          this.getChatList(this.form);
          // this.dialogVisible = false
        });
        this.reply="";
      },
      getChatList(row) {
        this.$axios.get('/chat/info/'+row.userid).then(res => {
          console.log(res)
          this.dataList = res.data.data;
        })
      },
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
				this.$axios.get('/chat/page', {
					name: this.keywords
				}).then(res => {
					res = res.data
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
				this.$axios.post('/chat/delete', row).then(res => {
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
					this.$axios.post('/chat/update', data).then(res => {
						res = res.data
						if (res.code === 200) {
							this.getList()
							this.$message.success('操作成功！')
						} else {
							this.$message.error(res.msg)
						}
					})
				} else {
					this.$axios.post('/chat/insert', data).then(res => {
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
				this.$axios.post('/chat/update', row).then(res => {
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
				this.form.image = res.data
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
				this.form.info = ''
				this.form.idea = ''
				this.form.color = ''
				this.form.price = ''
				this.form.type = 3
				this.form.enable = true
				this.form.intro = ''
				this.form.imgsrc = ''
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
  .chat-form {
    height: 100%;
    display: flex;
    flex-direction: column;
  }
  .chat-content {
    overflow-y: scroll;
    border: 1px solid #eeeeee;
    margin: 0;
    padding: 0;
    width: 100%;
    flex: 1;
  }
  .left-content {
    float: left;
    margin-bottom: 10px;
    padding: 10px;
  }
  .right-content {
    float: right;
    margin-bottom: 10px;
    padding: 10px;
  }
  .btn-input {
    margin-left: 0px;
    display: flex;
    width: 100%;
    padding: 10px 12px;
    box-sizing: border-box;
  }
</style>
