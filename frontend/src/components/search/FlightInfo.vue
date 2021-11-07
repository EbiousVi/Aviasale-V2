<template>
  <v-container>
    <v-container class="d-flex justify-space-between">
      <div class="departure-date">
        <v-tooltip open-delay="100" right>
          <template v-slot:activator="{ on, attrs }">
            <div v-bind="attrs" v-on="on">
              <div>
                <strong>{{
                  parseTime(
                    flightsDto[0].flight.departureDate,
                    flightsDto[0].airportFrom.timeZone
                  )
                }}</strong>
                <br />
              </div>
              <div>
                <span>{{ parseDate(flightsDto[0].flight.departureDate) }}</span>
              </div>
            </div>
          </template>
          Local Time - {{ flightsDto[0].airportFrom.timeZone }}
        </v-tooltip>
      </div>

      <div class="flights-info">
        <div v-if="flightsDto.length - 1 !== 0" class="d-flex justify-center">
          <span>{{ flightsDto.length - 1 }} </span> &nbsp;
          <span style="color: orange">stop</span> &nbsp; at &nbsp;
          <strong>{{ countTransfers(flightsDto) }}</strong>
        </div>
        <div v-if="flightsDto.length - 1 === 0">
          <span style="color: green">Direct flight</span>
        </div>
        <div class="d-flex justify-center">
          <div v-for="(fl, i) in flightsDto" :class="i" :key="i">
            <v-tooltip open-delay="100" left>
              <template v-slot:activator="{ on, attrs }">
                <strong color="primary" dark v-bind="attrs" v-on="on">
                  {{ fl.flight.airportFrom }}
                </strong>
              </template>
              <span
                >{{ JSON.parse(fl.airportFrom.airportName).en }} -
                {{ JSON.parse(fl.airportFrom.city).en }}
              </span>
            </v-tooltip>
            <v-icon class="mr-1" style="transform: rotate(45deg)">
              mdi-airplane
            </v-icon>
            <v-tooltip open-delay="100" right>
              <template v-slot:activator="{ on, attrs }">
                <strong color="primary" dark v-bind="attrs" v-on="on">
                  {{ fl.flight.airportTo }}
                </strong>
              </template>
              <span
                >{{ JSON.parse(fl.airportTo.airportName).en }} -
                {{ JSON.parse(fl.airportTo.city).en }}
              </span>
            </v-tooltip>
            <v-icon
              class="mr-1"
              color="orange"
              v-if="i !== flightsDto.length - 1"
            >
              mdi-clock
            </v-icon>
          </div>
        </div>
      </div>

      <div class="arrival-date">
        <v-tooltip open-delay="100" left>
          <template v-slot:activator="{ on, attrs }">
            <div v-bind="attrs" v-on="on">
              <div>
                <strong>{{
                  parseTime(
                    flightsDto[flightsDto.length - 1].flight.arrivalDate,
                    flightsDto[flightsDto.length - 1].airportTo.timeZone
                  )
                }}</strong>
              </div>
              <div>
                <span>{{
                  parseDate(
                    flightsDto[flightsDto.length - 1].flight.arrivalDate
                  )
                }}</span>
              </div>
            </div>
          </template>
          Local Time -
          {{ flightsDto[flightsDto.length - 1].airportTo.timeZone }}
        </v-tooltip>
      </div>
    </v-container>
  </v-container>
</template>

<script>
import * as timeUtils from "../../Utils/timeUtils.js";

export default {
  name: "FlightInfo",
  props: {
    flightsDto: {
      type: Array,
      require: true,
    },
  },
  methods: {
    countTransfers(flightsDto) {
      let transfers = [];
      for (let i = 0; i < flightsDto.length; i++) {
        transfers.push(flightsDto[i].flight.airportTo);
      }
      return transfers.slice(0, -1).toString();
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
