import Vue from "vue";
import Component from "vue-class-component";

// import {CreateElement, VNode} from "vue";

@Component
class App extends Vue {
  msg: string = 'This is our first Vue app';

  //TODO: Render function renders only <div class='App'></div>
  // public render(h: CreateElement): VNode {
  //   return (<div class='App'>
  //     <h1>{this.msg}</h1>
  //     <p>Something is here</p>
  //   </div>);
  // }
}

export default App;