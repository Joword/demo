import { createRouter, createWebHistory } from "vue-router";
import VariantView from '@/views/VariantView.vue';
// import ChatView from '@/views/ChatView.vue'
import { checkLogin } from "@/api/request";
import { Message } from '@/utils/utils';
// import { useLoadingBar } from "naive-ui";

const routers = [
    {
        path: '/',
        name: 'home',
        component: () => import('@/views/HomeView.vue'),
    },
    {
        path: '/register',
        name : 'register',
        component: () => import('@/components/submodules/register.vue'),
    },
    {
        path: '/error',
        name: 'error',
        component: () => import('@/components/submodules/error.vue'),
    },
    {
        path: '/forgot',
        name: 'forgottenPwd',
        component: () => import('@/components/submodules/forgotten.vue'),
    },
    {
        path: '/changePwd',
        name: 'changePwd',
        component: () => import('@/components/submodules/changePwd.vue')
    },
    {
        path: '/login',
        name: 'login',
        component: () => import('@/components/submodules/login.vue'),
    },
    {
        path: '/variant/:variantId',
        name: 'variant',
        component: VariantView,
        // meta: {
        //     requireAuth: true,
        //     roles: ['user', 'admin']
        // }
    },{
        path: '/paper',
        name: 'paper',
        component: import('@/views/ChatView.vue'),
        // meta: {
        //     requireAuth: true,
        //     roles: ['user', 'admin']
        // }
    },{
        path: '/about',
        name: 'about',
        component: () => import('@/views/AboutView.vue'),
        // meta: {
        //     requireAuth: true,
        //     roles: ['user', 'admin']
        // }
    },{
        path: '/tools',
        name: 'tools',
        component: () => import('@/views/ToolsView.vue'),
        // meta: {
        //     requireAuth: true,
        //     roles: ['user', 'admin']
        // }
    },{
        path: '/gene/:gene',
        name: 'gene',
        component: () => import('@/views/GeneView.vue'),
    },{
        path: '/pmid/:pmid',
        name: 'pmid',
        component: () => import('@/views/PmidView.vue')
    }, {
        path: '/manager',
        name: 'manager',
        component: () => import('@/components/manager.vue')
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes: routers,
    scrollBehavior() { return { top: 0 } }
})

// let isRequestTokenStatus = false;
// router.beforeEach(async (to, from, next) => {
//     if(to.path === '/' || to.path === '/login') {
//         next();
//     }else{
//         if(!isRequestTokenStatus) {
//             const response = await checkLogin();
//             console.log(response)
//             if(response.data.code === 2004) {
//                 Message.error('请先登录');
//                 next('/login');
//             }else{
//                 isRequestTokenStatus = true;
//                 next();
//             }
//         }else{
//             next();
//         }
//     }
// })

export default router;