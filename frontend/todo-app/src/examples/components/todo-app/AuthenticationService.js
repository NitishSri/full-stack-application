import axios from 'axios'

class AuthenticationService {

    validateCredentials(username, password) {
        return axios.post('http://localhost:9091/login', {
            username: username, password: password
        })
    }

    loginUser(username, password) {
        console.log('loginuser')
        let basicAuthenticationHeader = 'Basic ' + window.btoa(username + ":" + password)
        sessionStorage.setItem("authenticatedUser", username)
        this.setupAxiosInterceptor(basicAuthenticationHeader)
    }

    logout() {
        sessionStorage.removeItem("authenticatedUser");
    }

    isUserLoggedIn() {
        let user = sessionStorage.getItem("authenticatedUser");
        if (null === user) return false
        return true
    }

    getLoggedInUserID() {
        let user = sessionStorage.getItem("authenticatedUser");
        if (null === user) return null
        return user
    }

    setupAxiosInterceptor(basicAuthenticationHeader) {
        console.log(basicAuthenticationHeader)
        axios.interceptors.request.use(
            (config) => {
                if (this.isUserLoggedIn) {
                    headers: {
                        config.headers.authorization = basicAuthenticationHeader
                    }
                    return config
                }
            }

        )
    }
}

export default new AuthenticationService()