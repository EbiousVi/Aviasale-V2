<template>
  <div>
    <div class="d-flex flex-column align-center pt-2">
      <div class="d-flex mb-2">
        <v-btn @click="getCabinLayout" class="mr-2">Load Cabin Layout</v-btn>
        <v-btn
          color="primary"
          @click="checkIn"
          class="mr-2"
          :disabled="this.selectedSeat === '' || selectedSeat === undefined"
          >Check In</v-btn
        >
        <v-btn color="secondary" @click="close">Close</v-btn>
      </div>
      <v-alert
        class="mt-4"
        v-model="enableAlert"
        dismissible
        elevation="2"
        text
        type="error"
        >{{ alertMsg }}</v-alert
      >
      <div>
        <v-chip class="mr-1">{{ passengerDocDto.fareConditions }}</v-chip>
        <v-chip class="mr-1">{{ passengerDocDto.ticketNo }}</v-chip>
        <v-chip class="mr-1">{{ passengerDocDto.fullName }}</v-chip>
        <v-chip class="mr-1">{{ aircraft }} - {{ aircraftName }} </v-chip>
        <v-chip v-if="selectedSeat" color="info">
          Seat - {{ selectedSeat }}</v-chip
        >
      </div>
    </div>

    <div class="d-flex flex-column align-center mt-2">
      <div
        v-for="(row, rowIndex) in seats"
        :key="rowIndex"
        class="d-flex flex-column"
      >
        <v-chip-group v-model="selectedSeat">
          <div class="d-flex">
            <div
              v-for="(seat, seatIndex) in row"
              :key="seatIndex"
              style="padding: 0; margin: 0"
            >
              <v-chip
                active-class="primary text--accent-5"
                style="width: 64px"
                :value="seat.seatNo"
                :disabled="!seat.free"
                :color="setAisleColor(seat.seatNo)"
                >{{ seat.seatNo }}</v-chip
              >
            </div>
          </div>
        </v-chip-group>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { validity } from "../../Utils/token/validateToken";
import { bearer } from "../../Utils/token/bearer";
import { refreshTokens } from "../../Utils/token/refreshTokens";
export default {
  emit: ["close", "seatNo"],
  props: {
    passengerDocDto: {
      type: Object,
      require: true,
    },
    aircraft: {
      type: String,
      require: true,
    },
    aircraftName: {
      type: String,
      require: true,
    },
  },
  data() {
    return {
      selectedSeat: "",
      seats: [],
      enableAlert: false,
      alertMsg: "",
    };
  },
  methods: {
    checkIn() {
      const accessToken = this.$store.getters.getUser.accessToken;
      const refreshToken = this.$store.getters.getUser.refreshToken;
      const isValid = validity(accessToken);
      if (isValid) {
        axios
          .get("http://localhost:37064/boarding", {
            headers: {
              Authorization: bearer(accessToken),
            },
            params: {
              flightId: this.passengerDocDto.flightId,
              ticketNo: this.passengerDocDto.ticketNo,
              seatNo: this.selectedSeat,
            },
          })
          .then((response) => {
            if (response.status === 200) {
              this.$emit("seatNo", this.selectedSeat);
              this.$emit("close", false);
              this.selectedSeat = "";
            }
          })
          .catch((error) => {
            this.enableAlert = true;
            this.alertMsg =
              error.response !== undefined ? error.response.data : error;
            this.seats.reduce((a, seatDto) =>
              seatDto.find((item) => item.seatNo === this.selectedSeat)
            );
          });
      } else {
        const promise = refreshTokens(refreshToken);
        promise.then((result) => {
          if (result.status === 200) {
            this.$store.commit("setTokens", result.data);
            this.checkIn();
          } else if ((result.status === 403) | (result.status === 401)) {
            this.enableAlert = true;
            this.alertMsg = result.response.data;
          } else {
            this.enableAlert = true;
            this.alertMsg = result;
          }
        });
      }
    },
    getCabinLayout() {
      const accessToken = this.$store.getters.getUser.accessToken;
      const refreshToken = this.$store.getters.getUser.refreshToken;
      const isValid = validity(accessToken);
      if (isValid) {
        axios
          .get("http://localhost:37064/layoutService", {
            headers: {
              Authorization: bearer(accessToken),
            },
            params: {
              flightId: this.passengerDocDto.flightId,
              aircraft: this.aircraft,
              conditions: this.passengerDocDto.fareConditions,
            },
          })
          .then((response) => {
            this.seats = response.data;
          })
          .catch((error) => {
            this.enableAlert = true;
            this.alertMsg =
              error.response !== undefined ? error.response.data : error;
          });
      } else {
        const promise = refreshTokens(refreshToken);
        promise.then((result) => {
          if (result.status === 200) {
            this.$store.commit("setTokens", result.data);
            this.getCabinLayout();
          } else if ((result.status === 403) | (result.status === 401)) {
            this.enableAlert = true;
            this.alertMsg = result.response.data;
          } else {
            this.enableAlert = true;
            this.alertMsg = result;
          }
        });
      }
    },
    close() {
      this.$emit("close", false);
    },
    setAisleColor(val) {
      if (val === "AISLE") return "secondary";
    },
  },
};
</script>
<style>
</style>