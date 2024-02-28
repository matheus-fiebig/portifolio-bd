<template>
  <div class="row mt-4">
    <div class="col-12">
      <h3>CONTROLE | CR</h3>
      <hr>
    </div>
  </div>

  <div class="d-flex mt-3 justify-content-end mb-3">
    <button class="btn btn-outline-primary" @click="newCr()"> Cadastrar CR </button>
  </div>
  <div class="row mt-4">
    <table class="table table-responsive no-wrap-table">
      <thead>
        <tr>
          <th scope="col" class="text-left">Cod</th>
          <th scope="col" class="text-left">Sigla</th>
          <th scope="col" class="text-left ">Nome</th>
          <th scope="col" class="text-center">Status</th>
          <th scope="col" class="text-center">Ações</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="cr in crs" :key="cr.id">
          <td>{{ cr['codigo'] }}</td>
          <td>{{ cr['sigla'] }}</td>
          <td>{{ cr['nome'] }}</td>
          <td style="width: 100px">
            <div class="pill text-center text-wrap" :class="{
              approved: cr['ativo'] === true,
              inativo: cr['ativo'] === false,
            }">
              {{ cr.ativo ? 'Ativo' : 'Inativo' }}
            </div>
          </td>
          <td class="text-center">
            <button class="btn btn-link" @click="editCr(cr)"><i class="fa fa-pencil" aria-hidden="true"></i></button>
            <button class="btn btn-link" @click="editUserCr(cr['id'])"><i class="fas fa-id-card"
                aria-hidden="true"></i></button>

            <button class="btn btn-link" @click="inativarCr(cr['id'])"><i class="fa fa-trash"
                aria-hidden="true"></i></button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
  <ModalCrUsuario :id-cr="idCr"></ModalCrUsuario>
  <ModalCadastroCrView @update-table="loadAllCr" :id-cr-prop="idCr" :codigo-cr-prop="codigoCr" :nome-cr-prop="nomeCr"
    :sigla-cr-prop="siglaCr"></ModalCadastroCrView>
</template>
<script lang="ts">
import http from '@/services/http';
import { defineComponent } from 'vue';
import ModalCadastroCrView from '@/views/Cr/ModalCadastroCr/ModalCadastroCrView.vue';
import ModalCrUsuario from '@/views/Cr/ModalCrUsuario/ModalCrUsuario.vue';
interface cr {
  id: number,
  idCr: number,
  codigoCr: string,
  siglaCr: string,
  nomeCr: string,

}
export default defineComponent({
  name: "ControleCrView",
  components: {
    ModalCadastroCrView,
    ModalCrUsuario
  },
  data() {
    return {
      crs: [] as Array<any>,
      idCr: 0,
      codigoCr: '',
      siglaCr: '',
      nomeCr: '',
    }
  },
  created() {
    this.loadAllCr();
  },
  methods: {
    setCrDetails(crDetails: any) {
      this.idCr = crDetails.id;
      this.codigoCr = crDetails.codigo;
      this.siglaCr = crDetails.sigla;
      this.nomeCr = crDetails.nome;
    },

    newCr() {
      var modal = document.getElementById("cadastro-cr-modal")!;
      modal.style.display = "block";
    },
    editUserCr(id: number) {
      this.idCr = id;
      var modal = document.getElementById("cr-usuario-modal")!;
      modal.style.display = "block";
    },
    editCr(cr: any) {
      this.setCrDetails(cr);

      // Show the modal
      var modal = document.getElementById("cadastro-cr-modal")!;
      modal.style.display = "block";
    },

    loadAllCr() {
      http.get('/cr')
        .then((x: any) => this.crs = x.data)
        .catch(err => alert('Algo deu errado, tente novamente mais tarde.'));
    },
    async inativarCr(id: number) {
      const cr = this.crs.find((cr) => cr.id === id);

      if (cr) {

        try {
          if (cr.ativo) {
            await http.delete(`/cr/${cr.id}`);
            cr.ativo = false;
            alert('CR marcado como Inativo');
          } else {
            alert('CR já está Inativo');
          }
        } catch (error) {
          alert('Algo deu errado, tente novamente mais tarde.');
        }
      } else {
        alert('CR não encontrado');
      }
    }
  }
})
</script>

<style scoped>
.pill {
  border-radius: 30px;
  width: 140px;
  color: white;

  &.approvedGestor {
    background-color: #fac02d;
  }

  &.approved {
    background-color: #26fc29;
  }

  &.waiting {
    background-color: gainsboro;
  }

  &.reproved,
  &.canceled {
    background-color: red;
  }
}
</style>
