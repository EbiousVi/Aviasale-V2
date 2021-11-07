<template>
  <div>
    <div v-for="(flightDto, index) in flightsDto" :key="index" class="mb-2">
      <v-chip class="mr-2" color="green lighten-3" label
        ><strong>
          {{
            flightDto.price.value.toLocaleString("en-US", {
              style: "currency",
              currency: "RUB",
            })
          }}
        </strong>
      </v-chip>
      <v-chip
        label
        class="mr-2"
        :color="setColorByStatus(flightDto.flight.status)"
      >
        {{ flightDto.flight.status }}</v-chip
      >
      <v-chip label class="mr-2" color="grey lighten-1"
        ><span>{{ flightDto.flight.airportFrom }}</span>
        <v-icon class="mr-1" style="transform: rotate(45deg)">
          mdi-airplane
        </v-icon>
        <span>{{ flightDto.flight.airportTo }}</span></v-chip
      >
      <v-chip
        class="mr-2"
        close
        close-icon="mdi-cursor-default-click"
        label
        link
        @click="checkFreeSeats(flightDto.flight.flightId, conditions, index)"
        ><strong>Available seats </strong>&nbsp;<span v-if="update">
          {{ freeSeats[index] }}</span
        >
      </v-chip>
      <v-chip class="mr-2" label
        ><strong>Duration</strong> : {{ flightDto.flight.interval }}</v-chip
      >
      <v-chip class="mr-2" label
        ><strong>Range</strong> : {{ flightDto.distance }} km</v-chip
      >
      <v-chip class="mr-2" label
        ><strong>Aircraft</strong> :
        {{ JSON.parse(flightDto.aircraft.model).en }}
      </v-chip>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "ChipsGroup",
  props: {
    flightsDto: {
      type: Array,
      require: true,
    },
    conditions: {
      type: String,
      require: true,
    },
  },
  watch: {
    flightsDto: {
      immediate: true,
      handler(cur, old) {
        if (old !== cur) {
          this.update = false;
          for (let i = 0; i < this.flightsDto.length; i++) {
            this.freeSeats.push("");
          }
          return;
        }
      },
    },
  },
  mounted() {
    for (let i = 0; i < this.flightsDto.length; i++) {
      this.freeSeats.push("");
    }
  },
  data: () => ({
    freeSeats: [],
    update: false,
  }),
  methods: {
    checkFreeSeats(flightId, conditions, index) {
      (this.update = false),
        axios
          .get("http://localhost:37064/free-seats", {
            params: {
              flightId: flightId,
              conditions: conditions,
            },
          })
          .then((response) => {
            this.update = true;
            this.freeSeats[index] = response.data;
          });
    },
    setColorByStatus(val) {
      if (val === "Scheduled") return "info";
      if (val === "On Time") return "yellow";
      if (val === "Delayed") return "warning";
    },
  },
};
</script>

<style>
</style>