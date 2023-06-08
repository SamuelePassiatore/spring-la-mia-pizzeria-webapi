<script>
import PizzaComp from './PizzaComp.vue';
import axios from 'axios';
const BASE_API_URL = "http://localhost:8080/api/v1";

export default {
  data() {
    return {
      pizze: [],
      filtro: '',
      pizzeFiltrate: []
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
          if (this.filtro) {
            this.pizzeFiltrate = pizze.filter(pizza => {

              return pizza.nome.toLowerCase().includes(this.filtro.toLowerCase());
            });
          } else {
            this.pizzeFiltrate = pizze;
          }

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
  <div class="d-flex flex-column align-items-center">
    <h1 class="p-2">Pizzeria</h1>
    <router-link to="/store" class="btn btn-small btn-success m-2">Crea una pizza</router-link>
    <input type="text" v-model="filtro" placeholder="Filtra per nome" class="form-control m-2 w-50"
      @keyup.enter="getPizze()">
    <p v-if="pizzeFiltrate.length === 0" class="text-danger pt-4">Nessuna pizza trovata con questo nome.</p>
    <PizzaComp class="p-2" v-for="pizza in pizzeFiltrate" :pizza="pizza" />
  </div>
</template>

<style scoped></style>
