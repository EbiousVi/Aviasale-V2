<template>
  <v-container style="padding: 0; margin: 0">
    <v-timeline v-for="(fl, index) in flights" :key="index">
      <!-- from -->
      <v-timeline-item fill-dot style="width: 600px">
        <template v-slot:icon>
          <v-icon dark> mdi-airplane-takeoff </v-icon>
        </template>
        <template v-slot:opposite>
          <strong>{{
            parseTime(fl.flight.departureDate, fl.airportFrom.timeZone)
          }}</strong>
          <br />
          <span>{{
            parseDate(fl.flight.departureDate, fl.airportFrom.timeZone)
          }}</span>
          <br />
          <span>Local-Time : {{ fl.airportFrom.timeZone }}</span>
        </template>
        <div>
          <strong>{{ fl.flight.airportFrom }}</strong>
          <br />
          <span>{{ JSON.parse(fl.airportFrom.city).en }}</span>
          <br />
          <span>{{ JSON.parse(fl.airportFrom.airportName).en }}</span>
        </div>
      </v-timeline-item>
      <!--in-->
      <v-timeline-item fill-dot>
        <template v-slot:icon>
          <v-icon dark style="transform: rotate(135deg)"> mdi-airplane </v-icon>
        </template>
        <template v-slot:opposite>
          <span>Duration : </span><strong> {{ fl.flight.interval }}</strong>
        </template>
        <div class="d-flex justify-end mt-2">
          <span> Distance : </span>
          <strong> {{ fl.distance }}km</strong>
        </div>
      </v-timeline-item>
      <!--To-->
      <v-timeline-item fill-dot style="width: 600px">
        <template v-slot:opposite>
          <strong>{{
            parseTime(fl.flight.arrivalDate, fl.flight.timeZone)
          }}</strong>
          <br />
          <span>{{
            parseDate(fl.flight.arrivalDate, fl.flight.timeZone)
          }}</span>
          <br />
          <span>Local-Time : {{ fl.airportTo.timeZone }}</span>
        </template>
        <template v-slot:icon>
          <v-icon dark> mdi-airplane-landing </v-icon>
        </template>
        <div>
          <strong>{{ fl.flight.airportTo }}</strong>
          <br />
          <span>{{ JSON.parse(fl.airportTo.city).en }}</span>
          <br />
          <span>{{ JSON.parse(fl.airportTo.airportName).en }}</span>
        </div>
      </v-timeline-item>
      <!--wait-->
      <v-timeline-item
        fill-dot
        color="orange"
        v-if="flights.length > 1 && index !== flights.length - 1"
      >
        <template v-slot:icon>
          <v-icon dark> mdi-clock </v-icon>
        </template>
        <template v-slot:opposite>
          <strong>{{ duration(index) }}</strong>
          <br />
        </template>
        <div class="d-flex flex-column align-end">
          <strong>Waiting at</strong>
          <span>
            {{ JSON.parse(fl.airportTo.city).en }}
          </span>
          <span>{{ JSON.parse(fl.airportTo.airportName).en }}</span>
        </div>
      </v-timeline-item>
    </v-timeline>
  </v-container>
</template>

<script>
import * as timeUtils from "../../Utils/timeUtils.js";

export default {
  name: "Timeline",
  data: () => ({}),
  props: {
    flights: {
      type: Array,
      require: true,
    },
  },
  methods: {
    duration(index) {
      const d1 = this.flights[index].flight.arrivalDate;
      const d2 = this.flights[index + 1].flight.departureDate;
      const date1 = Date.parse(d1);
      const date2 = Date.parse(d2);
      const diff = date2 - date1;
      const d = Math.floor(diff / (1000 * 60 * 60 * 24));
      const m = Math.floor((diff / (1000 * 60)) % 60);
      const h = Math.floor((diff / (1000 * 60 * 60)) % 24);
      if (d > 0) {
        return d + "D " + h + "H " + m + "M";
      } else {
        return h + "H " + m + "M";
      }
    },
    parseTime(date, timeZone) {
      return timeUtils.parseTime(date, timeZone);
    },
    parseDate(date, timeZone) {
      return timeUtils.parseDate(date, timeZone);
    },
  },
};
</script>

<style>
</style>