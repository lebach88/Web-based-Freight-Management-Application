<template>
    <header class="p-3 bg-success">
      <div class="container">
        <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
  
          <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
            <li>
              <router-link to="/" class="nav-link px-2 text-white">Trang chủ</router-link>
            </li>
            <li>
              <router-link to="/product" class="nav-link px-2 text-white">Hàng hóa</router-link>
            </li>
          </ul>
  
          <div class="col-12 col-lg-auto text-white p-2">
            {{ message }}
          </div>
  
          <div class="text-end" v-if="!auth">
            <router-link to="/login" type="button" class="btn btn-outline-light me-2">Login</router-link>
            <router-link to="/register" type="button" class="btn btn-warning">Sign-up</router-link>
          </div>
  
          <div class="text-end" v-if="auth">
            <router-link to="/" @click="logout" class="btn btn-danger">Log-out</router-link>
          </div>
  
        </div>
      </div>
    </header>
  </template>
  <script>
  import {computed, ref} from "vue";
  import axios from "axios";
  import {useStore} from "vuex";
  
  export default {
    name: "NavbarComponent",
    setup(){
      const store = useStore();
      const auth = computed(() => store.state.auth);
      const message = computed(() => store.state.message); // access message
      const logout = async () =>{
        localStorage.removeItem('token');
        axios.defaults.headers.common['Authorization'] = '';
        await store.dispatch('setMessage','')
        await store.dispatch('setAuth',false);
      }
  
      return {
        auth,
        logout,
        message
      }
    }
  }
</script>