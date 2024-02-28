<template>
  <div>
    <Cliente @update-table="loadAllCliente"></Cliente>
    
    <div id="update-cliente-modall" class="r-modal">
      <div class="r-modal-content">
          <div class="modal-header d-flex align-items-baseline">
              <h4>Edição de Cliente</h4>
              <span class="close" @click="close">&times;</span>
          </div>
          <div class="modal-body">
              <div class="row">
                  <div class="col-12" style="margin-top: 10px;">
                      <div class="form-group">
                          <label for="cod">Razão Social</label>
                          <input type="text" class="form-control" id="cod" v-model="nomeRs">
                      </div>
                  </div>
              </div>
              <div class="row">
                  <div class="col-12" style="margin-top: 20px;">
                      <div class="form-group">
                          <label for="sigla">CNPJ</label>
                          <input type="text" class="form-control" id="sigla" v-model="siglaCnpj">
                      </div>
                  </div>
              </div>

              <div class="row mt-4">
                  <div class="col">
                      <button type="button" @click="save()" class="btn btn-success">Salvar</button>
                      <button type="button" @click="close()" class="btn btn-link r-ml-2">Cancelar</button>
                  </div>
              </div>
          </div>
      </div>
  </div>

    <div class="row">
      <table class="table table-responsive no-wrap-table">
        <thead>
          <tr>
            <th scope="col" class="text-left">Razão Social</th>
            <th scope="col" class="text-left">Cnpj</th>
            <th scope="col" class="text-center">Status</th>
            <th scope="col" class="text-center">Ações</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="cliente in clientes" :key="cliente.idCliente">
            <td>{{ cliente.razaoSocialCliente }}</td>
            <td>{{ cliente.cnpjCliente }}</td>
            <td style="width: 100px">
              <div
                class="pill text-center text-wrap"
                :class="{ approved: cliente.ativo, inativo: !cliente.ativo }"
              >
                {{ cliente.ativo ? "Ativo" : "Inativo" }}
              </div>
            </td>
            <td class="text-center">
              <button class="btn btn-link" @click="uptedeCliente(cliente)">
                <i class="fa fa-pencil" aria-hidden="true"></i>
              </button>
              <button
                class="btn btn-link"
                @click="inativarCliente(cliente['idCliente'])"
              >
                <i class="fa fa-trash" aria-hidden="true"></i>
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script lang="ts">
import http from "@/services/http";
import { PropType, defineComponent } from "vue";
import Cliente from "@/views/Cliente/Cliente.vue";
interface clientee {
  idCliente: number;
  razaoSocialCliente: string;
  cnpjCliente:string;
  
}

export default defineComponent({
  name: "VizuCliente",
  components: {
    Cliente,
    
  },

  data() {
    return {
      clientes: [] as Array<any>,
      idCliente: 0,
      nomeRs: '',
      siglaCnpj: '',
      ativo: true,
    };
  },

  created() {
    this.loadAllCliente();
  },

  methods: {
    setclienteDetails(clienteDetails: clientee) {
    this.idCliente = clienteDetails.idCliente;
    this.nomeRs = clienteDetails.razaoSocialCliente;
    this.siglaCnpj = clienteDetails.cnpjCliente;
    
  },
    async save() {
      
      const updatedCliente = {
        id: this.idCliente,
        razaoSocialCliente: this.nomeRs,
        cnpjCliente: this.siglaCnpj,
        ativo: this.ativo,
      };

      try {
        await http.put(`/cliente/${this.idCliente}`, updatedCliente);
        alert('Cliente atualizado!');
        this.close();
        await this.loadAllCliente();
      } catch (error) {
        alert('Erro ao atualizar o cliente. Tente novamente mais tarde.');
      }
    },

    close() {
  var modal = document.getElementById('update-cliente-modall')!;
  if (modal) {
    modal.style.display = 'none';
    this.clear();
  }
},


    clear() {
      this.nomeRs = '';
      this.siglaCnpj = '';
    },
    
    uptedeCliente(cliente: clientee) {
      this.idCliente = cliente.idCliente
      var modal = document.getElementById("update-cliente-modall")!;
      modal.style.display = "block";

      this.setclienteDetails(cliente)
      
    },
    async loadAllCliente() {
      try {
        const response = await http.get("/cliente");
        this.clientes = response.data;
      } catch (err) {
        alert("Algo deu errado, tente novamente mais tarde.");
      }
    },

    async inativarCliente(idCliente: number) {
      const cliente = this.clientes.find((c) => c.idCliente === idCliente);

      if (cliente) {
        if (cliente.ativo) {
          try {
            await http.delete(`/cliente/${idCliente}`);
            cliente.ativo = false;
            alert("Cliente inativado com sucesso");
          } catch (error) {
            alert("Erro ao inativar o cliente. Tente novamente mais tarde.");
          }
        } else {
          alert("O cliente já está inativo");
        }
      } else {
        alert("Cliente não encontrado");
      }
    },
  },
});
</script>

<style>
.pill {
  border-radius: 30px;
  width: 140px;
  color: white;
}

.approved {
  background-color: #26fc29;
}

.inativo {
  background-color: red; /* Defina a cor desejada para clientes inativos */
}
</style>
