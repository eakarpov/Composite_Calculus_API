import Vue from 'vue';
import App from './App.vue';
import store from './vuex/store';

window.onload = () => {
  new Vue({
    el: '#app',
    store,
    render: h => h(App)
  });
};