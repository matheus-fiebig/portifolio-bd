<template>
    <div id="cadastro-cr-modal" class="r-modal">
        <!-- Modal content -->
        <div class="r-modal-content">
            <div class="modal-header d-flex align-items-baseline">
                <h4>Cadastro de CR</h4>
                <span class="close" @click="close">&times;</span>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-12">
                        <div class="form-group">
                            <label for="cod">Cod</label>
                            <input type="text" class="form-control" id="cod" v-model="codigoCr">
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-12">
                        <div class="form-group">
                            <label for="sigla">Sigla</label>
                            <input type="text" class="form-control" id="sigla" v-model="siglaCr">
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-12">
                        <div class="form-group">
                            <label for="nome">Nome</label>
                            <input type="text" class="form-control" id="nome" v-model="nomeCr">
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
import http from '@/services/http';
import { defineComponent } from 'vue';

export default defineComponent({
    name: 'ModalCadastroCrView',
    props: {
        idCrProp: Number,
        nomeCrProp: String,
        siglaCrProp: String,
        codigoCrProp: String
    },
    data() {
        return {
            codigoCr: this.codigoCrProp,
            siglaCr: this.siglaCrProp,
            nomeCr: this.nomeCrProp,
            idCr: this.idCrProp
        }
    },
    watch: {
        siglaCrProp(newValue, oldValue) {
            this.siglaCr = newValue;
        },
        codigoCrProp(newValue, oldValue) {
            this.codigoCr = newValue;
        },
        nomeCrProp(newValue, oldValue) {
            this.nomeCr = newValue;
        },
        idCrProp(newValue, oldValue) {
            this.idCr = newValue;
        }
    },
    methods: {
        async updateCr() {
            const updatedCr = {
                codigoCr: this.codigoCr,
                siglaCr: this.siglaCr,
                nomeCr: this.nomeCr,
                idCr: this.idCr,
            };
            try {
                await http.put(`/cr/${updatedCr.idCr}`, updatedCr);
                alert('Cr atualizado!');
                this.emitUpdateTable();
                this.close();
            } catch (error) {
                alert('Erro ao atualizar o cr. Tente novamente mais tarde.');
            }
        },
        save() {
            if(this.idCr){
                this.updateCr();
                return;
            }
            const crToCreate = {
                codigoCr: this.codigoCr,
                siglaCr: this.siglaCr,
                nomeCr: this.nomeCr,
                idCr: 0
            }

            http.post('/cr', crToCreate)
                .then(_ => alert('CR salvo com sucesso!!!'))
                .catch(_ => alert('Algo deu errado. Tente novamente mais tarde.'))
                .finally(() => {
                    this.emitUpdateTable();
                    this.close()
                });
        },
        emitUpdateTable() {
            this.$emit('update-table');
        },
        close() {
            var modal = document.getElementById("cadastro-cr-modal")!;
            modal.style.display = "none";
            this.clear();
        },
        clear() {
            this.codigoCr = '';
            this.siglaCr = '';
            this.nomeCr = '';
        }
    }
})
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