<template>
    <div class="row mt-4">
        <div class="col-12">
            <h3>CONTROLE | CLIENTE</h3>
            <hr>
        </div>
    </div>
    <div class="d-flex mt-3 justify-content-end mb-3">
        <button class="btn btn-outline-primary" @click="newCliente()"> Cadastrar Cliente </button>
    </div>
    <div id="cadastro-cliente-modal" class="r-modal">
        <div class="r-modal-content">
            <div class="modal-header d-flex align-items-baseline">
                <h4>Cadastro de Cliente</h4>
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
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component';
import http from '@/services/http';
import { defineComponent } from 'vue';


export default defineComponent({
    name: 'Cliente',

    data() {
        return {
            nomeRs: '',
            siglaCnpj: '',
            ativo:true,
            cllienteId:0

        }
    },
    methods: {
        newCliente() {
            var modal = document.getElementById("cadastro-cliente-modal")!;
            modal.style.display = "block";
        },



        save() {
            const createCliente = {
                razaoSocialCliente: this.nomeRs,
                cnpjCliente: this.siglaCnpj, 
                ativo:this.ativo,
                idCliente: 0
            }


            http.post('/cliente', createCliente)
                .then(_ => alert('Cliente salvo !!!'))
                .catch(_ => alert('Tente Novamente '))
                .finally(() => {
                    this.clear()


                });


        },
        emitUpdateTable() {
            this.$emit('update-table');
        },



        close() {
            var modal = document.getElementById("cadastro-cliente-modal")!;
            modal.style.display = "none";
            this.clear();
        },

        clear() {
            this.nomeRs = '';
            this.siglaCnpj = '';
        }
    }
})

</script>

<style scoped>
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
}

.r-ml-2 {
    margin-left: 15px;
}
</style>