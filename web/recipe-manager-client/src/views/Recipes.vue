<template lang="pug">
h1 Recipes!
.recipe-container
  template(v-for="recipe in recipeData", v-bind:key="recipe")
    RecipeCard(:data="recipe")
</template>

<script>
// @ is an alias to /src
import RecipeCard from '@/components/RecipeCard.vue';
import axios from 'axios';

export default {
  name: 'Recipes',
  components: {
    RecipeCard,
  },
  data() {
    return {
      recipeData: [],
    };
  },
  mounted() {
    axios
      .get('http://localhost:8080/api/recipes?queryString=cheese')
      .then((response) => {
        this.recipeData = response.data;
      })
      .catch((ex) => console.log(ex));
  },
};
</script>

<style lang="scss" scoped>
.recipe-container {
  display: flex;
  align-content: center;
  flex-wrap: wrap;
  justify-content: space-evenly;
}
</style>
