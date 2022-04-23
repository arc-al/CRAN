import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: "/manage",
    component: ()=> import("../views/manage"),
    redirect: "/manage/home",
    children: [
      { path: "home",  name: "首页", component: ()=> import("../views/Home"),},
      { path: "BaseStation",  name: "基站信息", component: ()=> import("../views/BaseStation"),},
      { path: "Terminal",  name: "终端信息", component: ()=> import("../views/Terminal"),},
      { path: "Mec",  name: "MEC信息", component: ()=> import("../views/Mec"),},
      { path: "Slicing",  name: "切片信息", component: ()=> import("../views/Slicing"),},
      { path: "SLA",  name: "SLA信息", component: ()=> import("../views/SLA"),},
      { path: "SLASelect",  name: "适配性分析", component: ()=> import("../views/selectSla"),},
      { path: "map",  name: "百度地图", component: ()=> import("../views/Map"),},
    ]
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
