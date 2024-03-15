import { createRouter, createWebHistory } from "vue-router";
// import { useLoadingBar } from "naive-ui";

const routers = [
    {
        path: '/',
        name: 'home',
        component: () => import('@/views/HomeView.vue'),
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes: routers,
    scrollBehavior() { return { top: 0 } }
})

export default router;