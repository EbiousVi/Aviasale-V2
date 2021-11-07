<template>
  <v-container class="grey lighten-3" style="border-radius: 5px">
    <v-form ref="form" v-model="valid" lazy-validation>
      <v-text-field
        v-model="form.fullName"
        label="Full name"
        required
        :counter="64"
        :rules="fullNameRules"
      ></v-text-field>

      <v-text-field
        v-model="form.passengerId"
        label="Passport"
        required
        :counter="11"
        :rules="passportRules"
      ></v-text-field>

      <v-text-field
        v-model="form.email"
        label="E-mail"
        required
        :counter="64"
        :rules="emailRules"
      ></v-text-field>

      <v-text-field
        v-model="form.password"
        label="Password"
        hint="At least 6 characters"
        :counter="64"
        required
        :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
        :rules="passRules"
        :type="showPassword ? 'text' : 'password'"
        @click:append="showPassword = !showPassword"
      ></v-text-field>

      <v-text-field
        v-model="form.phoneNo"
        label="Phone number"
        required
        :counter="15"
        :rules="phoneNumberRules"
      ></v-text-field>

      <v-btn :disabled="!valid" color="primary" class="mr-4" @click="validate">
        Sign Up
      </v-btn>
      <v-btn color="warning" @click="reset"> Reset Form </v-btn>
    </v-form>
    <v-alert v-model="enableAlert" dismissible dense type="error" class="mt-4">
      {{ alertMsg }}
    </v-alert>
  </v-container>
</template>

<script>
import axios from "axios";
export default {
  name: "SignUp",
  data: () => ({
    signUpURL: "http://localhost:37064/api/auth/sign-up",
    form: {
      fullName: "Test User",
      passengerId: "0000 000001",
      email: "testuser@mail.com",
      password: "qweqwe",
      phoneNo: "88006003000",
    },
    alertMsg: "",
    enableAlert: false,
    valid: true,
    password: "",
    showPassword: false,
    passRules: [
      (v) => !!v || "Password is required",
      (v) => (v && v.length >= 6) || "At least 6 characters",
      (v) =>
        (v && v.length <= 64) || "Password must be less than 64 characters",
    ],
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
        (v && v.length < 16) || "Phone number must be less than 15 characters",
    ],
  }),
  methods: {
    submitForm() {
      axios
        .post(this.signUpURL, this.form)
        .then((response) => {
          if (response.status === 200) {
            this.$router.push("/sign-in");
          }
        })
        .catch((error) => {
          this.enableAlert = true;
          this.alertMsg = error.response !== undefined ? error.response.data : error;
        });
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
  },
};
</script>