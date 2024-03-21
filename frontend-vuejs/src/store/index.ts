
import {ActionContext, createStore} from 'vuex'

export default createStore({
  state: {
    auth: false,
    message: ''
  },
  getters: {
  },
  mutations: {
    setAuth(state: {message: any;auth: boolean}, auth: boolean){
      state.auth = auth;
    },
    setMessage(state, value) { // new mutation
      state.message = value;
    },
  },
  actions: {
    setAuth(context: ActionContext<any, any>, auth: boolean){
      context.commit('setAuth',auth);
    },
    setMessage({commit}, value) { // new action
      commit('setMessage', value);
    },
  },
  modules: {
  }
})
