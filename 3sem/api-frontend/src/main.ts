import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import LoginView from "./views/Login/LoginView.vue";
import "@fortawesome/fontawesome-free/css/all.css";
import "@fortawesome/fontawesome-free/js/all.js";
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";
import VueApexCharts from "vue3-apexcharts";

const app = createApp(App)
  .component("LoginView", LoginView)
  .use(router)
  .use(VueApexCharts)
  .mount("#app");
