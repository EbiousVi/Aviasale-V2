<template>
  <div class="mt-4">
    <search-flight-form
      @flights="getFligthsDto"
      @noOfTickets="getNoOfTickets"
      @conditions="getConditions"
      @tripType="getTripType"
      @wait="isLoaded = true"
    >
    </search-flight-form>
    <cards
      v-if="isLoaded"
      :flightsDto="flightsDto"
      :noOfTickets="noOfTickets"
      :tripType="tripType"
      :conditions="conditions"
    ></cards>
  </div>
</template>

<script>
import Cards from "./Cards.vue";
import SearchFlightForm from './form/SearchFlightForm.vue';


export default {
  name: "Search",
  components: {  Cards, SearchFlightForm },
  data() {
    return {
      isLoaded: false,
      flightsDto: {},
      noOfTickets: 0,
      tripType: "",
      conditions: "",
    };
  },
  methods: {
    getTripType(tripType) {
      this.tripType = tripType;
    },
    getFligthsDto(flightsDto) {
      this.flightsDto = flightsDto;
    },
    getNoOfTickets(noOfTickets) {
      this.noOfTickets = noOfTickets;
    },
    getConditions(conditions) {
      this.conditions = conditions;
    },
  },
};
</script>

<style>
</style>