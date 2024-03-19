<template>
  <Nav/>
  <router-view/>
</template>
<script>
import Nav from "@/components/NavbarComponent.vue";
export default {
  components: {Nav}
}
</script>
