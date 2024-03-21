<template>
  <main class="container">
    <div class="pt-3">
      <form @submit.prevent="submit">
      <h1 class="h3 mb-3 card-title text-center">Đăng nhập</h1>
      <div class="form-floating form-group">
        <input v-model="data.email" type="email" class="form-control form-control-lg" :class="{ 'is-invalid': errors.email }"  placeholder="name@example.com" @input="onChange('email')">
        <label>Email address</label>
        <p v-if="errors.email" class="text-danger label"> {{ errors.email }}</p>
      </div>
      <div class="form-floating form-group">
        <input v-model="data.password" type="password" class="form-control form-control-lg" :class="{ 'is-invalid': errors.password }"  placeholder="password" @input="onChange('password')">
        <label>Password</label>
        <p v-if="errors.password" class="text-danger label"> {{ errors.password }}</p>
      </div>
      <button class="w-100 btn btn-lg btn-success" type="submit">Đăng nhập</button>
      </form>
    </div>
  </main>
</template>

<script lang="ts">
import {reactive} from "vue";
import axios, { AxiosError } from "axios";
import { useRouter } from "vue-router";

type UserFormLogin = 'email' | 'password';

export default {
  name: "LoginView",
  mounted(){
    document.title = 'Đăng nhập'
  },
  setup(){
    const data = reactive({
      email: '',
      password: ''
    });
    const errors = reactive<Record<UserFormLogin, string>>({
      email: '',
      password: ''
    });
    const router = useRouter();
    const onChange = (field: UserFormLogin) =>{
      errors[field] = '';
    }
    const submit = async () =>{
      if(!data.email) errors.email = 'Email là rỗng.';
      if(!data.password) errors.password = 'Password là rỗng.';

      if(Object.values(errors).every(error => !error)) {
        const response = await axios.post('login',data,{withCredentials:true});
        if (response.status === 200) {
          axios.defaults.headers.common['Authorization'] = `Bearer ${response.data.token}`
          localStorage.setItem('token', response.data.token);
          await router.push('/');
        } else {
          errors.email = 'Email hoặc mật khẩu không đúng.';
          errors.password = 'Email hoặc mật khẩu không đúng.';
        }
      }
    }
    return{
      data,
      submit,
      errors,
      onChange
    }
  }
}
</script>
<style scoped>
.is-invalid {
  border-color: red;
}
</style>
