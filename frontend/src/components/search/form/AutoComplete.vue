<template>
  <v-autocomplete
    v-model="value"
    @change="$emit('complete', value)"
    :items="items"
    :item-text="getItemText"
    item-value="code"
    :label="label"
    :error="isErrEnable"
    :error-messages="errMsg"
    :rules="[(v) => v.length !== 0 || errMsg]"
  >
  </v-autocomplete>
</template>

<script>
import axios from "axios";
export default {
  name: "AutoComplete",
  emits: ["complete"],
  props: {
    errMsg: {
      type: String,
    },
    label: {
      type: String,
      require: true,
    },
  },
  async mounted() {
    this.getAirports();
  },
  methods: {
    getItemText(airport) {
      return `${airport.city} ${airport.name} - ${airport.code}`;
    },
    getAirports() {
      axios.get("http://localhost:37064/airports", {}).then((response) => {
        this.departure = response.data;
        this.arrival = response.data;
      });
    },
  },
  data() {
    return {
      value: "",
      items: [],
      isErrEnable: false,
    };
  },
};
</script>

<style>
</style>