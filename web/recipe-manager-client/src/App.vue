<template lang="pug">
nav.navbar.navbar-expand-md.navbar-dark.bg-primary
  .container-fluid
    a.navbar-brand(href="#") Recipe Manager
    button.navbar-toggler(
      type="button",
      data-bs-toggle="collapse",
      data-bs-target="#navbarSupportedContent",
      aria-controls="navbarSupportedContent",
      aria-expanded="false",
      aria-label="Toggle navigation"
    )
      span.navbar-toggler-icon
    #navbarSupportedContent.collapse.navbar-collapse
      ul.navbar-nav.me-auto.mb-2.mb-lg-0
        li.nav-item
          router-link.nav-link(to="/") Home
        li.nav-item
          router-link.nav-link(to="/create-recipe") Create Recipe
        li.nav-item
          router-link.nav-link(to="/search-recipes") Search Recipes
<router-view />
</template>

<style lang="scss" scoped>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

.nav-item a.router-link-exact-active {
  color: #42b983 !important;
}
</style>
