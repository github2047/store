
<script >
export default {
  data(){
    return{

    }
  },
  created() {
    const loading=document.getElementById("loading")
    const app=document.getElementById("app")
    document.body.removeChild(app);
    if(loading!=null){
      document.body.removeChild(loading);
      document.body.appendChild(app);
    }
  }
}
</script>

<template>
  <router-view/>
</template>

<style scoped>
/*#app {*/
/*  font-family: Avenir, Helvetica, Arial, sans-serif;*/
/*  -webkit-font-smoothing: antialiased;*/
/*  -moz-osx-font-smoothing: grayscale;*/
/*  !*text-align: center;*!*/
/*  color: #2c3e50;*/
/*  margin-top: 60px;*/
/*}*/
</style>
