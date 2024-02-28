<template>
    <div class="row mt-4 mb-4">
      <div class="col-12">
      <h3>RELATÓRIO </h3>
      <hr>
      </div>
    </div>
    <div class="row relatorio">
        <div class="col-3">
            <p class="bold">Data Inicio</p>
            <input class="form-control" id="party" type="date" name="partydate" v-model="relatorio.datainicio" />
       </div> 
       <div class="col-3">
        <p class="bold">Data Fim</p>
        <input class="form-control" id="party" type="date" name="partydate" v-model="relatorio.datafim" />
       </div> 
       <div class="col-4" style="margin-top:40px;"><button @click="gerar()" type="button"
        class="btn btn-outline-primary"> Gerar relatório </button></div>
        
    </div>


</template>

<script lang="ts">
import http from "@/services/http";
import { Options, Vue } from "vue-class-component";
export default class Relatorio extends Vue {
  
  relatorio: any = {
    datainicio:'',
    datafim:''


  }
  async gerar() {

    try {
    const response = await http.get('/relatorio',{responseType:'arraybuffer', params:this.relatorio});
    var blob = new Blob([response.data]);
    var link = document.createElement('a');
    link.href = URL.createObjectURL(blob);
    link.download ='extrato_horas.xls';
    link.click()

    
  } catch (error) {
    console.error('Erro na solicitação de relatório:', error);
    alert('Erro ao gerar relatório. Tente novamente.');
  }
}
}
</script>

<style scoped>
.party {
  border: 1px solid #000;
  
}


.bold {
  font-weight: 700;
  width: 100%;
}

.relatorio {
  margin-bottom: 75px;
}
</style>