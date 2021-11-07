<template>
  <v-container>
    <v-pagination
      v-model="page"
      :length="Math.ceil(flightsDto.depRtnFlPairDtos.length / perPage)"
    >
    </v-pagination>
    <div v-for="(depRtnFlDto, index) in visiblePages" :key="index">
      <v-row>
        <v-col md="12">
          <v-card>
            <v-card-actions class="d-flex flex-column justify-space-between">
              <v-container style="padding: 0; margin: 0">
                <flight-info
                  :flightsDto="depRtnFlDto.depFlDtos"
                  style="padding: 0"
                ></flight-info>
                <chips-group
                  :flightsDto="depRtnFlDto.depFlDtos"
                  :conditions="conditions"
                ></chips-group>
                <v-divider></v-divider>
                <flight-info
                  v-if="tripType === 'ROUND'"
                  :flightsDto="depRtnFlDto.rtnFlDtos"
                  style="padding: 0"
                ></flight-info>
                <chips-group
                  v-if="tripType === 'ROUND'"
                  :flightsDto="depRtnFlDto.rtnFlDtos"
                  :conditions="conditions"
                ></chips-group>
                <v-divider v-if="tripType === 'ROUND'"></v-divider>
              </v-container>
            </v-card-actions>
            <v-card-actions>
              <v-chip color="green lighten-2" class="mt-2 mr-2" label
                ><strong
                  >TOTAL :
                  {{
                    (
                      depRtnFlDto.depTotalPrice + depRtnFlDto.rtnTotalPrice
                    ).toLocaleString("en-US", {
                      style: "currency",
                      currency: "RUB",
                    })
                  }}</strong
                ></v-chip
              >
              <v-btn color="primary" class="mt-2" @click="bookingAction(index)">
                Booking
              </v-btn>
              <v-btn
                class="mt-2"
                color="secondary"
                @click="detailsAction(index)"
              >
                Details
              </v-btn>

              <v-dialog
                :value="bookingIndex === index"
                fullscreen
                hide-overlay
                transition="dialog-bottom-transition"
              >
                <v-card>
                  <pre-booking
                    :noOfTickets="noOfTickets"
                    :prices="givePrices(depRtnFlDto, index)"
                    :tripType="tripType"
                    @close="bookingIndex = -1"
                    @passenger="bookingIndex = -1"
                    @booking="bookingIndex = -1"
                  ></pre-booking>
                </v-card>
              </v-dialog>

              <v-snackbar
                v-if="isLogin"
                v-model="snackbar"
                :multi-line="true"
                :top="true"
                color="black"
                timeout="15000"
              >
                You need to log in to continue
                <br />
                <template v-slot:action="{ attrs }">
                  <v-btn
                    color="white"
                    class="mr-4 black--text"
                    v-bind="attrs"
                    @click="$router.push('/sign-up').catch((err) => {})"
                  >
                    <span>Sign up </span>
                  </v-btn>
                  <v-btn
                    color="white"
                    class="mr-4 black--text"
                    v-bind="attrs"
                    @click="$router.push('/sign-in').catch((err) => {})"
                  >
                    Sign in
                  </v-btn>
                  <v-btn
                    color="red"
                    text
                    v-bind="attrs"
                    @click="snackbar = false"
                  >
                    Close
                  </v-btn>
                </template>
              </v-snackbar>
            </v-card-actions>

            <v-dialog
              :value="detailsIndex === index"
              fullscreen
              hide-overlay
              transition="dialog-bottom-transition"
            >
              <v-card>
                <v-btn dark class="mt-2 ml-2" @click="detailsIndex = -1"
                  >Close</v-btn
                >
                <v-card-actions class="d-flex justify-space-around">
                  <div>
                    <strong>Depart Flight</strong>
                    <timeline
                      :flights="depRtnFlDto.depFlDtos"
                      @close="detailsIndex = -1"
                    ></timeline>
                  </div>
                  <v-divider vertical v-if="tripType === 'ROUND'"></v-divider>
                  <div v-if="tripType === 'ROUND'">
                    <strong>Return Flight</strong>
                    <timeline :flights="depRtnFlDto.rtnFlDtos"></timeline>
                  </div>
                </v-card-actions>
              </v-card>
            </v-dialog>
          </v-card>
        </v-col>
      </v-row>
    </div>
  </v-container>
</template>

<script>
import PreBooking from "../booking/PreBooking.vue";
import ChipsGroup from "../search/ChipsGroup.vue";
import FlightInfo from "../search/FlightInfo.vue";
import Timeline from "../search/Timeline.vue";

export default {
  name: "Cards",
  components: { Timeline, FlightInfo, PreBooking, ChipsGroup },
  props: {
    flightsDto: {
      type: Object,
      require: true,
    },
    tripType: {
      type: String,
      require: true,
    },
    noOfTickets: {
      type: Number,
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
          this.page = 1;
          return;
        }
      },
    },
  },
  computed: {
    visiblePages() {
      return this.flightsDto.depRtnFlPairDtos.slice(
        (this.page - 1) * this.perPage,
        this.page * this.perPage
      );
    },
    isLogin() {
      return this.$store.getters.getUser.email.length === 0;
    },
  },
  data: () => ({
    page: 1,
    perPage: 5,
    snackbar: false,
    bookingIndex: -1,
    detailsIndex: -1,
  }),
  methods: {
    givePrices(depRtnFlDto) {
      const prices = [];
      depRtnFlDto.depFlDtos.forEach((el) => prices.push(el.price));
      if (depRtnFlDto.rtnFlDtos.length > 0) {
        depRtnFlDto.rtnFlDtos.forEach((el) => prices.push(el.price));
      }
      return prices;
    },
    bookingAction(index) {
      if (this.$store.getters.getUser.email.length > 0) {
        this.bookingIndex = index;
        return;
      }
      this.snackbar = true;
    },
    detailsAction(index) {
      this.detailsIndex = index;
    },
  },
};
</script>

<style>
</style>