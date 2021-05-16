<template lang="pug">
.container-fluid
  template(v-if="recipeData")
    .row.justify-content-evenly.mt-5
      .col-md-6
        .title-section.text-center
          h1 {{ recipeData.title }}
          h3 {{ recipeData.category }} | {{ recipeData.cookingMethod }}
          h5 By: {{ recipeData.author }}
          p.mt-3 {{ recipeData.description }}
          router-link.btn.btn-info(
            :to="{ name: 'Edit Recipe', params: { recipeId: recipeData.id } }"
          ) Edit Recipe
        .ingredient-section.my-5
          h2.text-center Ingredients
          h6.text-center Serves: {{ recipeData.servings }}
          table.table.table-striped
            thead
              tr
                th(scope="col") Ingredient
                th(scope="col") Quantity
                th(scope="col") Unit
            tbody
              template(v-for="inged in recipeData.ingredients", :key="inged")
                tr
                  td {{ inged.name }}
                  td {{ inged.quantity }}
                  td {{ inged.unit }}
      .col-md-6
        h2.text-center Steps
        ol
          template(v-for="step in recipeData.steps", :key="step")
            li {{ step }}
  template(v-else)
    h1.mt-5.text-center {{ errorMessage }}
</template>

<script>
import axios from 'axios';

export default {
  name: 'View Recipe',
  data() {
    return {
      recipeData: null,
      errorMessage: '',
    };
  },
  mounted() {
    // Get the recipe with this ID
    axios
      .get(`http://localhost:8080/api/recipes/${this.$route.params.recipeId}`)
      .then((response) => {
        this.recipeData = response.data;
      })
      .catch((error) => {
        if (error.response.status === 404) {
          this.errorMessage = 'Recipe Not Found';
        } else {
          this.errorMessage = 'Could not get recipe details';
        }
      });
  },
};
</script>

<style lang="scss" scoped>
</style>
