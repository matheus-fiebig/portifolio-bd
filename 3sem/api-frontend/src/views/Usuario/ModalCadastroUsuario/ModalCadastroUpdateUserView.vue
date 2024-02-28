<template>
  <div class="r-modal" id="update-user-modal">
    <div class="r-modal-content">
      <div class="modal-header d-flex align-items-baseline">
        <h4>Edição de Usuário</h4>
        <span class="close" @click="close">&times;</span>
      </div>
      <transition name="fade">
      <div class="modal-body " >
        <div class="row">
          <div class="col-12">
            <div class="form-group">
              <label for="nome">Nome</label>
              <input type="text" class="form-control" id="nome" :value="localNome" @input="updateNome" />
            </div>
          </div>
        </div>

        <div class="row">
          <div class="col-12">
            <div class="form-group">
              <label for="telefone">Telefone</label>
              <input type="text" class="form-control" id="telefone" :value="localTelefone" @input="updateTelefone" />
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-12">
            <div class="form-group">
              <label for="email">Email</label>
              <input type="text" class="form-control" id="email" :value="localEmail" @input="updateEmail" />
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-12">
            <div class="form-group">
              <label for="cpf">CPF</label>
              <input type="text" class="form-control" id="cpf" :value="localCpf" @input="updateCpf" />
            </div>
          </div>
        </div>

        <div class="row">
          <div class="col-12">
            <div class="form-group">
              <label for="funcao">Função</label>
              <select class="form-select" id="funcao" :value="localFuncao" @input="updateFuncao">
                <option value="Colaborador">Colaborador</option>
                <option value="Administrador">Administrador</option>
                <option value="Gestor">Gestor</option>
              </select>
            </div>
          </div>
        </div>

        

        <div class="row mt-4">
          <div class="col">
            <button type="button" @click="save" class="btn btn-success">Salvar</button>
            <button type="button" @click="close" class="btn btn-link r-ml-2">Cancelar</button>
          </div>
        </div>
      </div>
    </transition>

    </div>
  </div>
</template>

<script lang="ts">
import http from '@/services/http';
import { defineComponent } from 'vue';
interface User {
  id: number;
  nome: string;
  email: string;
  telefone: string;
  cpf: string;
  idTipoUsuario: number;
  ativo: boolean;
}
const enumUser = {
  Colaborador: 1,
  Administrador: 2,
  Gestor: 3,
};

export default defineComponent({
  props: {
    userId: Number,
  },
  data() {
    return {
      localId: this.userId,
      localNome: '',
      localEmail: '',
      localTelefone: '',
      localCpf: '',
      localFuncao: '',
      localStatus: '',
    };
  },
  methods: {
    close() {
      const modal = document.getElementById("update-user-modal");
      if (modal) {
        modal.style.display = "none";
      }
      this.clear();
    },
    clear() {
      this.localNome = "";
      this.localEmail = "";
      this.localTelefone = "";
      this.localCpf = "";
      this.localFuncao = "";
      this.localStatus = "";
    },
    updateNome(event: InputEvent) {
    this.localNome = (event.target as HTMLInputElement).value;
  },
  updateTelefone(event: InputEvent) {
    this.localTelefone = (event.target as HTMLInputElement).value;
  },
  updateEmail(event: InputEvent) {
    this.localEmail = (event.target as HTMLInputElement).value;
  },
  updateCpf(event: InputEvent) {
    this.localCpf = (event.target as HTMLInputElement).value;
  },
  updateFuncao(event: InputEvent) {
    this.localFuncao = (event.target as HTMLInputElement).value;
  },
  setUserDetails(userDetails: User) {
    this.localId = userDetails.id;
    this.localNome = userDetails.nome;
    this.localEmail = userDetails.email;
    this.localTelefone = userDetails.telefone;
    this.localCpf = userDetails.cpf;
    this.localFuncao = userDetails.idTipoUsuario.toString();
    this.localStatus = userDetails.ativo ? 'ativo' : 'inativo';
  },
    async save() {
      const funcaoAsInt = this.getFuncao(this.localFuncao);

      const updatedUser = {
        id: this.localId,
        nome: this.localNome,
        email: this.localEmail,
        telefone: this.localTelefone,
        cpf: this.localCpf,
        idTipoUsuario: funcaoAsInt,
        ativo: this.localStatus === 'ativo',
      };

      try {
        await http.put(`/usuario/${this.localId}`, updatedUser);
        alert("Usuario atualizado!")

        this.close();
      } catch (error) {
        console.error(error);
      }
    },
    getFuncao(tipoUsuario: string) {
      switch (tipoUsuario) {
        case 'Colaborador':
          return enumUser.Colaborador;
        case 'Administrador':
          return enumUser.Administrador;
        case 'Gestor':
          return enumUser.Gestor;
        default:
          return 1;
      }
    },
  },
});
</script>


<style lang="css">
.r-modal {
  display: none;
  position: fixed;
  z-index: 1;
  padding-top: 100px;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: rgb(0, 0, 0);
  background-color: rgba(0, 0, 0, 0.4);
}

.r-modal-content {
  background-color: #fefefe;
  margin: auto;
  padding: 20px;
  border: 1px solid #888;
  width: 630px;
}

.close {
  color: #aaaaaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: #000;
  text-decoration: none;
  cursor: pointer;
}

button {
  color: white;
}

.r-ml-2 {
  margin-left: 15px;
}

/* Defina o estilo de entrada pela esquerda */
.fade-enter-active {
  animation: enter-from-left 0.5s;
}

/* Defina o estilo de saída */
.fade-leave-active {
  animation: fade-out 0.5s;
}

@keyframes enter-from-left {
  from {
    opacity: 0;
    transform: translateX(-100%);
  }
  to {
    opacity: 1;
    transform: translateX(0);
  }
}

@keyframes fade-out {
  from {
    opacity: 1;
  }
  to {
    opacity: 0;
  }
}

</style>
