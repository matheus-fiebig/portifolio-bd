<template>
  <div class="table-horas">
    <table class="table table-responsive no-wrap-table">
      <thead>
        <tr>
          <th scope="col" class="text-left">Modalidade</th>
          <th scope="col" class="text-left">CR</th>
          <th scope="col" class="text-left">Cliente</th>
          <th scope="col" class="text-left">Projeto</th>
          <th scope="col" class="text-left">Inicio</th>
          <th scope="col" class="text-left">Fim</th>
          <th scope="col" class="text-left" v-if="podeGerenciarLancamentos">
            Solicitante
          </th>
          <th scope="col" class="text-left">Motivo do Lançamento</th>
          <th scope="col" class="text-left">
            Justificativa do Gestor/Administrador
          </th>
          <th scope="col" class="text-center">Status</th>
          <th scope="col" class="text-center">
            Ações
          </th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="linha in horas">
          <td>{{ linha.modalidade }}</td>
          <td>{{ linha.cr }}</td>
          <td>{{ linha.cliente }}</td>
          <td>{{ linha.projeto }}</td>
          <td>{{ linha.inicio }}</td>
          <td>{{ linha.fim }}</td>
          <td v-if="podeGerenciarLancamentos">{{ linha.solicitante }}</td>
          <td class="text-center">{{ linha.motivo }}</td>
          <td class="text-center">{{ linha.justificativa ?? "-" }}</td>
          <td>
            <div
              class="pill text-center text-wrap"
              :class="{
                waiting: linha.status == 1,
                approved: linha.status == 2,
                reproved: linha.status == 3,
                canceled: linha.status == 4,
                approvedGestor: linha.status == 5,
              }"
            >
              {{ obterDescricaoStatus(linha.status) }}
            </div>
          </td>
          
          <td v-if="!podeGerenciarLancamentos && linha.status == 1">
            <button class="btn btn-link text-danger" @click="cancelar(linha)">
              <i class="fa fa-window-close" aria-hidden="true"></i> Cancelar
            </button>
          </td>

          <td
            v-if="
              podeGerenciarLancamentos &&
              (linha.status == 1 || (linha.status == 5 && nivelPermissao == 3))
            "
            class="text-center"
          >
            <button class="btn btn-link text-success" @click="aprovar(linha)">
              <i class="fa fa-check" aria-hidden="true"></i>
            </button>
            <button class="btn btn-link text-danger" @click="reprovar(linha)">
              <i class="fa fa-window-close" aria-hidden="true"></i>
            </button>
          </td>
          <td
            v-if="
              (nivelPermissao == 3 && linha.status != 1 && linha.status != 5) ||
              (nivelPermissao == 2 &&
               (linha.status == 5 || linha.status == 3 || linha.status == 2 || linha.status == 4)) ||
                (!podeGerenciarLancamentos  && linha.status != 1)
            "
            class="text-center"
          >
            -
          </td>
        </tr>
      </tbody>
    </table>
  </div>

  <ModalMotivo
    :idLancamento="idLancamentoReprova"
    :statusAtual="statusReprova"
    @update-table="updateTableEvent"
  ></ModalMotivo>
</template>

<script lang="ts">
import { PropType, defineComponent } from "vue";
import { ExtratoHoraLinha } from "./extrato-hora-linha";
import ModalMotivo from "../ModalMotivo/ModalMotivo.vue";
import http from "@/services/http";
import { useAuth } from "@/stores/auth";

export default defineComponent({
  props: {
    podeGerenciarLancamentos: Boolean,
    horas: Array as PropType<ExtratoHoraLinha[]>,
  },
  components: {
    ModalMotivo,
  },
  data() {
    return {
      nivelPermissao: 0,
      idLancamentoReprova: 0,
      statusReprova: 0,
    };
  },
  created() {
    this.nivelPermissao = useAuth().getUser().permissionLevel;
  },
  methods: {
    aprovar(linha: ExtratoHoraLinha): void {
      const horaParaAprovar = {
        idLancamento: linha.id,
        status: linha.status == 1 ? 5 : 2,
        justificativa: null,
      };

      http
        .put("/lancamentoHoras", horaParaAprovar)
        .then((r) => {
          alert("Hora aprovada com sucesso.");
          this.updateTableEvent();
        })
        .catch((err) => {
          alert("Algo deu errado tente novamente mais tarde.");
        });
    },

    updateTableEvent() {
      this.$emit("update-table");
    },

    reprovar(linha: ExtratoHoraLinha): void {
      this.idLancamentoReprova = linha.id;
      this.statusReprova = linha.status;
      const modal = document.getElementById("reprovar-modal")!;
      modal.style.display = "block";
    },

    cancelar(item: ExtratoHoraLinha){
      const horaParaReprovar = {
        idLancamento: item.id,
        status: 4,
        justificativa: 'Cancelado pelo usuario'
      };

      http.put('/lancamentoHoras', horaParaReprovar)
          .then(r => {
              this.$emit('update-table');
              alert('Hora cancelada com sucesso.');
          })
          .catch(err => {
              alert('Algo deu errado tente novamente mais tarde.')
          });
    },

    obterDescricaoStatus(statusId: number) {
      switch (statusId) {
        case 1:
          return "Em Aprovação";
        case 2:
          return "Aprovada";
        case 3:
          return "Reprovada";
        case 4:
          return "Cancelada";
        case 5:
          return "Aprovada pelo Gestor";
        default:
          return "-";
      }
    },

    formatarData(toFormatDate: Date | undefined | null): string {
      if (!toFormatDate) return "";

      const data = new Date(toFormatDate);
      const dataSplit = data.toISOString().split("T");

      if (!dataSplit) return "-";

      const datePart = dataSplit[0].split("-").reverse().join("/");
      const timePart = dataSplit[1].substring(0, 5);
      return datePart + " " + timePart;
    },
  },
});
</script>
<style>
.table-horas {
  width: 100%;
  overflow-x: auto;

  .no-wrap-table {
    text-wrap: nowrap;
  }
}

.pill {
  border-radius: 30px;
  width: 140px;
  color: white;
  text-align: center;

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