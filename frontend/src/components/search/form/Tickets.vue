<template>
  <v-menu offset-y :close-on-content-click="false" :nudge-bottom="5">
    <template v-slot:activator="{ attrs, on }">
      <v-text-field
        :value="`${noOfTickets}, ${condition}`"
        v-bind="attrs"
        v-on="on"
        readonly
        @change="setCondition"
      >
      </v-text-field>
    </template>
    <v-card class="pt-5">
      <v-icon color="red" @click="decrement" :disabled="noOfTickets === 0">
        mdi-minus
      </v-icon>
      {{ noOfTickets }}
      <v-icon color="green" @click="increment" :disabled="noOfTickets === 10">
        mdi-plus
      </v-icon>
      <v-select
        @change="$emit('condition', condition)"
        v-model="condition"
        :items="conditions"
        label="Fare conditions"
      ></v-select>
    </v-card>
  </v-menu>
</template>

<script>
export default {
  name: "Tickets",
  emits: ["noOfTickets", "condition"],
  data: () => ({
    noOfTickets: 1,
    condition: "Economy",
    conditions: ["Economy", "Comfort", "Business"],
  }),
  methods: {
    decrement() {
      this.noOfTickets--;
      this.$emit("noOfTickets", this.noOfTickets);
    },
    increment() {
      this.noOfTickets++;
      this.$emit("noOfTickets", this.noOfTickets);
    },
    setCondition() {
      this.$emit("noOfTickets", this.noOfTickets);
    },
  },
};
</script>
