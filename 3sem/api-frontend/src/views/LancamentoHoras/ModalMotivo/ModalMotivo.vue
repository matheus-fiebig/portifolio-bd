<template>
    <div id="reprovar-modal" class="r-modal">

        <!-- Modal content -->
        <div class="r-modal-content">
            <div class="modal-header d-flex align-items-baseline">
                <h4>Explique o motivo</h4>
                <span class="close" @click="close">&times;</span>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col">
                        <textarea v-model="motivo" placeholder="Motivo" style="width: 600px;" type="text" class="form-control">
                        </textarea>
                    </div>
                </div>
                <div class="row mt-4">
                    <div class="col">
                        <button type="button" @click="reprovar" class="btn btn-danger">Reprovar</button>
                        <button type="button" @click="close" class="btn btn-link r-ml-2">Cancelar</button>
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
    name: 'ModalMotivo',
    __name: 'ModalMotivo',
    props: {
        idLancamento: Number,
        statusAtual: Number
    },
    data(){
        return {
            motivo: '',
        }
    },
    methods: {
        close() {
            var modal = document.getElementById("reprovar-modal")!;
            modal.style.display = "none";
            this.motivo = '';
        },

        reprovar(){
            const horaParaReprovar = {
                idLancamento: this.idLancamento,
                status: 3,
                justificativa: this.motivo
            };

            http.put('/lancamentoHoras', horaParaReprovar)
                .then(r => {
                    this.close();
                    this.$emit('update-table');
                    alert('Hora reprovada com sucesso.');
                })
                .catch(err => {
                    alert('Algo deu errado tente novamente mais tarde.')
                });
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
        width: fit-content;
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

    button{
        color:white;
    }
}

.r-ml-2{
    margin-left:15px;
}
</style>    