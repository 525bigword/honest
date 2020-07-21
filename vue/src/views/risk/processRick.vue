<template>
  <div>
    <div class="app-container" :style="{'display':dis}" style="width:100%">
      <el-form :inline="true" class="demo-form-inline">
        <!--查询条件-->
        <div align="center" style="margin-top: 30px">
          <el-form-item label="名称">
            <el-input
              v-model="search.proName"
              class="search_name"
              placeholder="请输入流程风险名称">
            </el-input>
          </el-form-item>
          <el-form-item label="年份">
            <el-date-picker
              :editable="false"
              style="width: 200px"
              v-model="search.proYear"
              type="year"
              format="yyyy"
              value-format="yyyy"
              placeholder="选择年份"
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="风险等级" >
            <el-select style="width: 200px;" v-model="search.proGrade" placeholder="请选择">
              <el-option label="一级风险" value="一级风险"></el-option>
              <el-option label="二级风险" value="二级风险"></el-option>
              <el-option label="三级风险" value="三级风险"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button
              type="primary"
              @click="onSearch()"
              class="el-icon-search">查询
            </el-button>
            <el-button
              type="primary"
              class="el-icon-refresh" plain @click="onrest">重置
            </el-button>
          </el-form-item>
        </div>
        <!--操作按钮-->
        <div>
          <el-form-item>
            <el-button type="primary" class="el-icon-plus" @click="add">新增</el-button>
            <el-button type="primary" class="el-icon-delete" @click="dele">删除</el-button>
          </el-form-item>
        </div>
        <!--数据表格-->
        <el-table
          :data="tableData.slice((currentPage-1)*pageSize,currentPage*pageSize)"
          border
          style="width: 100%" ref="multipleTable">
          <el-table-column type="selection" align="center" width="50px"></el-table-column>
          <el-table-column sortable label="序号"  type="index"  align="center" width="60px"></el-table-column>
          <el-table-column width="100px"
                           align="center"
                           prop="proYear"
                           label="流程年份"
          >
          </el-table-column>
          <el-table-column
            prop="proName"
            label="流程风险名称" align="center">
            <template slot-scope="scope">
              <el-tooltip content="点击查看详情或修改" placement="right" effect="dark">
                <a @click="handleEdit(scope.$index, scope.row)"
                   target="_blank"
                   class="buttonText" style="color: #1890ff">{{scope.row.proName}}</a>
              </el-tooltip>
            </template>
          </el-table-column>
          <!-- <el-table-column align="center" prop="proBranch" label="分管领导">
           </el-table-column>-->
          <el-table-column align="center"
                           prop="proAccessoryName"
                           label="流程图名称">
          </el-table-column>
          <el-table-column width="120px"
                           align="center"
                           prop="proGrade"
                           label="风险等级">
          </el-table-column>
          <el-table-column align="center" width="120px"
                           prop="proCreateName"
                           label="创建人">
          </el-table-column>
          <el-table-column align="center" width="120px"
                           prop="proCreateTime"
                           label="创建时间"
                           :formatter="dateFormat">
          </el-table-column>
        </el-table>
        <!--分页-->
        <div class="block" align="center">
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage4"
            :page-sizes="[5, 10, 20, 50,100]"
            layout="total, sizes, prev, pager, next, jumper"
            :page-size="pageSize"
            :total="total">
          </el-pagination>
        </div>
      </el-form>
    </div>
    <!--增加修改页面-->
    <div :style="{'display':dis2}"  style="background-color: lightgray;width:100%;margin-top:-9px">
      <el-main><!--  margin-left:40px; -->
        <el-form 
        ref="dataForm" 
        :rules="rules" 
        :model="rickInfo" 
        label-position="center" 
        label-width="150px" 
        style="width: 100%;">
          <!--操作按钮-->
          <div style="background-color: white;width: 100%;height: 65px;position:fixed; top:50px; left:-1px;z-index:2 ;">
            <el-form-item  style="width:100%;height:30px;margin-left: -60px;margin-top:12px" align="right">
              <el-button type="success"  class="el-icon-top"  @click="submitUser()">保存</el-button>
              <el-button class="el-icon-back" @click="deselect()">返回</el-button>
            </el-form-item>
          </div>
          <div style="background-color: white;margin-top: 25px;z-index:3;">
            <div style="height:20px"></div>
            <h2 v-text="h1Text" align="center"></h2>
            <el-input v-model="rickInfo.proid" placeholder="序号" type="hidden"></el-input>
            <el-row style="margin-left:5%;font-weight: bold;">
              <el-col :span="12" style="width:43%">
                <el-form-item style="font-weight: bold;" label="流程风险名称" prop="proName">
                  <el-input style="width: 100%"  v-model="rickInfo.proName" placeholder="请输入流程风险名称" ></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12" style="width:43%">
                <el-form-item style="font-weight: bold;" label="风险流程年份" prop="proYear">
                  <el-date-picker
                    :editable="false"
                    style="width: 100%"
                    v-model="rickInfo.proYear"
                    type="year"
                    format="yyyy"
                    value-format="yyyy"
                    placeholder="选择年份">
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row style="margin-left:5%;font-weight: bold;">
              <el-col :span="24" >
                <el-form-item style="font-weight: bold;" label="流程风险图" prop="proAccessoryName">
                  <el-upload
                    style="width:715px"
                    class="upload-demo"
                    v-model="rickInfo.proAccessoryName"
                    ref="upload"
                    action="https://localhost:8080/imp/importDuty"
                    :on-remove="fileRemove"
                    :on-change="handleImgChange1"
                    accept=".jpg,.png,.gif"
                    :file-list="fileList"
                    :limit="1"
                    list-type="picture"
                    :auto-upload="false"
                  >
                    <el-button slot="trigger" class="el-icon-upload" size="small" type="primary">上传图片</el-button>
                    <span slot="tip"  style="margin-left: 20px">建议上传5M以下的文件，文件类型支持格式：jpg，png，gif</span>
                  </el-upload>
                </el-form-item>
              </el-col>
            </el-row>
            <!--分管领导  proBranch-->
            <!--<el-row style="margin-left:5%;font-weight: bold;" >
              <el-col :span="12" style="width:43%">
                <el-form-item label="分管领导" prop="defaultvalue2">
                  <el-cascader style="width: 350px"
                               placeholder="部门"
                               v-model="userInfo.pdeptid"
                               :props="props2"
                               @change="Change2"
                               :show-all-levels="false"
                               :options="bm"
                  ></el-cascader>
                </el-form-item>
              </el-col>
              <el-col :span="12" style="width:43%">
                &lt;!&ndash;<el-form-item  label="岗位" prop="pinfomationId">&ndash;&gt;
                  <el-select @change="selectSystemChanged" style="width: 350px"  v-model="userInfo.pinfomationid"
                             placeholder="岗位">
                    <el-option
                      v-for="(post) in postList3"
                      :key="post.pid"
                      :label="post.pname"
                      :value="post.pid"
                    ></el-option>
                  </el-select>
              </el-col>
            </el-row>-->
            <el-row style="margin-left:5%;font-weight: bold;" >
              <el-col :span="12" style="width:43%">
                <el-form-item label="风险发生可能性L值"  prop="pLvalue">
                  <el-select v-model="rickInfo.pLvalue" placeholder="风险发生可能性L值"
                             style="width: 100%" class="filter-item" @change="changValueRisk()">
                    <el-option v-for="item in lValue" :key="item.lId" :label="item.lName" :value="item.lId"/>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12" style="width:43%">
                <el-form-item label="风险产生严重性C值"  prop="pCvalue">
                  <el-select v-model="rickInfo.pCvalue" placeholder="风险产生严重性C值"
                             style="width: 100%" class="filter-item" @change="changValueRisk()">
                    <el-option v-for="item in cValue" :key="item.cId" :label="item.cName" :value="item.cId"/>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row style="margin-left:5%;font-weight: bold;">
              <el-col :span="12" style="width:43%">
                <el-form-item label="流程风险值D值" prop="pDvalue">
                  <el-input placeholder="系统自动算出" v-model="rickInfo.pDvalue"
                            style="width: 100%" disabled="disabled"/>
                </el-form-item>
              </el-col>

              <el-col :span="12" style="width:43%">
                <el-form-item label="风险等级" prop="proGrade">
                  <el-select v-model="rickInfo.proGrade" placeholder="系统计算风险值后自动选择"
                             style="width: 100%" class="filter-item"
                             disabled="disabled">
                    <el-option v-for="item in riskGrades" :key="item.rgId" :label="item.rgName" :value="item.rgId"/>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row style="margin-left:5%;font-weight: bold;">
              <el-col :span="24">
                <el-form-item style="font-weight: bold;" label="流程风险描述" prop="proInfomation">
                  <!--<quill-editor class="editor"  style="height:500px;width:90%;"
                                ref="myQuillEditor"
                                v-model="rickInfo.proInfomation"></quill-editor>-->
                  <el-input v-model="rickInfo.proInfomation" :rows="5" style="width: 84%"
                            placeholder="请输入流程风险描述..." type="textarea"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row style="margin-left:5%;font-weight: bold;">
              <el-col :span="24">
                <el-form-item style="font-weight: bold;" label="预防和控制措施" prop="proMeasures">
                  <el-input v-model="rickInfo.proMeasures" :rows="10" style="width: 84%"
                            placeholder="请输入预防和控制措施..." type="textarea"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <!--<el-form-item style="margin-top:30px"></el-form-item>-->
          </div>
        </el-form>
      </el-main>

    </div>
  </div>
