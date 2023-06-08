import { createRouter, createWebHistory } from 'vue-router';

import HomePageView from '../components/views/HomePageView.vue';
import PizzaCreateView from '../components/views/PizzaCreateView.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomePageView
    },
    {
      path: '/store',
      name: 'store',
      component: PizzaCreateView
    }
  ]
})

export {router};
