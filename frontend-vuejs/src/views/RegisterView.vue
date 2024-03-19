<script lang="ts">
import {reactive} from "vue";
import axios from "axios";
import { useRouter } from "vue-router";

export default {
  name: 'RegisterView',
  setup() {
    const data = reactive({
      firstName: '',
      lastName: '',
      email:'',
      password:''
    });
    const router = useRouter();
    const submit = async () => {
      await axios.post('signup', data);
      await router.push('/login');
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
      <h1 class="h3 mb-3 fw-normal">Sign Up</h1>
      <div class="form-floating">
        <input v-model="data.firstName" type="text" class="form-control" placeholder="Enter">
        <label >Firstname</label>
      </div>
      <div class="form-floating">
        <input v-model="data.lastName" type="text" class="form-control"  placeholder="Enter">
        <label >Lastname</label>
      </div>
      <div class="form-floating">
        <input v-model="data.email" type="email" class="form-control"  placeholder="name@example.com">
        <label >Email address</label>
      </div>
      <div class="form-floating">
        <input v-model="data.password" type="password" class="form-control"  placeholder="Password">
        <label >Password</label>
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
