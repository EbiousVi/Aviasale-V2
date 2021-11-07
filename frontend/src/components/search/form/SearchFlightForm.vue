<template>
  <v-container class="grey darken" style="border-radius: 5px">
    <v-row>
      <v-col md="3">
        <v-autocomplete
          v-model="form.from"
          item-value="code"
          label="Select departure"
          :items="departure"
          :item-text="getItemText"
          :error="fromErr"
          :error-messages="fromErrMsg"
          :rules="[(v) => v.length !== 0 || 'Select departure']"
          @change="fromValid"
        >
        </v-autocomplete>
      </v-col>
      <v-col md="3">
        <v-autocomplete
          v-model="form.to"
          item-value="code"
          label="Select destination"
          :items="arrival"
          :item-text="getItemText"
          :error="toErr"
          :error-messages="toErrMsg"
          :rules="[(v) => v.length !== 0 || 'Select destination']"
          @change="toValid"
        >
        </v-autocomplete>
      </v-col>
      <v-col md="2">
        <calendar
          label="Depart date"
          :master="true"
          @date="getDepartDate"
          style="margin: 0; padding: 0"
        ></calendar>
      </v-col>
      <v-col md="2">
        <calendar
          label="Return date"
          :master="false"
          :masterDate="form.departDate"
          :disable="form.tripType === 'ONEWAY'"
          @date="getReturnDate"
          style="margin: 0; padding: 0"
        ></calendar>
      </v-col>
      <v-col md="2">
        <tickets
          @condition="getCondition"
          @noOfTickets="getNoOfTickets"
        ></tickets>
      </v-col>
    </v-row>
    <v-row style="padding: 0; margin: 0">
      <v-radio-group v-model="form.tripType" row>
        <v-radio label="Oneway Trip" value="ONEWAY"></v-radio>
        <v-radio label="Round Trip" value="ROUND"></v-radio>
      </v-radio-group>
      <v-divider vertical class="mr-2"></v-divider>
      <v-checkbox
        v-model="isDirect"
        label="Direct Only"
        class="mr-2"
      ></v-checkbox>
      <v-btn class="mt-4 mr-2" color="primary" @click="validateForm">
        <span class="mr-1">Search</span>
        <v-icon color="white"> mdi-cloud-upload </v-icon>
      </v-btn>
      <v-chip class="mt-5 ml-5" label>
        {{ time }}
        {{ Intl.DateTimeFormat().resolvedOptions().timeZone }}</v-chip
      >
      <v-progress-linear
        v-if="waiting"
        color="primary"
        indeterminate
        rounded
        height="6"
      ></v-progress-linear>
    </v-row>
    <v-alert
      v-model="enableAlert"
      dismissible
      elevation="2"
      dense
      type="info"
      class="mt-2"
      ><div class="d-flex justify-center">
        {{ alertMsg }}
      </div></v-alert
    >
  </v-container>
</template>

<script>
import axios from "axios";
import Tickets from "./Tickets.vue";
import Calendar from "./Calendar.vue";

export default {
  name: "SearchFlightForm",
  components: { Tickets, Calendar },
  emits: ["flights", "noOfTickets", "tripType", "wait", "conditions"],
  async mounted() {
    this.getAirports();
  },
  beforeDestroy() {
    // prevent memory leak
    clearInterval(this.interval);
  },
  created() {
    this.interval = setInterval(() => {
      this.time = Intl.DateTimeFormat("ru-Ru", {
        hour: "numeric",
        minute: "numeric",
        second: "numeric",
      }).format();
    }, 1000);
  },
  data() {
    return {
      form: {
        from: "",
        to: "",
        departDate: "",
        returnDate: "",
        zoneId: Intl.DateTimeFormat().resolvedOptions().timeZone,
        condition: "Economy",
        noOfTickets: 1,
        tripType: "ONEWAY",
        flightType: "",
      },
      isDirect: false,
      enableAlert: false,
      alertMsg: "",
      interval: null,
      time: null,
      departure: [],
      arrival: [],
      fromErr: false,
      toErr: false,
      fromErrMsg: "",
      toErrMsg: "",
      onewayTripURL: "http://localhost:37064/one-way/search",
      roundTripURL: "http://localhost:37064/round-trip/search",
      waiting: false,
    };
  },
  methods: {
    changeFlightTypeVal() {
      if (this.isDirect === true) {
        this.form.flightType = "DIR";
      } else {
        this.form.flightType = "ALL";
      }
    },
    fromValid() {
      if (this.form.from.length !== 0) {
        this.fromErr = false;
        this.fromErrMsg = "";
      }
    },
    toValid() {
      if (this.form.to.length !== 0) {
        this.toErr = false;
        this.toErrMsg = "";
      }
    },
    validateForm() {
      if (this.form.from.length === 0) {
        this.fromErr = true;
      }
      if (this.form.to.length === 0) {
        this.toErr = true;
        return;
      }
      if (this.form.from === this.form.to) {
        this.fromErr = true;
        this.toErr = true;
        this.toErrMsg = "Same departure and distation airports!";
        this.fromErrMsg = "Same departure and distation airports!";
        return;
      }
      this.submitForm();
    },
    getNoOfTickets(val) {
      this.form.noOfTickets = val;
    },
    getCondition(val) {
      this.form.condition = val;
    },
    getDepartDate(val) {
      this.form.departDate = val;
    },
    getReturnDate(val) {
      this.form.returnDate = val;
    },
    getItemText(airport) {
      return `${airport.city} ${airport.name} - ${airport.code}`;
    },
    getAirports() {
      axios
        .get("http://localhost:37064/airports")
        .then((response) => {
          this.departure = response.data;
          this.arrival = response.data;
        })
        .catch((error) => {
          this.waiting = false;
          this.alertMsg =
            error.response === undefined ? 'Try to reload page' : error;
          this.enableAlert = true;
        });
    },
    submit(URL) {
      this.waiting = true;
      this.changeFlightTypeVal();
      axios
        .post(URL, this.form)
        .then((response) => {
          if (response.status === 200) {
            this.enableAlert = false;
            this.waiting = false;
            this.$emit("flights", response.data);
            this.$emit("noOfTickets", this.form.noOfTickets);
            this.$emit("tripType", this.form.tripType);
            this.$emit("conditions", this.form.condition);
            this.$emit("wait", false);
          }
        })
        .catch((error) => {
          this.waiting = false;
          this.alertMsg =
            error.response !== undefined ? error.response.data : error;
          this.enableAlert = true;
        });
    },
    submitForm() {
      if (this.form.tripType === "ONEWAY")
        return this.submit(this.onewayTripURL);
      if (this.form.tripType === "ROUND") return this.submit(this.roundTripURL);
    },
  },
};
</script>

<style scoped>
</style>
