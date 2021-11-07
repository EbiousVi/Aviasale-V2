<template>
  <v-container
    class="grey lighten-3"
    style="border-radius: 5px; margin-top: 25px"
  >
    <v-form ref="form" v-model="valid" lazy-validation>
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
        required
        :counter="64"
        :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
        :rules="passRules"
        :type="showPassword ? 'text' : 'password'"
        @click:append="showPassword = !showPassword"
      ></v-text-field>
      <v-btn :disabled="!valid" color="primary" class="mr-4" @click="validate">
        Sign In
      </v-btn>
      <v-btn color="warning" class="mr-4" @click="reset"> Reset Form </v-btn>
    </v-form>
    <v-alert v-model="enableAlert" dismissible dense type="error" class="mt-4">
      {{ alertMsg }}
    </v-alert>
  </v-container>
</template>

<script>
import axios from "axios";

export default {
  name: "SignIn",
  data: () => ({
    signInURL: "http://localhost:37064/api/auth/sign-in",
    alertMsg: "",
    enableAlert: false,
    form: {
      email: "testuser@mail.com",
      password: "qweqwe",
    },
    valid: true,
    password: "",
    showPassword: false,
    passRules: [
      (v) => !!v || "Password is required",
      (v) => (v && v.length >= 6) || "At least 6 characters",
      (v) =>
        (v && v.length <= 64) || "Password must be less than 64 characters",
    ],
    emailRules: [
      (v) => !!v || "E-mail is required",
      (v) => /.+@.+\..+/.test(v) || "E-mail must be valid",
      (v) => (v && v.length <= 64) || "E-mail must be less than 64 characters",
    ],
  }),
  methods: {
    submitForm() {
      axios
        .post(this.signInURL, this.form)
        .then((response) => {
          if (response.status === 200) {
            this.$store.commit("setUser", response.data);
            this.$router.push("/");
          }
        })
        .catch((error) => {
          this.alertMsg = error.response !== undefined ? error.response.data : error;
          this.enableAlert = true;
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