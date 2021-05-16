<template lang="pug">
.container
  .row.text-center.my-3
    h1 Edit Recipe
    template(v-for="error in errorMessages")
      .invalid-feedback.d-block.text-center {{ error }}
  .row.justify-content-center.mx-sm-5
    template(v-if="recipeData")
      form.col-12.needs-validation(
        @submit.prevent="submitform",
        ref="createRecipeForm",
        novalidate
      )
        .row
          .col-6
            .input-group.has-validation
              input.form-control(
                v-model="recipeData.title",
                placeholder="Title",
                type="text",
                required,
                autofocus
              )
              input.form-control(
                v-model="recipeData.author",
                placeholder="Author",
                type="text",
                required
              )
            .input-group.has-validation.my-3
              select.form-select(v-model="recipeData.category", required)
                option(value="", disabled) Category
                option(value="ANY") Any
                template(v-for="cat in categories", :key="cat")
                  option(value=cat) {{ cat }}
              select.form-select(v-model="recipeData.cookingMethod", required)
                option(value="", disabled) Cooking Method
                option(value="ANY") Any
                template(v-for="method in cookingMethods", :key="method")
                  option(value=method) {{ method }}
            .input-group.has-validation
              span.input-group-text Serves:
              input.form-control(
                type="number",
                v-model="recipeData.servings",
                min=1,
                step=1
              )
          .col-6
            textarea.form-control(
              v-model="recipeData.description",
              placeholder="Description",
              required,
              rows=5
            )
        .row.text-center.my-4
          .col-12
            h3 Steps
            ol.list-group.list-group-numbered.recipeList
              template(v-for="(step, index) in recipeData.steps")
                li.list-group-item.d-flex.justify-content-between.align-items-start
                  textarea.form-control.ms-3(
                    v-model="recipeData.steps[index]",
                    rows=3,
                    required
                  )
            .btn-group.btn-group-sm.mb-1(role="group")
              button.btn.btn-outline-primary(
                type="button",
                v-on:click="removeStep"
              ) Remove Step
              button.btn.btn-outline-primary(
                type="button",
                v-on:click="addStep"
              ) Add Step
        .row.text-center.mt-3
          .col-12
            h3 Ingredients
            ul.list-group.recipeList
              template(v-for="(ingredient, index) in recipeData.ingredients")
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
            .btn-group
              button.btn.btn-success(@click="updateRecipe", type="submit") Update Recipe
              button.btn.btn-danger(
                type="button",
                data-bs-toggle="modal",
                data-bs-target="#verifyDeleteModal"
              ) Delete Recipe
#verifyDeleteModal.modal.fade(tabindex="-1")
  .modal-dialog.modal-dialog-centered
    .modal-content
      .modal-header.bg-warning
        h5.modal-title Delete Recipe
        button.btn-close(type="button", data-bs-dismiss="modal")
      .modal-body.text-center
        h4 Are you sure you want to delete this recipe?
        h4 There is no undo!
      .modal-footer.bg-warning
        button.btn.btn-secondary(type="button", data-bs-dismiss="modal") Close
        button.btn.btn-danger(
          @click="deleteRecipe",
          type="button",
          data-bs-dismiss="modal"
        ) Delete
</template>

<script>
import axios from 'axios';

export default {
  name: 'Edit Recipe',
  components: {},
  data() {
    return {
      recipeData: null,
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
      this.recipeData.steps.push('');
    },
    removeStep() {
      if (this.recipeData.steps.length > 1) {
        this.recipeData.steps.pop();
      }
    },
    addIngredient() {
      this.recipeData.ingredients.push({
        name: '',
        quantity: 1,
        unit: '',
      });
    },
    removeIngredient() {
      if (this.recipeData.ingredients.length > 1) {
        this.recipeData.ingredients.pop();
      }
    },
    updateRecipe() {
      // Submit request to update recipe

      const form = this.$refs.createRecipeForm;
      if (form.checkValidity()) {
        axios
          .put(`http://localhost:8080/api/recipes/${this.$route.params.recipeId}`, this.recipeData)
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
    deleteRecipe() {
      // Submit request to delete recipe

      axios
        .delete(`http://localhost:8080/api/recipes/${this.$route.params.recipeId}`)
        .then((response) => {
          console.log(response);
          this.$router.push('/');
        })
        .catch((error) => {
          this.errorMessages.push("There recipe doesn't exist!");
          console.error(error);
        });
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

    // Get the recipe with this ID
    axios
      .get(`http://localhost:8080/api/recipes/${this.$route.params.recipeId}`)
      .then((response) => {
        this.recipeData = response.data;

        // format category and cooking method
        const formatedCat = this.formatDropdownOptions([this.recipeData.category])[0];
        this.recipeData.category = formatedCat;

        const formatedMethod = this.formatDropdownOptions([this.recipeData.cookingMethod])[0];
        this.recipeData.cookingMethod = formatedMethod;
      })
      .catch((error) => {
        if (error.response.status === 404) {
          this.errorMessages.push('Recipe Not Found.');
        } else {
          this.errorMessages.push('Could not get recipe details.');
        }
      });
  },
};
</script>

<style lang="scss" scoped>
.recipeList > li {
  border: none;
}
</style>
