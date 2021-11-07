<template>
  <v-container>
    <div class="d-flex justify-center mb-4">
      <v-btn style="width: 50%" class="primary" @click="getBookingDto"
        >Watch Booking</v-btn
      >
    </div>
    <v-alert
      class="mt-2"
      v-model="enableAlert"
      dismissible
      elevation="2"
      dense
      type="info"
      ><div class="d-flex justify-center">
        {{ alertMsg }}
      </div></v-alert
    >
    <v-card
      v-for="(bookingDto, bookingIndex) in bookingDtos"
      :key="bookingIndex"
    >
      <v-card-title style="background-color: grey" class="d-flex justify-center"
        ><v-chip class="mr-1" color="primary" label>{{
          bookingIndex + 1
        }}</v-chip>
        <v-chip class="mr-1" label>{{ bookingDto.booking.bookRef }}</v-chip>
        <v-chip class="mr-1" label>{{
          parseBookingDate(bookingDto.booking.bookDate)
        }}</v-chip>
        <v-chip class="mr-1" color="green lighten-3" label>{{
          bookingDto.booking.totalAmount.toLocaleString("en-US", {
            style: "currency",
            currency: "RUB",
          })
        }}</v-chip>
        <v-btn
          color="secondary"
          x-small
          fab
          @click="deleteBooking(bookingIndex, bookingDto.booking.bookRef)"
        >
          <v-icon>mdi-delete</v-icon>
        </v-btn>
      </v-card-title>
      <v-container>
        <v-card
          v-for="(
            bookingPartDto, bookingPartIndex
          ) in bookingDto.bookingPartDtos"
          :key="bookingPartIndex"
          class="mb-2"
        >
          <v-card-actions
            class="d-flex flex-column justify-center align-center"
          >
            <div class="d-flex justify-center mb-4">
              <v-chip color="info" class="mr-1" label
                >{{ bookingIndex + 1 }}.{{ bookingPartIndex + 1 }}</v-chip
              >
              <v-chip
                class="mr-1"
                :color="colorByStatus(bookingPartDto.flightDto.flight.status)"
                label
                >{{ bookingPartDto.flightDto.flight.status }}</v-chip
              >
              <v-chip class="mr-1" label>{{
                bookingPartDto.flightDto.flight.flightNo
              }}</v-chip>
              <v-chip class="mr-1" label>{{
                JSON.parse(bookingPartDto.flightDto.aircraft.model).en
              }}</v-chip>
              <v-chip class="mr-1" label
                >{{ bookingPartDto.flightDto.distance }} km</v-chip
              >
              <v-chip class="mr-1" label>{{
                bookingPartDto.flightDto.flight.interval
              }}</v-chip>
            </div>

            <div class="d-flex justify-center align-center mb-4">
              <div class="mr-4">
                <v-tooltip open-delay="100" left>
                  <template v-slot:activator="{ on, attrs }">
                    <div v-bind="attrs" v-on="on">
                      <div>
                        <strong>{{
                          parseTime(
                            bookingPartDto.flightDto.flight.departureDate,
                            bookingPartDto.flightDto.airportFrom.timeZone
                          )
                        }}</strong>
                      </div>
                      <div>
                        <span>{{
                          parseDate(
                            bookingPartDto.flightDto.flight.departureDate,
                            bookingPartDto.flightDto.airportFrom.timeZone
                          )
                        }}</span>
                      </div>
                    </div>
                  </template>
                  Local Time -
                  {{ bookingPartDto.flightDto.airportFrom.timeZone }}
                </v-tooltip>
              </div>
              <div class="mr-4">
                <v-tooltip bottom open-delay="100">
                  <template v-slot:activator="{ on, attrs }">
                    <strong dark v-bind="attrs" v-on="on">
                      {{ bookingPartDto.flightDto.flight.airportFrom }}
                    </strong>
                  </template>
                  <span>
                    {{
                      JSON.parse(
                        bookingPartDto.flightDto.airportFrom.airportName
                      ).en
                    }}
                    -
                    {{
                      JSON.parse(bookingPartDto.flightDto.airportFrom.city).en
                    }}
                  </span>
                </v-tooltip>
                <v-icon class="mr-1"> mdi-airplane-takeoff </v-icon>
                <v-icon class="mr-2" style="transform: rotate(45deg)">
                  mdi-airplane
                </v-icon>
                <v-icon class="mr-1"> mdi-airplane-landing </v-icon>
                <v-tooltip bottom open-delay="100">
                  <template v-slot:activator="{ on, attrs }">
                    <strong color="primary" dark v-bind="attrs" v-on="on">
                      {{ bookingPartDto.flightDto.flight.airportTo }}
                    </strong>
                  </template>
                  <span>
                    {{
                      JSON.parse(bookingPartDto.flightDto.airportTo.airportName)
                        .en
                    }}
                    -
                    {{
                      JSON.parse(bookingPartDto.flightDto.airportTo.city).en
                    }}</span
                  >
                </v-tooltip>
              </div>
              <div>
                <v-tooltip open-delay="100" right>
                  <template v-slot:activator="{ on, attrs }">
                    <div v-bind="attrs" v-on="on">
                      <div>
                        <strong>{{
                          parseTime(
                            bookingPartDto.flightDto.flight.arrivalDate,
                            bookingPartDto.flightDto.airportTo.timeZone
                          )
                        }}</strong>
                      </div>
                      <div>
                        <span>{{
                          parseDate(
                            bookingPartDto.flightDto.flight.arrivalDate,
                            bookingPartDto.flightDto.airportTo.timeZone
                          )
                        }}</span>
                      </div>
                    </div>
                  </template>
                  Local Time -
                  {{ bookingPartDto.flightDto.airportTo.timeZone }}
                </v-tooltip>
              </div>
            </div>
            <div>
              <v-simple-table>
                <template v-slot:default>
                  <thead>
                    <tr>
                      <th class="text-left">Ticket NO</th>
                      <th class="text-left">Full Name</th>
                      <th class="text-left">Passport</th>
                      <th class="text-left">Fare conditions</th>
                      <th class="text-left">Seat</th>
                      <th class="text-left">Amount</th>
                    </tr>
                  </thead>
                  <tbody
                    v-for="(
                      passengerDocDto, passengerDocIndex
                    ) in bookingPartDto.passengerDocDtos"
                    :key="passengerDocIndex"
                  >
                    <tr>
                      <td>
                        {{ passengerDocDto.ticketNo }}
                      </td>
                      <td>
                        {{ passengerDocDto.fullName }}
                      </td>
                      <td>
                        {{ passengerDocDto.passengerId }}
                      </td>
                      <td>
                        {{ passengerDocDto.fareConditions }}
                      </td>
                      <td>
                        <v-dialog
                          :value="
                            bookingIndex === checkInIndex1 &&
                            bookingPartIndex === checkInIndex2 &&
                            passengerDocIndex === checkInIndex3
                          "
                          :retain-focus="false"
                          fullscreen
                          hide-overlay
                          transition="dialog-bottom-transition"
                        >
                          <v-card>
                            <cabin-layout
                              :passengerDocDto="passengerDocDto"
                              :aircraft="
                                bookingPartDto.flightDto.flight.aircraft
                              "
                              :aircraftName="
                                JSON.parse(
                                  bookingPartDto.flightDto.aircraft.model
                                ).en
                              "
                              @seatNo="
                                checkInSuccess(
                                  $event,
                                  bookingIndex,
                                  bookingPartIndex,
                                  passengerDocIndex
                                ),
                                  (checkInIndex1 = -1),
                                  (checkInIndex2 = -1),
                                  (checkInIndex3 = -1)
                              "
                              @close="
                                (checkInIndex1 = -1),
                                  (checkInIndex2 = -1),
                                  (checkInIndex3 = -1)
                              "
                            ></cabin-layout>
                          </v-card>
                        </v-dialog>
                        <v-btn
                          v-if="passengerDocDto.seatNo === null"
                          color="secondary"
                          small
                          dark
                          @click="
                            checkInOpen(
                              bookingIndex,
                              bookingPartIndex,
                              passengerDocIndex
                            )
                          "
                        >
                          Check-in
                        </v-btn>
                        <v-chip
                          color="yellow lighten-3"
                          v-if="passengerDocDto.seatNo !== null"
                          label
                          >{{ passengerDocDto.seatNo }}</v-chip
                        >
                      </td>
                      <td>
                        <v-chip color="green lighten-3" label>
                          {{
                            passengerDocDto.amount.toLocaleString("en-US", {
                              style: "currency",
                              currency: "RUB",
                            })
                          }}
                        </v-chip>
                      </td>
                    </tr>
                  </tbody>
                </template>
              </v-simple-table>
            </div>
          </v-card-actions>
        </v-card>
      </v-container>
    </v-card>
  </v-container>
