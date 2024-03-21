import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import 'bootstrap/dist/css/bootstrap.css'
import './interceptor/axios'
import axios from 'axios'

async function checkAuthentication() {
    const token = localStorage.getItem('token');
    if (token != null) {
        const response = await axios.get('profile', {
            headers: {
              'Authorization': `Bearer ${token}` // Đính kèm token vào header Authorization
            }
        });
        await store.dispatch('setMessage', response.data.username); // update message
        await store.dispatch('setAuth', true);
    } else {
      await store.dispatch('setAuth', false);
    }
  }
  
checkAuthentication().then(() => {
    createApp(App).use(store).use(router).mount('#app');
});
