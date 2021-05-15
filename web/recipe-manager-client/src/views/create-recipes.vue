<template lang="pug">
.container
  .row.text-center.my-3
    h1 Create Recipe
    template(v-for="error in errorMessages")
      .invalid-feedback.d-block.text-center {{ error }}
  .row.justify-content-center.mx-sm-5
    form.col-12.needs-validation(
      @submit.prevent="submitform",
      ref="createRecipeForm",
      novalidate
    )
      .row
        .col-6
          .input-group.has-validation
            input.form-control(
              v-model="newRecipe.title",
              placeholder="Title",
              type="text",
              required,
              autofocus
            )
            input.form-control(
              v-model="newRecipe.author",
              placeholder="Author",
              type="text",
              required
            )
          .input-group.has-validation.my-3
            select.form-select(v-model="newRecipe.category", required)
              option(value="", disabled) Category
              option(value="ANY") Any
              template(v-for="cat in categories", :key="cat")
                option(value=cat) {{ cat }}
            select.form-select(v-model="newRecipe.cookingMethod", required)
              option(value="", disabled) Cooking Method
              option(value="ANY") Any
              template(v-for="method in cookingMethods", :key="method")
                option(value=method) {{ method }}
          .input-group.has-validation
            span.input-group-text Serves:
            input.form-control(
              type="number",
              v-model="newRecipe.servings",
              min=1,
              step=1
            )
        .col-6
          textarea.form-control(
            v-model="newRecipe.description",
            placeholder="Description",
            required,
            rows=5
          )
      .row.text-center.my-4
        .col-12
          h3 Steps
          ol.list-group.list-group-numbered.recipeList
            template(v-for="(step, index) in newRecipe.steps")
              li.list-group-item.d-flex.justify-content-between.align-items-start
                textarea.form-control.ms-3(
                  v-model="newRecipe.steps[index]",
                  rows=3,
                  required
                )
          .btn-group.btn-group-sm.mb-1(role="group")
            button.btn.btn-outline-primary(
              type="button",
              v-on:click="removeStep"
            ) Remove Step
            button.btn.btn-outline-primary(type="button", v-on:click="addStep") Add Step
      .row.text-center.mt-3
        .col-12
          h3 Ingredients
          ul.list-group.recipeList
            template(v-for="(ingredient, index) in newRecipe.ingredients")
              li.list-group-item
                .input-group.has-validation
                  span.input-group-text Ingredient
                  input.form-control(
                    v-model="ingredient.name",
                    type="text",
                    required
                  )
                  span.input-group-text Amount
                  input.form-control(
                    v-model="ingredient.quantity",
                    type="number",
                    required
                  )
                  span.input-group-text Unit
                  input.form-control(
                    v-model="ingredient.unit",
                    type="text",
                    required
                  )
          .btn-group.btn-group-sm.mb-1(role="group")
            button.btn.btn-outline-primary(
              type="button",
              v-on:click="removeIngredient"
            ) Remove Ingredient
            button.btn.btn-outline-primary(
              type="button",
              v-on:click="addIngredient"
            ) Add Ingredient
      .row.text-center.mt-5
        .col-12
          button.btn.btn-success(@click="createRecipe", type="submit") Create Recipe
</template>

<script>
import axios from 'axios';

export default {
  name: 'Create Recipe',
  components: {},
  data() {
    return {
      newRecipe: {
        title: '',
        author: '',
        description: '',
        ingredients: [
          {
            name: '',
            quantity: 1,
            unit: '',
          },
        ],
        steps: [''],
        category: '',
        cookingMethod: '',
        servings: 1,
      },
      cookingMethods: [],
      categories: [],
      errorMessages: [],
    };
  },
  methods: {
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
    addStep() {
      this.newRecipe.steps.push('');
    },
    removeStep() {
      if (this.newRecipe.steps.length > 1) {
        this.newRecipe.steps.pop();
      }
    },
    addIngredient() {
      this.newRecipe.ingredients.push({
        name: '',
        quantity: 1,
        unit: '',
      });
    },
    removeIngredient() {
      if (this.newRecipe.ingredients.length > 1) {
        this.newRecipe.ingredients.pop();
      }
    },
    createRecipe() {
      // Submit request to create new recipe

      const form = this.$refs.createRecipeForm;
      if (form.checkValidity()) {
        axios
          .post('http://localhost:8080/api/recipes', this.newRecipe)
          .then((response) => {
            this.errorMessages = [];
            console.log(response);
            this.$router.push(`/recipes/${response.data.id}`);
          })
          .catch((error) => {
            this.errorMessages = error.response.data.errors;
            console.error(error);
          });
      }

      form.classList.add('was-validated');
    },
  },
  mounted() {
    // Get list of Cooking Methods
    axios
      .get('http://localhost:8080/api/cooking-methods')
      .then((response) => {
        this.cookingMethods = this.formatDropdownOptions(response.data);
      })
      .catch((error) => {
        this.errorMessages.push('Could not get list of Cooking Methods.');
        console.error(error);
      });

    // Get list of Categories
    axios
      .get('http://localhost:8080/api/categories')
      .then((response) => {
        this.categories = this.formatDropdownOptions(response.data);
      })
      .catch((error) => {
        this.errorMessages.push('Could not get list of Categories.');
        console.error(error);
      });
  },
};
</script>

<style lang="scss" scoped>
.recipeList > li {
  border: none;
}
</style>
