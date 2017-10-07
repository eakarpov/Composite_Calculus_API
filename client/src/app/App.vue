<template lang="jade">
    div.my-component
        h1 {{msg}}
</template>
<script>
  export default {
    data() {
      return {
        msg: 'This is our first Vue app'
      }
    }
  }
</script>
<style lang="sass" scoped>
    .my-component
        font: 100% Helvetica, font-serif
</style>