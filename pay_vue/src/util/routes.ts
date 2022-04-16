import {createRouter, createWebHashHistory} from 'vue-router'
import store from "./store";

const routes = [
    {path: '/', component: () => import('./../pages/Index.vue')},
    {path: '/login', component: () => import('./../pages/Login.vue')},
    {path: '/test-pay', component: () => import('./../pages/PayTest.vue')},
    {
        path: '/e-login', component: () => import('./../pages/merchant/Login.vue'), meta: {
            title: '商户登录'
        }
    },
    {
        path: '/merchant', component: () => import('./../pages/merchant/Index.vue'), meta: {
            title: '商户后台'
        }
    },
    {
        path: '/main',
        component: () => import('./../pages/main/Layout.vue'),
        children: [
            {path: '', component: () => import('./../pages/main/Index.vue')},
            {path: 'history', component: () => import('./../pages/main/History.vue')},
            {path: 'pay', component: () => import('./../pages/main/Pay.vue')},
            {path: 'setting', component: () => import('./../pages/main/Setting.vue')},
        ]
    }
];
const router = createRouter({
    // 4. 内部提供了 history 模式的实现。为了简单起见，我们在这里使用 hash 模式。
    history: createWebHashHistory(),
    routes, // `routes: routes` 的缩写
})

router.beforeEach((to,from,next)=>{
    if(to.path == '/e-login' && store.merchantInfo){
        next('/merchant')
        return
    }
    next()
})
router.beforeResolve(to => {
    let title = '魔法支付'
    if (to.meta && to.meta.title) {
        title = to.meta.title + " - " + title;
    }

    window.document.title = title;
    return true
})

export default router