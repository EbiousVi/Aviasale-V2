<template>
  <v-form ref="form" v-model="valid" lazy-validation>
    <v-container v-for="(passenger, index) in passengers" :key="index">
      <v-row class="mt-4">
        <strong>Passenger {{ index + 1 }}</strong>
      </v-row>
      <v-row>
        <v-col md="3">
          <v-text-field
            v-model="passenger.fullName"
            label="Full Name"
            required
            :counter="64"
            :rules="fullNameRules"
          ></v-text-field>
        </v-col>
        <v-col md="3">
          <v-text-field
            v-model="passenger.passengerId"
            label="Passport"
            required
            :counter="11"
            :rules="passportRules"
          ></v-text-field>
        </v-col>
        <v-col md="3">
          <v-text-field
            v-model="passenger.email"
            label="E-mail"
            required
            :counter="64"
            :rules="emailRules"
          ></v-text-field>
        </v-col>
        <v-col md="3">
          <v-text-field
            v-model="passenger.phoneNo"
            label="Phone number"
            required
            :counter="15"
            :rules="phoneNumberRules"
          ></v-text-field>
        </v-col>
      </v-row>
    </v-container>
    <v-container>
      <v-divider class="mb-4"></v-divider>
      <v-btn color="primary" class="mr-4" @click="validate">
        Try Booking
      </v-btn>
      <v-btn color="warning" class="mr-4" @click="reset"> Reset Form </v-btn>
      <v-btn color="secondary" @click="close" class="mr-4"> Close </v-btn>
      <v-alert
        class="mt-4"
        v-model="enableAlert"
        dismissible
        elevation="2"
        text
        type="error"
        >{{ alertMsg }}</v-alert
      >
    </v-container>
  </v-form>
</template>

<script>
import axios from "axios";
import { validity } from "../../Utils/token/validateToken";
import { bearer } from "../../Utils/token/bearer";
import { refreshTokens } from "../../Utils/token/refreshTokens";

export default {
  name: "PreBooking",
  emits: ["passenger", "close", "booking"],
  props: {
    noOfTickets: {
      type: Number,
      require: true,
    },
    prices: {
      type: Array,
      require: true,
    },
    tripType: {
      type: String,
      require: true,
    },
  },
  watch: {
    noOfTickets: {
      immediate: true,
      handler(cur, old) {
        if (old !== cur) {
          this.init();
          return;
        }
      },
    },
  },
  data: () => ({
    enableAlert: false,
    alertMsg: "",
    passengers: [],
    valid: true,
    fullNameRules: [
      (v) => !!v || "Full name is required",
      (v) =>
        (v && v.length <= 64) ||
        "The First and Last name must be less than 64 characters",
      (v) =>
        /[a-zA-Z]{2,}\s[a-zA-Z]{2,}/.test(v) ||
        "The First and Last name must be at least 2 characters separated by a space",
    ],
    passportRules: [
      (v) => !!v || "Passport is required",
      (v) =>
        (v && v.length === 11) ||
        "Passport number must be equals 11 characters. Like 0000 000001",
      (v) =>
        /\d{4}\s\d{6}/.test(v) ||
        "Passport number format: 4 character + whitespace + 6 characters. Like 0000 000001",
    ],
    emailRules: [
      (v) => !!v || "E-mail is required",
      (v) => /.+@.+\..+/.test(v) || "E-mail must be valid",
      (v) => (v && v.length <= 64) || "E-mail must be less than 64 characters",
    ],
    phoneNumberRules: [
      (v) => !!v || "Phone number is required",
      (v) =>
        /^(\s*)?(\+)?([- _():=+]?\d[- _():=+]?){10,14}(\s*)?$/.test(v) ||
        "Phone number must be valid",
      (v) =>
        (v && v.length <= 15) || "Phone number must be less than 15 characters",
    ],
  }),
  methods: {
    init() {
      this.passengers = [];
      const user = this.$store.getters.getUser;
      const buyer = {
        fullName: user.fullName,
        passengerId: user.passengerId,
        email: user.email,
        phoneNo: JSON.parse(user.phoneNo).phone,
      };
      this.passengers.push(buyer);
      if (this.noOfTickets > 1) {
        for (let i = 1; i < this.noOfTickets; i++) {
          const companion = {
            fullName: user.fullName,
            passengerId: user.passengerId,
            email: user.email,
            phoneNo: JSON.parse(user.phoneNo).phone,
          };
          this.passengers.push(companion);
        }
      }
    },
    submitForm() {
      const bookingInfo = {
        passengers: this.passengers,
        prices: this.prices,
      };
      const accessToken = this.$store.getters.getUser.accessToken;
      const refreshToken = this.$store.getters.getUser.refreshToken;
      const isValid = validity(accessToken);
      if (isValid) {
        axios
          .post("http://localhost:37064/booking/try-booking", bookingInfo, {
            headers: {
              Authorization: bearer(accessToken),
            },
          })
          .then((response) => {
            if (response.status === 200) {
              this.$emit("booking");
              this.$router.push("/booking");
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
            this.submitForm();
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
    validate() {
      if (this.$refs.form.validate()) {
        this.submitForm();
      }
    },
    reset() {
      this.$refs.form.reset();
      this.enableAlert = false;
    },
    close() {
      this.$emit("close", false);
    },
  },
};
</script>