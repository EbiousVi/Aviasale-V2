import Vue from 'vue'
import VueRouter from 'vue-router'
import Booking from '../components/booking/Booking.vue'
import SignIn from '../components/credentials/SignIn.vue'
import SignUp from '../components/credentials/SignUp.vue'
import Search from '../components/search/Search.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect: '/search',
    name: 'Search',
    component: Search
  },
  {
    path: '/sign-up',
    name: 'SignUp',
    component: SignUp
  },
  {
    path: '/sign-in',
    name: 'SignIn',
    component: SignIn
  },
  {
    path: '/search',
    name: 'Search',
    component: Search
  },
  {
    path: '/booking',
    name: 'Booking',
    component: Booking
  }
]
const router = new VueRouter({
  routes,
})

export default router
