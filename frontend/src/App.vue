<template>
  <v-app>
    <div>
      <v-app-bar color="primary" dense dark hide-on-scroll>
        <v-toolbar-title
          @click="$router.push('/search').catch((err) => {})"
          style="cursor: pointer; width: 250px"
          >Ebious Airlines
          <v-icon dark large> mdi-airport </v-icon>
        </v-toolbar-title>

        <v-tabs centered>
          <v-tab @click="$router.push('/search').catch((err) => {})"
            >Search Flight</v-tab
          >
          <v-tab
            @click="$router.push('/booking').catch((err) => {})"
            :disabled="this.$store.getters.getUser.email.length === 0"
            >My booking</v-tab
          >
        </v-tabs>
        <v-btn
          v-if="this.$store.getters.getUser.email.length === 0"
          @click="$router.push('/sign-in').catch((err) => {})"
          class="mr-2"
        >
          Sign in
          <v-icon>mdi-login</v-icon>
        </v-btn>
        <v-btn
          v-if="this.$store.getters.getUser.email.length === 0"
          @click="$router.push('/sign-up').catch((err) => {})"
          class="mr-2"
        >
          Sign up
          <v-icon>mdi-account-plus</v-icon>
        </v-btn>
        <div v-if="this.$store.getters.getUser.email.length > 0">
          <v-menu offset-y>
            <template v-slot:activator="{ on, attrs }">
              <v-btn color="secondary" dark v-bind="attrs" v-on="on">
                {{ getUserEmail }}
              </v-btn>
            </template>
            <v-list>
              <v-list-item v-for="item in 1" :key="item" link>
                <v-list-item-title @click="logout">Logout</v-list-item-title>
              </v-list-item>
            </v-list>
          </v-menu>
        </div>
      </v-app-bar>
    </div>
    <v-main>
      <keep-alive>
        <router-view />
      </keep-alive>
    </v-main>
  </v-app>
</template>

<script>
import axios from "axios";

export default {
  name: "App",
  data: () => ({
    form: {
      email: "",
    },
    logoutURL: "http://localhost:37064/api/auth/logout",
  }),
  computed: {
    getUserEmail() {
      return this.$store.getters.getUser.email;
    },
  },
  methods: {
    logout() {
      const emptyUser = {
        email: "",
        fullName: "",
        passengerId: "",
        phoneNo: "",
        accessToken: "",
        refreshToken: "",
      };
      this.form.email = this.$store.getters.getUser.email;
      axios
        .post(this.logoutURL, this.form)
        .then((response) => {
          if (response.status === 200) {
            this.$store.commit("setUser", emptyUser);
            this.$router.push('/');
          }
        })
        .catch(() => {
          this.$store.commit("setUser", emptyUser);
          this.$router.push('/');
        });
    },
  },
};
</script>
