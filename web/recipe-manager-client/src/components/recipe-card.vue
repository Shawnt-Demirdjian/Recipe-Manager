<template lang="pug">
.recipe-card
  h2 {{ data.title }}
  h4 By: {{ data.author }}
  h3 {{ data.category }} | {{ data.cookingMethod }}
  p {{ data.description }}
</template>

<script>
export default {
  name: 'Recipe Card',
  props: {
    data: JSON,
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
</style>
