import Vue from "vue";
import Component from "vue-class-component";

import Calculation from "../Calculation/Calculation.vue";

@Component({
  components: {
    calculation: Calculation
  }
})
class App extends Vue {
  title: string = 'Web interface for composite calculations';
}

export default App;