<template>
  <div class="row mt-4 mb-4" v-if="nivelDePermissao != 3">
    <div class="col-12">
      <h3>LANÇAMENTO DE HORAS</h3>
      <hr>
    </div>
  </div>

  <div class="row mt-4 mb-4" v-if="nivelDePermissao == 3">
    <div class="col-12">
      <h3>APROVAÇÃO / REPROVAÇÃO </h3>
      <hr>
    </div>
  </div>

  <div class="row lancamento-horas" v-if="nivelDePermissao != 3">
    <div class="col-3">
      <p class="bold">Modalidade</p>
      <select class="form-select" aria-label=".form-select-lg example" v-model="lancamento.modalidade">
        <option v-for="item in listmodal" :key="item.idModalidade" :value="item.idModalidade">{{ item.descricaoModalidade
        }}
        </option>
      </select>
    </div>

    <div class="col-3">
      <p class="bold">CR</p>
      <select class="form-select" aria-label=".form-select-lg example" v-model="lancamento.idCr">
        <option v-for="item in listcr" :key="item.idCr" :value="item.idCr">{{ item.nomeCr }}</option>
      </select>
    </div>
    <div class="col-3">
      <p class="bold">Cliente</p>
      <select class="form-select" aria-label=".form-select-lg example" v-model="lancamento.idCliente">
        <option v-for="item in listcliente" :key="item.idCliente" :value="item.idCliente">{{ item.razaoSocialCliente }}
        </option>
      </select>
    </div>
    <div class="col-3">
      <p class="bold">Projeto</p>
      <input type="text" class="form-control" v-model="lancamento.projeto">
    </div>
    <div class="col-3" style="margin-top:10px">
      <p class="bold">Motivo</p>
      <input type="text" class="form-control" v-model="lancamento.motivo">
    </div>

    <div class="col-3" style="margin-top: 10px;">
      <p class="bold">Inicio</p>
      <input id="party" type="datetime-local" name="partydate" v-model="lancamento.dataHoraInicio" />
    </div>

    <div class="col-3" style="margin-top:10px">
      <p class="bold">Fim</p>
      <input class="button-s" id="party" type="datetime-local" name="partydate" v-model="lancamento.dataHoraFim" />
    </div>
    <div class="col-1" style="margin-top:40px;"><button v-on:click="lancar" type="button"
        class="btn btn-outline-primary">Lançar</button></div>
  </div>
  <TabelaHoras style="margin-top: 100px;" :horas="horasLancadas" :podeGerenciarLancamentos="nivelDePermissao > 1"
    @update-table="obterLancamentos"></TabelaHoras>
</template>

<script lang="ts">
import TabelaHoras from './TabelaHoras/TabelaHoras.vue';
import { Options, Vue } from 'vue-class-component';
import http from "@/services/http";
import { useAuth } from '@/stores/auth';
import { ExtratoHoraLinha } from './TabelaHoras/extrato-hora-linha';


@Options({
  props: ['horasLancadas'],
  components: {
    TabelaHoras
  }
})
export default class LancamentoHorasView extends Vue {
  lancamento: any = {
    projeto: '',
    idCr: 0,
    idUsuario: 0,
    modalidade: 0,
    motivo: '',
    dataHoraInicio: '',
    dataHoraFim: '',
    justificativa: '',
    idCliente: 0
  }

  modal: any = [
    {
      id: '',
      modalidade: '',
    }
  ]

  listmodal: any = [];
  listcr: any = [];
  listcliente: any = [];


  horasLancadas: ExtratoHoraLinha[] = [];
  nivelDePermissao: number = 0;

  created(): void {
    this.obterLancamentos();
    this.ComboboxCr();
    this.ComboboxModalidade();
    this.ComboboxCliente();
  }

  obterLancamentos() {
    const user = useAuth().getUser();

    this.nivelDePermissao = user.permissionLevel;
    http.get('/lancamentoHoras/' + user.id)
      .then(response => this.horasLancadas = response.data)
      .catch(_ => alert('Algo deu errado. Tente novamente mais tarde.'));
  }

  ComboboxCr() {
    const user = useAuth().getUser();
    http.get('/cr/' + user.id)
      .then(response => {
        this.listcr = response.data
      })

  }

  ComboboxModalidade() {
    http.get("/modalidade")
      .then(response => {
        this.listmodal = response.data
      })

  }

  ComboboxCliente() {
    http.get('/cliente').then(Response => {
      this.listcliente = Response.data
    })
  }

  async lancar() {
    try {
      this.lancamento.idUsuario = useAuth().getUser().id;
      await http.post("/lancamentoHoras", this.lancamento);
      alert(`Horas lançadas com sucesso!!!`)
      this.obterLancamentos();
    } catch (error) {
      alert(`Algo deu errado. Tente novamente mais tarde.`)

    }
  }
};
</script>

<style scoped>
.bold {
  font-weight: 700;
  width: 100%;
}

.lancamento-horas {
  margin-bottom: 75px;
}

.form-select {
  width: 0.75;
}

.button-s {
  boder: 1px solid #000;
}
</style>