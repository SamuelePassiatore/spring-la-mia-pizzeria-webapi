<script>
import axios from 'axios';
const BASE_API_URL = "http://localhost:8080/api/v1";
export default {
    data() {
        return {
            pizza: {
                nome: "",
                descrizione: "",
                foto: "",
                prezzo: ""
            }
        };
    },
    methods: {
        createPizza() {
            console.log("pre create", JSON.stringify(this.pizza));
            axios.post(BASE_API_URL + "/pizze/store", this.pizza)
                .then(res => {
                    this.$router.push('/');
                })
                .catch(err => console.log(err));
        }
    },
    mounted() {
        console.log("pizza creata mounted");
    }
}
</script>

<template>
    <div class="p-3 text-center">
        <h1>Crea una pizza</h1>
        <form @submit.prevent="createPizza">
            <label for="nome">Nome</label><br>
            <input type="text" name="nome" v-model="pizza.nome">
            <br>
            <label for="descrizione">Descrizione</label><br>
            <input type="text" name="descrizione" v-model="pizza.descrizione">
            <br>
            <label for="prezzo">Foto</label><br>
            <input type="url" name="foto" v-model="pizza.foto">
            <br>
            <label for="prezzo">Price</label><br>
            <input type="number" name="prezzo" v-model="pizza.prezzo">
            <br>
            <input type="submit" value="CREATE" class="btn btn-small btn-success mt-3 me-2">
            <RouterLink class="btn btn-secondary mt-3" to="/">INDIETRO</RouterLink>
        </form>
    </div>
</template>

<style></style>