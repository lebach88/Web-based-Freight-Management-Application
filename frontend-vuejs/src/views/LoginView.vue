<script lang="ts">
import {reactive} from "vue";
import axios from "axios";
import router from "@/router";

export default {
  name: "LoginView",
  setup(){
    const data = reactive({
      email: '',
      password:''
    });
    const submit = async () =>{
      const response = await axios.post('login',data,{
        withCredentials:true});
      axios.defaults.headers.common['Authorization'] = `Bearer ${response.data.token}`
      localStorage.setItem('token', response.data.token);
      await router.push('/');
    }
    return{
      data,
      submit
    }
  }
}
</script>

<template>
  <main class="container">
    <form @submit.prevent="submit">
      <h1 class="h3 mb-3 fw-normal">Login</h1>

      <div class="form-floating">
        <input v-model="data.email" type="email" class="form-control" id="floatingInput" placeholder="name@example.com">
        <label for="floatingInput">Email address</label>
      </div>
      <div class="form-floating">
        <input v-model="data.password" type="password" class="form-control" id="floatingPassword" placeholder="Password">
        <label for="floatingPassword">Password</label>
      </div>

      <div class="checkbox mb-3">
        <label>
          <input type="checkbox" value="remember-me"> Remember me
        </label>
      </div>
      <button class="w-100 btn btn-lg btn-primary" type="submit">Sign in</button>
    </form>
  </main>
</template>
