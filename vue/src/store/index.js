import Vuex from 'vuex'
import Vue from "vue";


Vue.use(Vuex);

//准备actions——用于响应组件间的动作
const actions = {}

//准备mutations——用于操作数据
const mutations = {
    SetIsHeaderShowTrue(state){
        state.isHeaderShow=true
    },
    SetIsHeaderShowFalse(state){
        state.isHeaderShow=false
    }
}

//准备state——用于存储数据
const state = {
    isHeaderShow: true,
}

//计算属性
const getters = {

}

//创建并暴露store
export default new Vuex.Store({
    actions,
    mutations,
    state,
    getters,

})
