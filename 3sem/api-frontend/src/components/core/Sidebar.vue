<template>
  <div class="header">
    <div class="logo">
      <img class="logo-img" src="../../assets/logo.png">
    </div>
  </div>
  <div id="sidenav">
    <SidebarItem :menu="menu" @close-all-but-this="closeMenus" v-for="menu in menus">
    </SidebarItem>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component';
import { MenuParent, adminMenu, colaboradorMenu, gestorMenu } from './menu';
import SidebarItem from './SidebarItem.vue';
import { useAuth } from '@/stores/auth';

@Options({
  props: ['menus'],
  components: {
    SidebarItem
  }
})
export default class Sidebar extends Vue {
  menus: MenuParent[] = [];
  authorizationLevel: number = 1;
  
  created(): void {
    const auth = useAuth();
    this.createSidebar(auth.getUser().permissionLevel);
    
    const currentUrl = this.$route.fullPath;
    this.menus.forEach(menu => {
      menu.active = currentUrl.includes(menu.link) && !!menu.link && menu.link != '/';
      this.activateSubItens(menu, currentUrl);
    })
  }

  private createSidebar(permissionLevel: number) {
    switch(permissionLevel){
      case 1: this.menus = colaboradorMenu; break;
      case 2: this.menus = gestorMenu; break;
      case 3: this.menus = adminMenu; break;
    }
  }

  private activateSubItens(menu: MenuParent, currentUrl: string) {
    menu.childs.map((subitem:any) => {
      subitem.active = currentUrl.includes(subitem.link);
      if (subitem.active) menu.active = true;
    });
  }

  closeMenus(openedMenu: MenuParent) {
    this.menus.forEach(m => {
      m.active = openedMenu.description == m.description;
      m.childs.map(c => c.active = false);
    })
  }
}
</script>

<style scoped>
.sidebar {
  display: flex;
  flex-direction: row;
}

.header {
  .logo {
    display: flex;
    flex-direction: column;
    justify-content: center;
    position: relative;

    .logo-img {
      position: absolute;
      z-index: 2;
      top: 85vh;
      width: 200px;
      left: 20px;
    }
  }
}

#sidenav {
  height: 120vh;
  width: 260px;
  padding-top: 75px;
  float: left;
  overflow-x: hidden;
  box-shadow: 5px 5px 5px #c6c6c6;
  background-color: #1F25C1;
  transition: 0.5s;
  position: relative;

  .toggle-btn {
    top: 74%;
    left: 45%;
    position: absolute;
  }

  &.responsive {
    width: 100%;
  }

  @media screen and (max-width: 768px) {
    .toggle-btn {
      display: block;
    }
  }
}
</style>
