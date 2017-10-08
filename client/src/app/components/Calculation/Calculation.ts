import Component from "vue-class-component";
import Vue from "vue";

@Component
class Calculation extends Vue {
  buttonName: string = 'Submit';

  formListener =  () => {
    console.log('Submitted!');
    alert('You submitted the form!');
  }
}

export default Calculation;