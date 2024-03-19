import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import LoginView from '@/views/LoginView.vue'
import HomeView from "@/views/HomeView.vue";
import ProductView from "@/views/ProductView.vue";
import RegisterView from "@/views/RegisterView.vue";

const routes: Array<RouteRecordRaw> = [
    {path:"/",component: HomeView},
    {path:"/product", component: ProductView},
    {path: "/register", component: RegisterView},
    {path: "/login",component: LoginView}
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
