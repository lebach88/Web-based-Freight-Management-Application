import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import LoginView from '@/views/LoginView.vue'
import HomeView from "@/views/HomeView.vue";
import ProductView from "@/views/ProductView.vue";
import RegisterView from "@/views/RegisterView.vue";

const routes: Array<RouteRecordRaw> = [
    {path:"/",component: HomeView},
    {
        path:"/product", 
        component: ProductView,
        meta: {
            requiresAuth: true
        }
    },
    {path: "/register", component: RegisterView},
    {path: "/login",component: LoginView},
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.requiresAuth)) {
    // Kiểm tra xem người dùng đã đăng nhập hay chưa
    if (!isLoggedIn()) {
      // Nếu chưa đăng nhập, chuyển hướng đến trang đăng nhập
      next({ path: '/login' });
    } else {
      next();
    }
  } else {
    next(); // Đảm bảo luôn gọi next()!
  }
});

export default router;

function isLoggedIn() {
  return !!localStorage.getItem('token');
}

