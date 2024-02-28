<template>
  <div class="page">
    <Sidebar></Sidebar>
    <div class="container container-margins">
      <router-view />
    </div>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component'
import Sidebar from '@/components/core/Sidebar.vue';

@Options({
  components: {
    Sidebar
  }
})
export default class HomeView extends Vue {
};
</script>

<style scoped>
.page {
  width: 100%;
  overflow-x: hidden;

  .container-margins {
    width: calc(100vw - 350px);
    margin: 25px 0 0 300px;
  }

}
</style>
