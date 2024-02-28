import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import Login from '@/views/Login/LoginView.vue'
import Home from '@/views/HomeView.vue'
import LancamentoHorasView from '@/views/LancamentoHoras/LancamentoHorasView.vue'
import cliente from '@/views/Cliente/VizuCliente.vue'
import ControleCrView from '@/views/Cr/ControleCrView.vue'
import CadastroUsuarioView from '@/views/Usuario/CadastroUsuarioView.vue'
import Relatorio from '@/views/Relatorio/Relatorio.vue'
import ParametrizacaoView from '@/views/Parametrizacao/ParametrizacaoView.vue'
import DashboardViewVue from '@/views/Dashboard/DashboardView.vue'

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    name: "Login",
    component: Login,
  },
  {
    path: "/authorized/",
    name: "Home",
    component: Home,
    children: [
      {
        path: "lancamentohoras", 
        name: "LancamentoHoras",
        component:LancamentoHorasView,
      },
      {
        path: "cliente",
        name: "cliente",
        component:cliente
      },
      {
        path: "cr",
        name: "CR",
        component:ControleCrView,
      },
      {
        path: "usuario",
        name: "CadastroUsuario",
        component: CadastroUsuarioView,
      },
      {
        path: "parametrizacao",
        name: "Parametrizacao",
        component: ParametrizacaoView,
      },
      {
        path: "dashboard",
        name: "Dashboard",
        component: DashboardViewVue,
      },
      {
        path: "relatorio",
        name: "Relatorio",
        component: Relatorio,
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
