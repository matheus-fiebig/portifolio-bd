<template>
  <div class="row mt-4 mb-4">
    <div class="col-12">
      <h3>DASHBOARD</h3>
      <hr />
    </div>
  </div>
  <div class="row">
    <div class="col-4">
      <label>Cr</label>
      <select class="form-control" v-model="filter.idCr">
        <option v-for="item in crs" :key="item.idCr" :value="item.idCr">
          {{ item.nomeCr }}
        </option>
      </select>
    </div>
    <div class="col-4">
      <label>Cliente</label>
      <select class="form-control" v-model="filter.idCliente">
        <option
          v-for="item in clients"
          :key="item.idCliente"
          :value="item.idCliente"
        >
          {{ item.razaoSocialCliente }}
        </option>
      </select>
    </div>
    <div class="col-4">
      <label>Inicio</label>
      <input type="date" class="form-control" v-model="filter.dataInicio" />
    </div>
  </div>
  <div class="row mt-2">
    <div class="col-4">
      <label>Fim</label>
      <input type="date" class="form-control" v-model="filter.dataFim" />
    </div>
    <div class="col-5 mt-4">
      <button
        type="button"
        v-on:click="getData()"
        class="btn btn-outline-primary"
      >
        Carregar Dashboard
      </button>
    </div>
  </div>
  <div class="row mt-5">
    <div id="col-12">
      <apexchart
        type="bar"
        height="350"
        :options="chartConfig.chartOptions"
        :series="chartConfig.series"
      ></apexchart>
    </div>
  </div>
</template>
  
<script lang="ts">
import http from "@/services/http";
import { useAuth } from "@/stores/auth";
import { Options, Vue } from "vue-class-component";

export default class DashboardView extends Vue {
  crs: any = [];
  clients: any = [];

  filter: any = {
    idCliente: 0,
    idCr: 0,
    dataInicio: "",
    dataFim: "",
  };

  chartConfig = {
    series: [
      {
        data: [0, 0, 0, 0],
      },
    ],
    chartOptions: {
      chart: {
        type: "bar",
        height: 100,
      },
      plotOptions: {
        bar: {
          borderRadius: 6,
          horizontal: true,
        },
      },
      dataLabels: {
        enabled: false,
      },
      xaxis: {
        categories: [
          "Horas Extra - Diario",
          "Horas Extra - Noturno",
          "Sobreaviso",
        ],
      },
    },
  };

  created(): void {
    this.getClients();
    this.getCrs();
  }

  getData() {
    const request = {
      idCliente: this.filter.idCliente,
      idCr: this.filter.idCr,
      dataInicio: this.filter.dataInicio + " 00:00:00",
      dataFim: this.filter.dataFim + " 23:59:59",
    };
    http.get("/dashboard", { params: request }).then((response) => {
      const user = useAuth().getUser();
      const filteredData = response.data.filter(
        (x: any) => x.idUsuario == user.id
      );

      this.chartConfig.series[0].data = [
        this.getHour(filteredData, "HE Diurno"),
        this.getHour(filteredData, "HE Noturno"),
        this.getHour(filteredData, "Sobreaviso"),
      ];
    });
  }

  private getHour(f: any[], key: string) {
    return f.filter((x) => x.Modalidade == key).map((x) => x.horas)[0] ?? 0;
  }

  getClients() {
    http.get("/cliente").then((Response) => {
      this.clients = Response.data;
    });
  }

  getCrs() {
    const user = useAuth().getUser();
    http.get("/cr/" + user.id).then((response) => {
      this.crs = response.data;
    });
  }
}
</script>
  
<style scoped></style>