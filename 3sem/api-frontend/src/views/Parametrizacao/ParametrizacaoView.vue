<template>
    <div class="row mt-4">
        <div class="col-12">
            <h3>Parametros</h3>
            <hr>
        </div>
    </div>
    <div class="row mt-4">
        <div class="col-6">
            <label>Inicio da hora Noturna</label>
            <input v-model="request.inicioHorarioNoturno" type="time" class="form-control">
        </div>
        <div class="col-6">
            <label>Fim da hora Noturna</label>
            <input v-model="request.fimHorarioNoturno" type="time" class="form-control">
        </div>
    </div>
    <div class="row mt-4">
        <div class="col-4">
            <label >Dia de Inicio do Mes</label>
            <input v-model="request.dataInicioPagamento" type="number" class="form-control">
        </div>
        <div class="col-4">
            <label>Dia de Fechamento do Mes</label>
            <input v-model="request.dataFimPagamento" type="nubmer" class="form-control">
        </div>
    </div>
    <br />
    <div class="row mt-4">
        <div class="col-12">
            <h3>Verbas</h3>
            <hr>
        </div>
    </div>
    <div class="row">
        <div class="col-4 d-flex justify-content-center align-items-center">
            <h5>HE Diurno (%)</h5>
        </div>
        <div class="col-4">
            <label>1601</label>
            <input v-model="request.v1601" type="number" class="form-control">
        </div>
        <div class="col-4">
            <label>1602</label>
            <input v-model="request.v1602" type="number" class="form-control">
        </div>
    </div>
    <div class="row mt-2">
        <div class="col-4 d-flex justify-content-center align-items-center">
            <h5>HE Noturnas (%)</h5>
        </div>
        <div class="col-4">
            <label>3000</label>
            <input v-model="request.v3000" type="number" class="form-control">
        </div>
        <div class="col-4">
            <label>3001</label>
            <input v-model="request.v3001" type="number" class="form-control">
        </div>
    </div>
    <div class="row mt-2">
        <div class="col-4 d-flex justify-content-center align-items-center">
            <h5>Adicional Noturno (%)</h5>
        </div>
        <div class="col-8">
            <label>1809</label>
            <input v-model="request.v1809" type="number" class="form-control">
        </div>
    </div>
    <div class="row mt-2">
        <div class="col-4 d-flex justify-content-center align-items-center">
            <h5>Sobreaviso (%)</h5>
        </div>
        <div class="col-8">
            <label>3016</label>
            <input v-model="request.v3016" type="number" class="form-control">
        </div>
    </div>
    <div class="row mt-5 d-flex justify-content-end">
        <div class="col-3 justify-content-end d-flex">
            <button type="button" v-on:click="save()" class="btn btn-outline-primary">
                Salvar
            </button>
        </div>
    </div>
</template>
  
<script lang="ts">
import http from '@/services/http';
import { Options, Vue } from 'vue-class-component';

@Options({
    components: {
    }
})
export default class ParametrizacaoView extends Vue {
    request = {
        id: 0,
        dataInicioPagamento: '',
        dataFimPagamento: '',
        inicioHorarioNoturno: '',
        fimHorarioNoturno: '',
        v1601:0,
        v1602:0,
        v3000:0,
        v3001:0,
        v1809:0,
        v3016:0,
    };

    created(): void {
        this.getParameters();
    }

    getParameters(){
        http.get('/parametrizacao')
            .then(x => {
                this.request = x.data[0]
            });
    }

    save(){
        http.put('/parametrizacao/'+this.request.id, this.request)
            .then(_ => alert('Parametros salvos com sucesso!'))
            .then(_ => this.getParameters())
            .catch(_ => alert('Algo deu errado. Tente novamente mais tarde.'));
    }
};
</script>
  
<style scoped>
</style>