</template>

<script>
import * as timeUtils from "../../Utils/timeUtils";
import axios from "axios";
import { validity } from "../../Utils/token/validateToken";
import { bearer } from "../../Utils/token/bearer";
import { refreshTokens } from "../../Utils/token/refreshTokens";
import CabinLayout from "./CabinLayout.vue";

export default {
  name: "Booking",
  components: { CabinLayout },
  data() {
    return {
      alertMsg: "",
      enableAlert: false,
      bookingDtos: [],
      checkInIndex1: -1,
      checkInIndex2: -1,
      checkInIndex3: -1,
    };
  },
  methods: {
    colorByStatus(val) {
      if (val === "Scheduled") return "info";
      if (val === "On Time") return "yellow";
      if (val === "Delayed") return "warning";
      if (val === "Arrived") return "secondary";
    },
    checkInSuccess(seatNo, bookingIndex, bookingPartIndex, passengerDocIndex) {
      this.bookingDtos[bookingIndex].bookingPartDtos[
        bookingPartIndex
      ].passengerDocDtos[passengerDocIndex].seatNo = seatNo;
    },
    checkInOpen(bookingIndex, bookingPartIndex, passengerDocIndex) {
      this.checkInIndex1 = bookingIndex;
      this.checkInIndex2 = bookingPartIndex;
      this.checkInIndex3 = passengerDocIndex;
    },
    deleteBooking(bookingIndex, bookRef) {
      const URL = "http://localhost:37064/booking/delete-booking/".concat(
        bookRef
      );
      const accessToken = this.$store.getters.getUser.accessToken;
      const refreshToken = this.$store.getters.getUser.refreshToken;
      const isValid = validity(accessToken);
      if (isValid) {
        axios
          .delete(URL, {
            headers: {
              Authorization: bearer(accessToken),
            },
          })
          .then((response) => {
            if (response.status === 200) {
              this.enableAlert = true;
              this.alertMsg = "Booking " + bookRef + " delete success";
              this.bookingDtos.splice(bookingIndex, 1);
            }
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
            this.deleteBooking(bookingIndex, bookRef);
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
    getBookingDto() {
      const accessToken = this.$store.getters.getUser.accessToken;
      const refreshToken = this.$store.getters.getUser.refreshToken;
      const isValid = validity(accessToken);
      if (isValid) {
        axios
          .get("http://localhost:37064/booking/bookings", {
            headers: {
              Authorization: bearer(accessToken),
            },
          })
          .then((response) => {
            if (response.status === 200) {
              if (response.data.length === 0) {
                this.enableAlert = true;
                this.alertMsg =
                  this.$store.getters.getUser.fullName +
                  ", You have no bookings";
                return;
              }
              this.enableAlert = false;
              this.bookingDtos = response.data;
            }
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
            this.getBookingDto();
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
    parseTime(date, timeZone) {
      return timeUtils.parseTime(date, timeZone);
    },
    parseDate(date, timeZone) {
      return timeUtils.parseDate(date, timeZone);
    },
    parseBookingDate(val) {
      const date = new Date(val);
      return new Intl.DateTimeFormat("ru-RU", {
        weekday: "long",
        year: "numeric",
        month: "long",
        day: "numeric",
        hour: "2-digit",
        minute: "2-digit",
        timeZoneName: "short",
      }).format(date);
    },
  },
};
</script>

<style>
</style>