export default {
    namespace: true,
    state: {
        user: {
            email: "",
            fullName: "",
            passengerId: "",
            phoneNo: "",
            accessToken: "",
            refreshToken: "",
        },
    },
    mutations: {
        setUser(state, payload) {
            state.user = payload;
        },
        setTokens(state, payload) {     
            state.user.accessToken = payload.accessToken;
            state.user.refreshToken = payload.refreshToken;
        }
    },
    getters: {
        getUser(state) {
            return state.user;
        }
    }
}