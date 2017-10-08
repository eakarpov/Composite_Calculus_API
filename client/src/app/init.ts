import Vue from 'vue'
import App from './components/App/App.vue';
import store from './vuex/store';

class AppCore {
  private instance: Vue;

  private init() {
    this.instance = new Vue({
      el: '#app',
      store,
      render: h => h(App)
    })
  }

  constructor() {
    this.init();
  }
}

window.onload = () => {
  new AppCore();
};