</template>

<script>
  import {add, del, findBy, impFile, list, update} from '@/api/risk/processRick'
  import {mapGetters} from 'vuex'
  import qs from 'qs'
  import moment from 'moment'

  export default {
    computed: {
      ...mapGetters([
        'userId',
        'nickname'
      ])
    },
    created() {
      this.initList();
      /* this.getAllPost();
       this.getAllMechanism()*/
    },
    methods: {
      // 生成岗位风险编号
      getRiskId() {
        // 获得uuid(随机数)
        var s = [];
        var hexDigits = "0123456789abcdef";
        for (var i = 0; i < 36; i++) {
          s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
        }
        s[14] = "4"; // bits 12-15 of the time_hi_and_version field to 0010
        s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1); // bits 6-7 of the clock_seq_hi_and_reserved to 01
        s[8] = s[13] = s[18] = s[23] = "-"
        var uuid = s.join("")
        // 岗位风险编号前缀
        let letStr = "PRN" + Moment(new Date()).format("YYYY-MM-DD-")
        // 返回岗位风险编号
        return letStr + uuid
      },
      //动态计算风险值
      changValueRisk() {
        let lValue;
console.log('this.rickInfo.pLvalue',this.rickInfo.pLvalue)
        console.log('this.rickInfo.pCValue',this.rickInfo.pCvalue)
        if(this.rickInfo.pLvalue===undefined){
          lValue=undefined
        }else {
          lValue = this.rickInfo.pLvalue.substring(this.rickInfo.pLvalue.indexOf("(") + 1,
            this.rickInfo.pLvalue.indexOf(")"))
        }
        let cValue;

        if(this.rickInfo.pCvalue===undefined) {
          cValue=undefined
        }else {
          cValue=this.rickInfo.pCvalue.substring(this.rickInfo.pCvalue.indexOf("(") + 1,
            this.rickInfo.pCvalue.indexOf(")"))
        } console.log('lvalue',lValue)
        console.log('cvalue',cValue)
        let s;
        if(this.rickInfo.pLvalue!=undefined&&this.rickInfo.pCvalue!=undefined){
          s= this.rickInfo.pDvalue = parseInt(lValue) * parseInt(cValue)
        }

        //console.log("s----"+s)
        if (s >= 240) {
          this.rickInfo.proGrade = this.riskGrades[0].rgId
        }else if (s < 240&& s >=90) {
          this.rickInfo.proGrade = this.riskGrades[1].rgId
        }
        else if (s < 90) {
          this.rickInfo.proGrade = this.riskGrades[2].rgId
        }

      },
      // 设置风险值的默认选中和默认值
      defaultSelect() {
        this.rickInfo.pLvalue = this.lValue[0].lId
        this.rickInfo.pCvalue = this.cValue[0].cId
        let lValue=this.rickInfo.pLvalue.substring(this.rickInfo.pLvalue.indexOf("(")+1,
          this.rickInfo.pLvalue.indexOf(")"))
        let cValue=this.rickInfo.pCvalue.substring(this.rickInfo.pCvalue.indexOf("(")+1,
          this.rickInfo.pCvalue.indexOf(")"))
        this.rickInfo.pDvalue = parseInt(lValue) * parseInt(cValue)
        this.rickInfo.proGrade = this.riskGrades[0].rgId
      },
      fileRemove(file, fileList) {
        this.file = {};
        this.rickInfo.proAccessoryName = '';
        this.rickInfo.proAccessory = '';
        this.fileList = []
      },
      handleImgChange1(file, fileList, name) {
        const isLt2M = file.size / 1024 < 5000;
        if (!isLt2M) {
          this.$message({
            showClose: true,
            message: "文件不能超过5M",
            type: "warning"
          });
          if (fileList.length == 2) {
            this.fileList = fileList.slice(0, 1);
          } else {
            this.fileList = fileList.slice(1);
          }
        } else {
          this.file = file.raw;
          if (fileList) {
            this.fileList = fileList.slice(-1);
          }
        }
        this.formData = new FormData();
        this.formData.append("file", this.file);
        this.rickInfo.proAccessoryName = this.file.name;
        this.$refs['dataForm'].validate((valid) => {})
      },
      getAllMechanism() {
        getAllMechanism().then(response => {
          this.mechanismList = response.list
        })
      },
      change() {
        this.$forceUpdate()
      },
      getAllPost() {
        getAllPost().then(response => {
          this.postList = response.list
        })
      },
      stateFormat(row, column) {
        if (row.pstatus === 1) {
          return '正常'
        } else {
          return '禁用'
        }
      },
      postFormat(row, column) {
        for (var i = 0; i < this.postList.length; i++) {
          if (row.pinfomationId == this.postList[i].pid) {
            return this.postList[i].pname;
          }
        }

      },
      deptFormat(row, column) {
        for (var i = 0; i < this.mechanismList.length; i++) {
          if (row.pdeptId == this.mechanismList[i].mid) {
            return this.mechanismList[i].mechanismName;
          }
        }

      },
      dateFormat(row, column) {
        var date = row[column.property];
        return moment(date).format("YYYY-MM-DD")
      },
      onrest() {
        this.search = {
          proName: undefined,
          proYear: undefined,
          proGrade:undefined
        }
      },
      initList() {
        list().then(response => {
          this.tableData = response.list
          this.total = response.list.length
        })

      },
      // 修改
      handleEdit(index, row) {
        this.dialogTitle = '修改';
        this.h1Text="修改流程风险"
        this.rickInfo = row;
        if(row.proAccessoryName!==''&&row.proAccessoryName!==null){
          this.fileList=[{name:row.proAccessoryName,url:this.virtualimgIp+row.proAccessory}];
        }
        this.filename=row.proAccessoryName
        /*if (row.pstatus === 1) {
          this.userInfo.pstatus = '正常'
        }
        else {
          this.userInfo.pstatus = '禁用'
        }*/
        this.iconFormVisible = true;
        this.rowIndex = index;
        this.dis='none'
        this.dis2='inline-block'
        this.$refs['dataForm'].clearValidate()
      },
      //按条件查询
      onSearch() {
        if (this.search.proYear != undefined) {
          var dateee = new Date(this.search.proYear).toJSON();
          this.search.proYear = new Date(+new Date(dateee) + 8 * 3600 * 1000).toISOString().split("T")[0];
        }

        let postData = qs.stringify({
          proName: this.search.proName,
          proYear: this.search.proYear,
          proGrade:this.search.proGrade
        });
        this.listLoading = true
        findBy(postData).then((response) => {
          this.currentPage = 1
          this.tableData = response.list
          this.total = response.list.length
          this.listLoading = false
        })
      },
      dele() {
        var data = this.$refs.multipleTable.selection;
        if (JSON.stringify(data) == '[]') {
          this.$notify({
            title: '温馨提示',
            message: '请至少选择一行进行删除',
            type: 'success',
            duration: 2000
          })
        }
        else {
          var bool = confirm("是否确认删除？");
          if (!bool) {
            return;
          }
          let postData = qs.stringify({
            test: JSON.stringify(data)
          });
          console.log("删除数据" + postData);
          del(postData).then((response) => {
            this.initList();
            this.$notify({
              title: '成功',
              message: response.message,
              type: 'success',
              duration: 2000
            })
          })
        }
      },
      selectClassfy(data) {
        this.userInfo.deptName = data.label;
        this.isShowSelect = false;
      },
      changeSelectTree() {
        this.isShowSelect = true;
      },
      hideParentClick(e) {
        var isOther = e.relatedTarget == null || e.relatedTarget.closest("div.el-tree")
          == null || e.relatedTarget.closest("div.el-tree").id != "floatTree";

        if (isOther) {
          this.isShowSelect = false;
        } else {
          e.target.focus();
        }
      },
      handleSizeChange(size) {
        this.pageSize = size;
      },
      handleCurrentChange(currentPage) {
        this.currentPage = currentPage;
      },
      //新增
      add() {
        this.$refs['dataForm'].clearValidate()
        this.dialogTitle = '增加';
        this.h1Text="添加流程风险"
        this.rickInfo = {};
        this.iconFormVisible = true;
        // 设置风险值的默认选中和默认值
        //this.defaultSelect()
        this.dis='none'
        this.dis2='inline-block'
        this.fileList=[];
      },
      //弹窗取消
      deselect() {
        this.dis='inline-block'
        this.dis2='none'
        this.rickInfo={}
        this.fileList=[];
        this.$refs['dataForm'].clearValidate()
      },
      // 弹窗确定
      submitUser() {
        //alert("this.rickInfo.proAccessoryName:"+this.rickInfo.proAccessoryName)
        this.$refs['dataForm'].validate((valid) => {
          // 所有的校验都通过
          if (valid) {
            if (this.dialogTitle === '增加') {
              impFile(this.formData).then(response => {
                console.log(response)
                if(response===undefined) {

                }else
                {
                  this.rickInfo.proAccessory = response.dFile;
                }
                /*var dateee = new Date(this.rickInfo.proYear).toJSON();
            var date = new Date(+new Date(dateee) + 8 * 3600 * 1000).toISOString().replace(/T/g, " ").replace(/\.[\d]{3}Z/, "");
            this.rickInfo.proYear = date;*/

                let postData = {
                  proid: this.rickInfo.proid,
                  proName: this.rickInfo.proName,
                  proYear: this.rickInfo.proYear,
                  proBranch: this.rickInfo.proBranch,
                  proAccessory: this.rickInfo.proAccessory,
                  proAccessoryName: this.rickInfo.proAccessoryName,
                  proInfomation: this.rickInfo.proInfomation,
                  proMeasures: this.rickInfo.proMeasures,
                  proGrade: this.rickInfo.proGrade,
                  proCreateTime: this.rickInfo.proCreateTime,
                  proCreateId: this.rickInfo.proCreateId,
                  proCreateName: this.rickInfo.proCreateName,
                  proStatus: this.rickInfo.proStatus
                };
                console.log(postData)
               // console.log("postData:" + postData)
                //新增
                add(postData).then((response) => {
                  this.iconFormVisible = false;
                  clearInterval(this.timer)
                  this.initList();
                  this.$notify({
                    title: '成功',
                    message: response.message,
                    type: 'success',
                    duration: 2000
                  })
                  console.log(postData)
                  this.deselect()
                  this.fileList=[];
                })
              })

            }
            if (this.dialogTitle === '修改') {
              //console.log("准备执行修改方法")
              /*if (this.filename !==this.rickInfo.proAccessoryName) {*/
                impFile(this.formData).then(response => {
                  this.rickInfo.proAccessory = response.dFile;
                  console.log("response.dFile;"+response.dFile)
                  var dateee = new Date(this.rickInfo.proYear).toJSON();
                  var date = new Date(+new Date(dateee) + 8 * 3600 * 1000).toISOString().replace(/T/g, " ").replace(/\.[\d]{3}Z/, "");
                  this.rickInfo.proYear = date;

                  let postData = qs.stringify({
                    proid: this.rickInfo.proid,
                    proName: this.rickInfo.proName,
                    proYear: this.rickInfo.proYear,
                    proBranch: this.rickInfo.proBranch,
                    proAccessory: this.rickInfo.proAccessory,
                    proAccessoryName: this.rickInfo.proAccessoryName,
                    proInfomation: this.rickInfo.proInfomation,
                    proMeasures: this.rickInfo.proMeasures,
                    proGrade: this.rickInfo.proGrade,
                    proCreateTime: this.rickInfo.proCreateTime,
                    proCreateId: this.rickInfo.proCreateId,
                    proCreateName: this.rickInfo.proCreateName,
                    proStatus: this.rickInfo.proStatus
                  });
                  console.debug(postData)
                  update(postData).then((response) => {
                    this.iconFormVisible = false;
                    clearInterval(this.timer)
                    this.initList();
                    this.$notify({
                      title: '成功',
                      message: response.message,
                      type: 'success',
                      duration: 2000
                    })
                    this.deselect()
                    this.fileList=[];
                  })
                })
              /*}else{
                var dateee = new Date(this.rickInfo.proYear).toJSON();
                var date = new Date(+new Date(dateee) + 8 * 3600 * 1000).toISOString().replace(/T/g, " ").replace(/\.[\d]{3}Z/, "");
                this.rickInfo.proYear = date;
                let postData = qs.stringify({
                  proid: this.rickInfo.proid,
                  proName: this.rickInfo.proName,
                  proYear: this.rickInfo.proYear,
                  proBranch: this.rickInfo.proBranch,
                  proAccessory: this.rickInfo.proAccessory,
                  proAccessoryName: this.rickInfo.proAccessoryName,
                  proInfomation: this.rickInfo.proInfomation,
                  proMeasures: this.rickInfo.proMeasures,
                  proGrade: this.rickInfo.proGrade,
                  proCreateTime: this.rickInfo.proCreateTime,
                  proCreateId: this.rickInfo.proCreateId,
                  proCreateName: this.rickInfo.proCreateName,
                  proStatus: this.rickInfo.proStatus
                });

                update(postData).then((response) => {
                  this.iconFormVisible = false;
                  clearInterval(this.timer)
                  this.initList();
                  this.$notify({
                    title: '成功',
                    message: response.message,
                    type: 'success',
                    duration: 2000
                  })
                  this.deselect()
                })
              }*/
            }
          }
        })
      }

    },
    data() {
      // 自定义风险等级校验规则
      let pGradeCustom=(rule,value,callback)=>{
        if (value === '') {
          callback(new Error('请输入风险L、C值'));
        } else {
          callback();
        }
      }
      return {
        h1Text:'',
        timer: '',
        iconFormVisible: false,
        rickInfo: {},
        userInfo: {},
        dialogTitle: '增加',
        rowIndex: null,
        search: {
          proName: undefined,
          proYear: undefined,
          proGrade:undefined
        },
        currentPage4: 1,
        pageSize: 5,
        total: 0,
        postList: [],
        filename:'',
        mechanismList: [{}],
        currentPage: 1,
        isShowSelect: false,
        tableData: [{
          proid: undefined,
          processId: undefined,
          proName: undefined,
          proYear: undefined,
          proBranch: undefined,
          proAccessory: undefined,
          proAccessoryName: undefined,
          proInfomation: undefined,
          proMeasures: undefined,
          pLvalue:undefined,
          pCvalue:undefined,
          pDvalue:undefined,
          proGrade: undefined,
          proCreateTime: undefined,
          proCreateId: undefined,
          proCreateName: undefined,
          proStatus: undefined,
        }],
        listQuery: {
          page: 1,
          limit: 5
        },
        back: {
          // 添加、修改时绑定的表单数据
          sid: undefined,
          backTitle: "",
          backType: "",
          backAccessoryName: "",
          backAccessory: "",
          fileList: [],
          sysStaff: {
            name: "",
            sid: 0
          },
          sysMechanism: {
            mid: 0,
            mechanismName: ''
          },
          newTime: new Date(),
          dnumId: "",
          status: 1,
          dstatus: "",
          dutyContent: '',
          gettop: '',
          cid: 0
        },
        temp: {
          // 添加、修改时绑定的表单数据
          did: undefined,
          dutyTitle: "",
          dutyType: "",
          bmid: "",
          dutyAccessoryName: "",
          dutyAccessory: "",
          fileList: [],
          dutyContent: "",
          sysStaff: {
            name: "",
            sid: 0
          },
          newTime: new Date(),
          sid: null,
          dnumId: "",
          status: 1,
          dstatus: "",
          options: [],
          value: [],
          tongbao: ''
        },
        dis:'inline-block',
        dis2:'none',
        fileList: [],
        rules: {
          // 校验规则
          //dTitle:  [{ required: true, message: '标题必填', trigger: 'blur' }],
          //dFileName: [{ required: true, message: '请上传文件', trigger: 'change'}]
          proName: [{required:true,message:'流程名称不能为空',trigger:['blur','change']}],
          proYear:[{required:true,message:'执行年份不能为空',trigger:['blur','change']}],
          //proAccessoryName:[{required:true,message:'流程图必须上传',trigger:["change","blur"]}],
          proInfomation: [{required: true, message: "预防和控制措施不能为空", trigger: ["change", "blur"]}],
          proMeasures: [{required: true, message: "预防和控制措施不能为空", trigger: ['change',"blur"]}],
          /*pLvalue:[{required: true, message: "风险发生可能性L值不能为空", trigger:  ["change", "blur"]}],
          pCvalue:[{required: true, message: "风险发生可能性C值不能为空", trigger:  ["change", "blur"]}],*/
          //pDvalue:[{required: true}],
          proGrade:[
            {required:true,message:'请选择流程风险L、C值',trigger:["change", "blur"]},
            {validator:pGradeCustom,trigger:'blur'}
          ]
        },
        riskGrades: [
          {rgId: '一级风险', rgName: '高度风险(一级)'},
          {rgId: '二级风险', rgName: '中度风险(二级)'},
          {rgId: '三级风险', rgName: '一般风险(三级)'}],
        lValue: [
          {lId: '完全可能(10)', lName: '完全可能'},
          {lId: '可能性较大(6)', lName: '可能性较大'},
          {lId: '可能性一般(3)', lName: '可能性一般'},
          {lId: '可能性小(1)', lName: '可能性小'}
        ],
        cValue: [
          {cId: '重大(100)', cName: '重大'},
          {cId: '较大(40)', cName: '较大'},
          {cId: '一般(15)', cName: '一般'},
          {cId: '较轻(7)', cName: '较轻'}
        ]
      }
    }
  }
</script>
