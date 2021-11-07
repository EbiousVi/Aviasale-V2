<template>
  <v-container>
    <v-menu
      v-model="menu"
      transition="scale-transition"
      offset-y
      :close-on-content-click="false"
      :nudge-bottom="25"
    >
      <template v-slot:activator="{ on, attrs }">
        <v-text-field
          v-model="date"
          prepend-inner-icon="mdi-calendar"
          readonly
          v-bind="attrs"
          v-on="on"
          :label="label"
          :disabled="disable"
        ></v-text-field>
      </template>
      <v-date-picker
        v-model="date"
        scrollable
        :min="min"
        max="2017-09-14"
        @change="$emit('date', date)"
        @input="menu = false"
      ></v-date-picker>
    </v-menu>
  </v-container>
</template>

<script>
export default {
  name: "Calendar",
  emits: ["close", "date"],
  props: {
    disable: {
      type: Boolean,
      require: true,
      default: false,
    },
    label: {
      type: String,
      require: true,
    },
    master: {
      type: Boolean,
      require: true,
    },
    masterDate: {
      type: String,
    },
  },
  data: () => ({
    date: "",
    min: "2017-08-15",
    menu: false,
  }),
  created() {
    const x = new Date(Date.now()).getDate();
    if (this.master) {
      if (x >= 15 && x <= 31) {
        this.date = "2017-08-" + x;
      } else {
        if (x < 10) {
          this.date = "2017-09-0" + x;
        } else {
          this.date = "2017-09-" + x;
        }
      }
    } else {
      if (x >= 15 && x <= 31) {
        this.date = "2017-08-" + (x + 1);
      } else {
        if (x < 10) {
          this.date = "2017-09-0" + x;
        } else {
          this.date = "2017-09-" + x;
        }
      }
    }
  },
  watch: {
    masterDate: {
      immediate: true,
      handler(cur, old) {
        if (old !== cur && cur !== undefined && old !== undefined) {
          const date = new Date(this.masterDate);
          date.setDate(date.getDate() + 1);
          this.date = date.toISOString().slice(0, -14);
          this.min = date.toISOString().slice(0, -14);
          this.$emit("date", date.toISOString().slice(0, -14));
          return;
        }
      },
    },
  },
  async mounted() {
    await this.$nextTick();
    this.$emit("date", this.date);
  },
  methods: {},
};
</script>