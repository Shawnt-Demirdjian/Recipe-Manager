<template lang="pug">
.recipe-card
  h2
    router-link.nav-link(v-bind:to="'/recipes/' + recipeData.id") {{ recipeData.title }}
  h4 By: {{ recipeData.author }}
  h3 {{ recipeData.category }} | {{ recipeData.cookingMethod }}
  p {{ recipeData.description }}
</template>

<script>
export default {
  name: 'Recipe Card',
  props: {
    recipeData: JSON,
  },
};
</script>

<style lang="scss" scoped>
.recipe-card {
  flex: 1 0 auto;
  padding: 1em;
  margin: 1em;
  border: 1px solid #ccc;
  box-shadow: 2px 2px 6px 0px rgba(0, 0, 0, 0.3);
  width: 15em;
}

.nav-link {
  padding: unset;
}
</style>
