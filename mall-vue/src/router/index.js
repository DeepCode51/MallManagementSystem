import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Register from '../views/Register.vue'
import axios from '../axios'
import store from '../store'

Vue.use(VueRouter)

const routes = [{
        path: '/',
        name: 'Home',
        component: Home
    },
    {
        path: '/login',
        name: 'login',
        component: () =>
            import ('../views/Login.vue')
    }, {
        path: '/register',
        name: 'Register',
        component: Register
    }, {
        path: '/huang',
        name: 'huang',
        component: () =>
            import ('../views/common/huang.vue')
    }, {
        path: '/test',
        name: 'test',
        component: () =>
            import ('../views/common/test.vue')
    }, {
        path: '/detail',
        name: 'detail',
        component: () =>
            import ('../views/DetailPage.vue')
    }, {
        path: '/mycollection',
        name: 'mycollection',
        component: () =>
            import ('../views/MyCollection.vue')
    },
    {
        path: '/myAddress',
        name: 'myAddress',
        component: () =>
            import ('../views/AddressList.vue')
    }, 
	{
        path: '/myorder',
        name: 'myorder',
        component: () =>
            import ('../views/MyOrder.vue')
    }, {
        path: '/shoppingcart',
        name: 'shoppingcart',
        component: () =>
            import ('../views/ShoppingCart.vue')
    }, {
        path: '/mysoftware',
        name: 'mysoftware',
        component: () =>
            import ('../views/MySoftware.vue')
    }, {
        path: '/software',
        name: 'software',
        component: () =>
            import ('../views/Software.vue')
    }, {
        path: '/payment',
        name: 'payment',
        component: () =>
            import ('../views/Payment.vue')
    }, {
        path: '/paymentsuccessful',
        name: 'paymentsuccessful',
        component: () =>
            import ('../views/PaymentSuccessful.vue')
    }, {
        path: '/paymentfail',
        name: 'paymentfail',
        component: () =>
            import ('../views/PaymentFail.vue')
    }, {
        path: '/personalcenter',
        name: 'personalcenter',
        component: () =>
            import ('../views/PersonalCenter.vue')
    }, {
        path: '/messageCenter',
        name: 'messageCenter',
        component: () =>
            import ('../views/MessageCenter.vue')
    }, {
        path: '/journalism',
        name: 'journalism',
        component: () =>
            import ('../views/Journalism.vue')
    }, {
        path: '/bcakstage',
        name: 'bcakstage',
        component: () =>
            import ('../views/background/Backstage.vue'),
        children: [{
                path: '/index',
                name: 'index',
                component: () =>
                    import ('../views/background/Index.vue')
            }, {
                path: '/aboutus',
                name: 'aboutus',
                component: () =>
                    import ('../views/background/AboutUs.vue')
            },
            {
                path: '/hotSaleSoftware',
                name: 'hotSaleSoftware',
                component: () =>
                    import ('../views/background/HotSaleSoftware.vue')
            }, {
                path: '/hotSoftware',
                name: 'hotSoftware',
                component: () =>
                    import ('../views/background/HotSoftware.vue')
            }, {
                path: '/clothes',
                name: 'clothes',
                component: () =>
                    import ('../views/background/Clothes.vue')
            }, {
                path: '/computer',
                name: 'computer',
                component: () =>
                    import ('../views/background/Computer.vue')
            }, {
                path: '/phone',
                name: 'phone',
                component: () =>
                    import ('../views/background/Phone.vue')
            }, {
                path: '/shoes',
                name: 'shoes',
                component: () =>
                    import ('../views/background/Shoes.vue')
            }, {
                path: '/userList',
                name: 'userList',
                component: () =>
                    import ('../views/background/UserList.vue')
            }, {
                path: '/stats',
                name: 'stats',
                component: () =>
                    import ('../views/background/Stats.vue')
            }, {
                path: '/broken',
                name: 'broken',
                component: () =>
                    import ('../views/background/Broken.vue')
            }, {
                path: '/column',
                name: 'column',
                component: () =>
                    import ('../views/background/Column.vue')
            }, {
                path: '/comment',
                name: 'comment',
                component: () =>
                    import ('../views/background/Comment.vue')
            }, {
                path: '/order',
                name: 'order',
                component: () =>
                    import ('../views/background/Order.vue')
            },{
                path: '/config',
                name: '轮播图管理',
                component: () =>
                    import ('../views/config/index.vue')
            },{
                path: '/chat',
                name: '在线客服',
                component: () =>
                    import ('../views/chat/index.vue')
            }
        ]
    }, {
        path: '/sort',
        name: 'sort',
        component: () =>
            import ('../views/Sort.vue'),
        children: [{
            path: '/sortClothes',
            name: 'sortClothes',
            component: () =>
                import ('../views/sort/SortClothes.vue')
        }, {
            path: '/sortSports',
            name: 'sortSports',
            component: () =>
                import ('../views/sort/SortSports.vue')
        }, {
            path: '/sortComputer',
            name: 'sortComputer',
            component: () =>
                import ('../views/sort/SortComputer.vue')
        }, {
            path: '/sortPhone',
            name: 'sortPhone',
            component: () =>
                import ('../views/sort/SortPhone.vue')
        }, {
            path: '/sortShoes',
            name: 'sortShoes',
            component: () =>
                import ('../views/sort/SortShoes.vue')
        }]
    }, {
        path: '*',
        name: '404',
        component: () =>
            import ('../views/common/Error404.vue')
    },

]

const router = new VueRouter({
    mode: 'history', //去地址中的#
    routes
})


//路由守卫
router.beforeEach((to, from, next) => {

    let token = localStorage.getItem("token")

    if (to.path == '/login') {
        next()
    } else if (to.path == '/register') {
        next()
    } else if (!token) {
        next({ path: '/login' })
    } else if (token) {
        axios.get("/user/auth", {
            headers: {
                Authorization: localStorage.getItem("token")
            }
        }).then(res => {
            //console.log(res.data.data)
            // 拿到用户权限
            store.commit("setPermList", res.data.data.authoritys)
        })
    }
    next();
})




export default router
