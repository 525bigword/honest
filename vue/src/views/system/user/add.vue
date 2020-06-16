<template>

</template>

<script>
export default {
    data() {
      return {
          visible:true,
        form: {
          name: '',
          region: '',
          date1: '',
          date2: '',
          delivery: true,
          type: [],
          resource: '',
          desc: ''
        }
      }
    },
    methods: {
      onSubmit() {
        console.log('submit!');
      },
      init(id){
          this.form.id=id||0
          this.visible=true
          this.$nextTick(()=>{
              console.log("弹出")
          })
      }
    }
  }
</script>

<style>

</style>