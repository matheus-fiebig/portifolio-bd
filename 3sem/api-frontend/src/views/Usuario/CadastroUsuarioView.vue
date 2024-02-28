<template>
   <div class="row mt-4">
    <div class="col-12">
      <h3>CONTROLE | USUARIO</h3>
      <hr>
    </div>
  </div>
  <div class="d-flex mt-3 justify-content-end mb-3">
    <button class="btn btn-outline-primary" @click="newUser">Cadastrar Usuário</button>
  </div>
  <div class="row">
    <table class="table table-responsive no-wrap-table">
      <thead>
        <tr>
          <th scope="col" class="text-left">Nome</th>
          <th scope="col" class="text-left">Email</th>
          <th scope="col" class="text-left">Telefone</th>
          <th scope="col" class="text-left">CPF</th>
          <th scope="col" class="text-center">Função</th>
          <th scope="col" class="text-center">Status</th>
          <th scope="col" class="text-center">Ações</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(usuario, index) in usuarios" :key="index">
          <td>{{ usuario['nome'] }}</td>
          <td>{{ usuario['email'] }}</td>
          <td>{{ usuario['telefone'] }}</td>
          <td>{{ usuario['cpf'] }}</td>
          <td>{{ getFuncao(usuario.idTipoUsuario) }}</td>
          <td style="width: 100px">
            <div class="pill text-center text-wrap" :class="{ 'approved': usuario.ativo, 'canceled': !usuario.ativo }">{{ usuario.ativo ? 'Ativo' : 'Inativo' }}</div>
          </td>
          <td class="text-center">
            <button class="btn btn-link" @click="updateUser(usuario['id'])">
              <i class="fa fa-pencil" aria-hidden="true"></i>
            </button>
            <button class="btn btn-link" @click="inativarUsuario(usuario['id'])">
              <i class="fa fa-trash" aria-hidden="true"></i>
            </button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>

  <ModalUsuarioView
    :user-id="editUserId"
    @update-user-details="updateUserDetails"
    @close-modal="closeUpdateModal"
  ></ModalUsuarioView>

  <!-- Modal de Atualização de Usuário -->
  <ModalUpdateUsuarioView
    ref="updateUserModal"
    @close-modal="closeUpdateModal"
  ></ModalUpdateUsuarioView>

</template>

<script lang="ts">
import http from '@/services/http';
import { defineComponent, ref } from 'vue';
import ModalUpdateUsuarioView from './ModalCadastroUsuario/ModalCadastroUpdateUserView.vue';
import ModalUsuarioView from '@/views/Usuario/ModalCadastroUsuario/ModalCadastroUsuarioView.vue';

import { enumUser } from '@/views/Usuario/enumUser';

interface User {
  id: number;
  nome: string;
  email: string;
  telefone: string;
  cpf: string;
  idTipoUsuario: number;
  ativo: boolean;
}

export default defineComponent({
  name: "ControleUsuarioView",
  components: {
    ModalUsuarioView,
    ModalUpdateUsuarioView, 
  },
  data() {
    return {
      usuarios: [] as Array<any>,
      enumUser: enumUser,
      editUserId: null as number | null,
    };
  },
  created() {
    this.loadAllUser();
  },
  methods: {
    newUser() {
      var modal = document.getElementById("cadastro-user-modal")!;
      modal.style.display = "block";
    },
    async loadAllUser() {
      try {
        const response = await http.get('/usuario');
        this.usuarios = response.data;

        this.usuarios.forEach((usuario) => {
          usuario.funcao = this.getFuncao(usuario.idTipoUsuario);
        });
      } catch (err) {
        alert('Algo deu errado, tente novamente mais tarde.');
      }
    },
    getFuncao(idTipoUsuario: number): string {
      switch (idTipoUsuario) {
        case enumUser.Colaborador:
          return 'Colaborador';
        case enumUser.Administrador:
          return 'Administrador';
        case enumUser.Gestor:
          return 'Gestor';
        default:
          return 'Função Desconhecida';
      }
    },
    updateUser(id: number) {
  this.editUserId = id;
  var modal = document.getElementById("update-user-modal")!;
  modal.style.display = "block";

  const userToUpdate = this.usuarios.find((usuario) => usuario.id === id) as User | undefined;
  if (userToUpdate) {
    const updateUserModal = this.$refs.updateUserModal as typeof ModalUpdateUsuarioView | undefined;
    if (updateUserModal) {
      updateUserModal.setUserDetails(userToUpdate);
    }
  }
},
    async updateUserDetails(updatedUser: User) {
      try {
        const response = await http.put(`/usuario/${this.editUserId}`, updatedUser);
        const userIndex = this.usuarios.findIndex((u) => u.id === this.editUserId);
        if (userIndex !== -1) {
          this.usuarios[userIndex] = updatedUser;
        }

        this.closeUpdateModal();
      } catch (error) {
        console.error(error);
      }
    },
    closeUpdateModal() {
      this.editUserId = null;
      var modal = document.getElementById("update-user-modal")!;
      modal.style.display = "none";
    },
    async inativarUsuario(id: number) {
      const usuario = this.usuarios.find((usuario) => usuario.id === id);

      if (usuario) {
        try {
          if (usuario.ativo) {
            await http.delete(`/usuario/${usuario.id}`);
            usuario.ativo = false;
            alert('Usuário marcado como Inativo');
          } else {
            alert('Usuário já está Inativo');
          }
        } catch (error) {
          alert('Algo deu errado, tente novamente mais tarde.');
        }
      } else {
        alert('Usuário não encontrado');
      }
    },
  },
});
</script>



<style scoped>
.pill {
  border-radius: 30px;
  width: 140px;
  color: white;
}

.pill.approvedGestor {
  background-color: #fac02d;
}

.pill.approved {
  background-color: #26fc29;
}

.pill.waiting {
  background-color: gainsboro;
}

.pill.reproved,
.pill.canceled {
  background-color: red;
}
</style>
