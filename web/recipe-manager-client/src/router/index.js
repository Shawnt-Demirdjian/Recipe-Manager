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
    name: 'Create Recipe',
    component: () => import('../views/create-recipe.vue'),
  },
  {
    path: '/search-recipes',
    name: 'Search Recipes',
    component: () => import('../views/search-recipes.vue'),
  },
  {
    path: '/recipes/:recipeId',
    name: 'View Recipe',
    component: () => import('../views/view-recipe.vue'),
    props: true,
  },
  {
    path: '/edit-recipe/:recipeId',
    name: 'Edit Recipe',
    component: () => import('../views/edit-recipe.vue'),
    props: true,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
