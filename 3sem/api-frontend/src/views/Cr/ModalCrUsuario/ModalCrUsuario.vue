<template>
    <div id="cr-usuario-modal" class="r-modal">
        <!-- Modal content -->
        <div class="r-modal-content">
            <div class="modal-header d-flex align-items-baseline">
                <h4>Editar usuarios do CR {{ idCr }}</h4>
                <span class="close" @click="close">&times;</span>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-5 text-center">Usuários para Adicionar</div>
                    <div class="col-1">&nbsp;</div>
                    <div class="col-6 text-center">Usuarios do CR</div>
                </div>
                <div class="row">
                    <div class="col-5">
                        <table class="w-100">
                            <tr v-for="usuarioParaAdd in usuariosParaAdd">
                                <td 
                                    @click="setUsuarioParaAdd(usuarioParaAdd.id)" 
                                    class="cursor-pointer text-center"
                                    :class="{selected: estaSelecionadoParaAdicionar(usuarioParaAdd.id)}"
                                >{{usuarioParaAdd.nome}}</td>
                            </tr>
                        </table>
                    </div>
                    <div class="col-2 row">
                        <div class="col-12 cursor-pointer arrow-change" @click="adicionarNoCr()"><i class="fa-solid fa-square-caret-right"></i></div>
                        <div class="col-12 cursor-pointer arrow-change" @click="removerDoCr()"><i class="fa-solid fa-square-caret-left"></i></div>
                    </div>
                    <div class="col-5">
                        <table class="w-100">
                            <tr v-for="usuarioDoCr in usuariosDoCr">
                                <td 
                                    @click="setUsuarioParaRemover(usuarioDoCr.id)" 
                                    class="cursor-pointer text-center"
                                    :class="{selected: estaSelecionadoParaRemover(usuarioDoCr.id)}"
                                >{{usuarioDoCr.nome}}</td>
                            </tr>
                        </table>
                    </div>
                </div>
                <div class="row mt-4">
                    <div class="col">
                        <button type="button" @click="close()" class="btn btn-link r-ml-2">Cancelar</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
import http from '@/services/http';
import { CrUsuarioDominio } from './cr-usuario-dominio';
import { defineComponent } from 'vue';

export default defineComponent({
    name: 'ModalCrUsuario',
    props:['idCr'],
    watch:{
        idCr: function(newVal, oldVal) {
            this.loadUsuarioCr();
        }
    },
    data() {
        return {
            usuariosTotais: new Array<CrUsuarioDominio>(),
            usuariosParaAdd: new Array<CrUsuarioDominio>(),
            usuariosDoCr: new Array<CrUsuarioDominio>(),
            usuarioParaAdd: new Number(),
            usuarioParaRemover: new Number(),
        }
    },
    methods: {
        loadUsuarioCr(){
            this.limparCampos();
            http.get('/usuario')
            .then(x => {
                this.usuariosTotais = x.data;
                http.get('/crUsuario/' + this.idCr)
                    .then(y => {
                        const data = y.data as [
                            {
                                idUsuario: number,
                                idCr: number
                            }
                        ];

                        const usuariosCr = this.usuariosTotais.filter(z => data.map(z => z.idUsuario).includes(z.id));
                        this.usuariosDoCr.push(...usuariosCr)
                        
                        const usuariosForaDoCr = this.usuariosTotais.filter(z => !data.map(z => z.idUsuario).includes(z.id));
                        this.usuariosParaAdd.push(...usuariosForaDoCr)
                    });
            });
        },
        setUsuarioParaAdd(id:number){
            this.usuarioParaAdd = id;
        },
        setUsuarioParaRemover(id:number){
            this.usuarioParaRemover = id;
        },
        estaSelecionadoParaAdicionar(id:number){
            return id == this.usuarioParaAdd
        },
        estaSelecionadoParaRemover(id:number){
            return id == this.usuarioParaRemover;
        },
        adicionarNoCr(){
            http.post('/crUsuario', [{idUsuario: this.usuarioParaAdd, idCr: this.idCr}])
                .then(_ => this.loadUsuarioCr())
        },
        removerDoCr(){
            http.delete('/crUsuario', {data: {idUsuario: this.usuarioParaRemover, idCr: this.idCr}})
                .then(_ => this.loadUsuarioCr())
        },
        limparCampos(){
            this.usuariosParaAdd = [];
            this.usuariosDoCr = [];
            this.usuarioParaAdd = 0;
            this.usuarioParaRemover = 0;
            this.usuariosTotais = [];
        },
        close() {
            var modal = document.getElementById("cr-usuario-modal")!;
            modal.style.display = "none";
        }
    }
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

.cursor-pointer{
    cursor: pointer;
}

.selected{
    background-color: #00000021;
}

.arrow-change{
    display: flex;
    justify-content: center;
}

.r-ml-2 {
    margin-left: 15px;
}
</style>    