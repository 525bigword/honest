<template>
  <div>
    <div  style="background-color: lightgray;width: 100%;height: 900px" >
      <el-main>      <el-form :inline="true" :model="userInfo" class="demo-form-inline" label-width="250px" :rules="rules" ref="ruleForm">
        <div style="background-color: white;width: 100%;height: 65px" >
          <br/>
          <div align="right" ><el-form-item >
            <el-button type="primary" class="el-icon-edit" align="right" @click="submitUser('ruleForm')" >提交</el-button>
            <el-button type="primary" class="el-icon-refresh" @click="resetForm('ruleForm')">重置</el-button>
          </el-form-item></div></div>
        <br/>
        <div style="background-color: white">
          <el-input v-model="userInfo.lid" placeholder="编号" type="hidden" ></el-input>
          <el-form-item label="信访编号" v-if="false">

            <el-input v-model="userInfo.letterId" placeholder="信访编号" style="width: 830px" ></el-input>
          </el-form-item>
          <el-form-item label="来访人姓名">
            <el-input v-model="userInfo.lcomplainantName" placeholder="请输入来访人姓名" style="width: 400px" ></el-input>
          </el-form-item>
          <el-form-item label="被反映人姓名" prop="lpersonBeReported">
            <el-select v-model="userInfo.lpersonBeReported"  placeholder="请选择被反映人姓名" style="width: 400px">
              <el-option
                v-for="item in optionss"
                :key="item.sid"
                :label="item.name"
                :value="item.sid">
              </el-option>
            </el-select>
          </el-form-item>
          <br/>
          <el-form-item label="来访人部门/单位">
            <el-input v-model="userInfo.ldeptId" placeholder="请输入来访人部门/单位" style="width: 400px" ></el-input>
          </el-form-item>
          <el-form-item label="被反映人部门/单位" prop="lpbrDeptId">
            <el-cascader ref='cascaderUnit' :show-all-levels="false"
                         placeholder="请选择被反映人部门/单位"
                         :props="props"
                         :options="options_cascader"
                         :expandTrigger="'hover'"
                         clearable v-model="userInfo.lpbrDeptId" @change="handleItemChange"  style="width: 400px"></el-cascader>
           <!-- <el-input v-model="userInfo.lpbrDeptId"   placeholder="请选择被反映人部门/单位" style="width: 400px" ></el-input>-->
          </el-form-item>
          <br/>
          <el-form-item label="来访人岗位/职务">
            <el-input v-model="userInfo.lpostId" placeholder="请输入来访人岗位/职务" style="width: 400px" ></el-input>
          </el-form-item>
          <el-form-item label="被反映人岗位/职务" prop="lpbrPostId">
            <el-select v-model="userInfo.lpbrPostId"  placeholder="请选择被反映人岗位/职务" style="width: 400px" @change="change" >
              <el-option
                v-for="item in options"
                :key="item.pid"
                :label="item.pname"
                :value="item.pid">
              </el-option>
            </el-select>
          </el-form-item>
          <br/>
          <el-form-item label="原始信访内容" prop="lcontent">
            <el-input type="textarea" v-model="userInfo.lcontent" placeholder="请输入原始信访内容" style="width: 1065px;":rows="10"  ></el-input>
          </el-form-item>
          <br/>
          <el-form-item label="创建时间"  v-if="false">
            <el-date-picker v-model="userInfo.lcreateTime" placeholder="创建时间" style="width: 300px" type="datetime" disabled="disabled"></el-date-picker>
          </el-form-item>
          <br/>
        </div>
      </el-form></el-main>
    </div>
  </div>
</template>

