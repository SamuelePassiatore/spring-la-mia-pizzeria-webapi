<script>
import axios from 'axios';
const BASE_API_URL = "http://localhost:8080/api/v1";
export default {
    data() {
        return {
            open: false,
            deleted: false
        }
    },
    methods: {
        deletePizza(id) {
            console.log("delete pizza " + id);
            axios.delete(BASE_API_URL + "/pizze/delete/" + id)
                .then(res => this.deleted = true)
                .catch(err => console.log(err));
        }
    },
    props: {
        pizza: {}
    }
}
</script>

<template>
    <li v-if="!deleted">
        <span @click="open = !open" role="button">
            {{ pizza.nome }} - {{ pizza.prezzo }}€
        </span>
        <button class="btn btn-small btn-danger ms-3" @click="deletePizza(pizza.id)">Elimina</button>
        <div v-if="open">
            {{ pizza.descrizione }}
        </div>
    </li>
</template>

<style></style>