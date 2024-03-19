<script>
import {ref, onMounted, computed} from 'vue'
import axios from "axios";
import {useStore} from "vuex";
export default {
  name: "HomeView",
  setup(){
    const message = ref('not login');
    const store = useStore();
    const auth = computed(() => store.state.auth)
    onMounted( async () => {
      try{
        const {data} = await axios.get('profile');
        message.value = data.username;
        await store.dispatch('setAuth',true);
      }catch (e) {
        await store.dispatch('setAuth',false);
      }
    })
    return{
      message,
      auth
    }
  }
}
</script>

<template>
<div class="container mt-5 text-center">
  <h3>{{auth ? message: 'not log in'}}</h3>
</div>
</template>