<script>
  import qs from 'qs'
  import {xfbh,getFileGroup,initdept,initemploy,add,letterreporter} from '@/api/nologin/nologin'
  export default {
    created() {
      letterreporter().then((response)=>{
        this.userInfo.letterId=response.letterid
      })
    },
    methods:{//职务选择变化时
      change(){
        let postdata=qs.stringify({
          ppid:this.userInfo.lpbrPostId
        })
        initemploy(postdata).then((response)=>{

          this.optionss = response.list

        console.log('职务'+JSON.stringify(response))

      })
      },handleItemChange(value){//点击选择时初始化谈话对象和记录人
        var checkedNodes = this.$refs['cascaderUnit'].getCheckedNodes()//选择的值
        /*
        console.log('zz'+ value.join(',')); //全路径value值
         console.log('cc'+this.$refs.cascaderUnit.getCheckedNodes()[0].pathLabels); //全路径label值*/

        if(checkedNodes[0]!=undefined){
          this.userInfo.punit=value
          console.log('checkedNodes label'+checkedNodes[0].label)
          let postdata=qs.stringify({
            mid:checkedNodes[0].value
          })
          initdept(postdata).then((response)=>{

            this.options = response.list

          console.log('职务'+JSON.stringify(response))

        })
        }
        else{
          this.options=[]
        }
        //
        /*       console.log(checkedNodes) // 获取当前点击的节点
                 console.log(checkedNodes[0].data.label) // 获取当前点击的节点的label
                 console.log(checkedNodes[0].pathLabels) // 获取由 label 组成的数组*/
      },//重置
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
//新增提交
      submitUser(formName){
        this.$refs[formName].validate((valid) => {
          if (valid) {

            this.userInfo.lcreateTime = new Date(+new Date() + 8 * 3600 * 1000)

              .toISOString()
              .replace(/T/g, " ")
              .replace(/\.[\d]{3}Z/, "")
            let posdata=qs.stringify({
              letterId:this.userInfo.letterId,
              userName:this.userInfo.userName,
              lComplainantName:this.userInfo.lcomplainantName,
              lPersonBeReported:this.userInfo.lpersonBeReported,
              lDeptId:this.userInfo.ldeptId,
              lPostId:this.userInfo.lpostId,
              lPbrDeptId:this.userInfo.lpbrDeptId[this.userInfo.lpbrDeptId.length-1].toString(),
              puni:this.userInfo.lpbrDeptId.toString(),
              lPbrPostId:this.userInfo.lpbrPostId,
              lContent:this.userInfo.lcontent,
              lCreateName:'匿名',
              lTime: this.userInfo.lcreateTime,
              lCreateTime:this.userInfo.lcreateTime,
              lCreateId:0,
              lStatus:1
            })
            add(posdata).then((response)=>{
              this.$refs[formName].resetFields();
            this.$notify({
              title: '成功',
              message: response.message,
              type: 'success',
              duration: 2000
            })
          })
          } else {
            console.log('error submit!!');
        return false;
      }
      });

      },

    },
    data() {
      return { options_cascader:[],//级联选择器的options属性
        options:[],
        optionss:[],
        props: {
          checkStrictly:true,
          lazy: true,
          lazyLoad (node, resolve) {
            const { level } = node;
            const {data}=node;
            let parentId=data?data.value:'0'
            let postData=qs.stringify({
              parent:parentId
            });
            getFileGroup(postData).then((response)=>{
              console.log('response.list'+JSON.stringify(response.list))
            let nodes = level ===0?[]:[]

            if(response.list&&Array.isArray(response.list)){

              for(let item of response.list){
                nodes.push({
                  value:item.mid,
                  label: item.mechanismName
                });
              }
            }
            // 通过调用resolve将子节点数据返回，通知组件数据加载完成
            resolve(nodes);
          })


          }
        }, rules: {
          lpersonBeReported: [
            { required: true, message: '请选择被反映人姓名', trigger: 'blur' }
          ],
          lpbrDeptId: [
            { required: true, message: '请选择被反映人部门/单位', trigger: 'blur' },
          ],
          lpbrPostId: [
            { required: true, message: '请选择被反映人岗位/职务', trigger: 'blur' }

          ],
          lcontent: [
            { required: true, message: '请输入原始信访内容', trigger: 'blur' }
          ]
        },
        userInfo: {},
        dialogTitle: '增加',
        rowIndex: null,
        search:'',
        besearch:'',
        currentPage4: 1,
        pageSize:10,
        total:0,
        currentPage:1,

        tableData: []
      }
    }
  }
</script>

