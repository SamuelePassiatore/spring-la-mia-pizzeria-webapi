<script>
import PizzaComp from './PizzaComp.vue';
import axios from 'axios';
const BASE_API_URL = "http://localhost:8080/api/v1";

export default {
  data() {
    return {
      pizze: [],
    }
  },
  components: {
    PizzaComp
  },
  methods: {
    getPizze() {
      axios.get(BASE_API_URL + "/pizze")
        .then(res => {

          const pizze = res.data;
          console.log(pizze);
          this.pizze = pizze;
        })
        .catch(err => console.log(err));
    }
  },
  mounted() {
    this.getPizze();
  }
}

</script>

<template>
  <h1 class="text-center p-2">Pizzeria</h1>
  <PizzaComp class="text-center p-2" v-for="pizza in pizze" :pizza="pizza" />
</template>

<style scoped></style>
