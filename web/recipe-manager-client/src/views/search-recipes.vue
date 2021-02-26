<template lang="pug">
.container-fluid
  .row.text-center.my-3
    h1 Recipes!
  .row.justify-content-center.mx-sm-5
    form.search-form.col-12(@submit.prevent="submitform")
      .input-group
        input.form-control(
          v-model="queryString",
          placeholder="Search",
          type="text",
          required,
          autofocus
        )
        select.form-select(v-model="category")
          option(value="", disabled) Category
          option(value="ANY") Any
          template(v-for="cat in categories", :key="cat")
            option(value=cat) {{ cat }}
        select.form-select(v-model="cookingMethod")
          option(value="", disabled) Cooking Method
          option(value="ANY") Any
          template(v-for="method in cookingMethods", :key="method")
            option(value=method) {{ method }}
        button.btn.btn-primary(@click="search", type="submit") Search
    .invalid-feedback.d-block.text-center {{ errorMessage }}
.container-fluid.d-flex.align-content-center.flex-wrap.justify-content-center
  template(v-for="recipe in recipeData", :key="recipe")
    RecipeCard(:recipeData="recipe")
</template>

<script>
// @ is an alias to /src
import RecipeCard from '@/components/recipe-card.vue';
import axios from 'axios';

export default {
  name: 'Search Recipes',
  components: {
    RecipeCard,
  },
  data() {
    return {
      cookingMethods: [],
      categories: [],
      recipeData: [],
      queryString: '',
      category: '',
      cookingMethod: '',
      errorMessage: '',
    };
  },
  methods: {
    search() {
      const params = {};
      if (this.category !== 'ANY' && this.category !== '') {
        params.category = this.category;
      }
      if (this.cookingMethod !== 'ANY' && this.cookingMethod !== '') {
        params.cookingMethod = this.cookingMethod;
      }
      params.queryString = this.queryString;

      axios
        .request({ url: 'http://localhost:8080/api/recipes', method: 'get', params })
        .then((response) => {
          this.recipeData = response.data;
          this.errorMessage = '';
        })
        .catch((error) => {
          if (error.response.status === 404) {
            // Query returned no results
            this.errorMessage = 'No recipes found';
            this.recipeData = [];
          }
        });
    },
    formatDropdownOptions(options) {
      // Format list of Categories or Cooking Methods
      const newOptions = [];
      options.forEach((option) => {
        // swap underscores with spaces
        let newOption = option.replace('_', ' ');
        // Only first letter should be captial
        newOption = newOption.replace(
          /\w\S*/g,
          (txt) => txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase(),
        );
        newOptions.push(newOption);
      });
      return newOptions;
    },
  },
  mounted() {
    // Get list of Cooking Methods
    axios
      .get('http://localhost:8080/api/cooking-methods')
      .then((response) => {
        this.cookingMethods = this.formatDropdownOptions(response.data);
        this.errorMessage = '';
      })
      .catch((error) => {
        this.errorMessage = 'Could not get list of Cooking Methods.';
        console.error(error);
      });

    // Get list of Categories
    axios
      .get('http://localhost:8080/api/categories')
      .then((response) => {
        this.categories = this.formatDropdownOptions(response.data);
        this.errorMessage = '';
      })
      .catch((error) => {
        this.errorMessage = 'Could not get list of Categories.';
        console.error(error);
      });
  },
};
</script>

<style lang="scss" scoped>
.input-group > * {
  flex: 1 0 auto;
  min-width: 12em;
}
</style>
