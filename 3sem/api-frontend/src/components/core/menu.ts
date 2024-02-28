export interface MenuParent extends MenuChild{
    icon: string;
    childs:MenuChild[];
}

export interface MenuChild{
    description:string;
    link:string;
    active:boolean;
}

export const colaboradorMenu: MenuParent[] = [
    {
      icon: 'fas fa-home',
      description: 'Home',
      active: false,
      link: '/authorized',
      childs: []
    },
    {
      icon: 'fas fa-clock',
      description: 'Lançamento de Horas',
      active: false,
      link:'lancamentohoras',
      childs: [
      ]
    },
    {
      icon: 'fas fa-chart-bar',
      description: 'Dashboard',
      active: false,
      link:'dashboard',
      childs: [
      ]
    },
    {
      icon: 'fa-solid fa-right-from-bracket',
      description: 'Sair',
      active: false,
      link:'/',
      childs: []
    }
]

export const gestorMenu: MenuParent[] = [
  {
    icon: 'fas fa-home',
    description: 'Home',
    active: false,
    link: '/authorized',
    childs: []
  },
  {
    icon: 'fas fa-clock',
    description: 'Lançamento de Horas',
    active: false,
    link:'lancamentohoras',
    childs: []
  },
  {
    icon: 'fas fa-chart-bar',
    description: 'Dashboard',
    active: false,
    link:'dashboard',
    childs: [
    ]
  },
  {
    icon: 'fa-solid fa-right-from-bracket',
    description: 'Sair',
    active: false,
    link:'/',
    childs: []
  }
]


export const adminMenu: MenuParent[] = [
  {
    icon: 'fas fa-home',
    description: 'Home',
    active: false,
    link: '/authorized',
    childs: []
  },
  {
    icon: 'fa-solid fa-users',
    description: 'Controle',
    active: false,
    link:'',
    childs: [
      { active: false, description: 'CR', link: 'cr' },
      { active: false, description: 'Usuario', link: 'usuario' },
      { active: false, description: 'Cliente', link: 'cliente' },
    ]
  },
  {
    icon: 'fas fa-clock',
    description: 'Aprovação/Reprovação',
    active: false,
    link:'lancamentohoras',
    childs: []
  },
  {
    icon: 'fas fa-chart-bar',
    description: 'Dashboard',
    active: false,
    link:'dashboard',
    childs: [
    ]
  },
  {
    icon: 'fas fa-clock',
    description: 'Relatório',
    active: false,
    link:'relatorio',
    childs: []
  },
  {
    icon: 'fa fa-cog',
    description: 'Parametrização',
    active: false,
    link:'parametrizacao',
    childs: []

  },
  {
    icon: 'fa-solid fa-right-from-bracket',
    description: 'Sair',
    active: false,
    link:'/',
    childs: []
  }
]
