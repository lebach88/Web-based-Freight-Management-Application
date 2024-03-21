import store from "@/store";
import axios from "axios";
import { reactive } from "vue";

axios.defaults.baseURL = 'http://localhost:8000/api/v1/auth/';

let refresh = false;
const data = reactive({
    token: localStorage.getItem('token')
})
axios.interceptors.response.use(resp => resp, async error =>{

    if (error.response.status === 403 && !refresh ){
        refresh = true
        const response = await axios.post('refresh',data,{withCredentials: true});
        if (response.status === 200){
            axios.defaults.headers.common['Authorization'] = `Bearer ${response.data.token}`;
            return axios(error.config)
        }
    }
    refresh = false;
    return error;
})