import { Options, Vue } from 'vue-class-component';
import http from "@/services/http";
import TabelaHoras from './TabelaHoras/TabelaHoras.vue';



@Options({
components: {
TabelaHoras
}
})
export default class LancamentoHorasView extends Vue {
lancamento: any = {
id: '',
cr: '',
cliente: '',
projeto: '',
inicio: '',
fim: '',
modalidade: '',
solicitante: '',
justificativa: '',
status: '',
};

modal: any = [{
id: '',
modalidade: '',
}];




async lancar() {


const { data } = await http.post("/lancamentoHoras", this.lancamento);

}
}
