import { createRouter, createWebHistory } from 'vue-router';
import Home from '../views/home.vue';

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
  },
  {
    path: '/create-recipe',
    name: 'Create Recipes',
    component: () => import('../views/create-recipes.vue'),
  },
  {
    path: '/search-recipes',
    name: 'Search Recipes',
    component: () => import('../views/search-recipes.vue'),
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
