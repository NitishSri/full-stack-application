import axios from 'axios';

class AuthenticationService {
  validateCredentials(username, password) {
    return axios.post('http://localhost:9091/login', {
      username, password,
    });
  }

  loginUser(username, token) {
    sessionStorage.setItem('authenticatedUser', username);
    const basicAuthenticationHeader = `Bearer ${  token}`;
    this.setupAxiosInterceptor(basicAuthenticationHeader);
  }

  logout() {
    sessionStorage.removeItem('authenticatedUser');
  }

  isUserLoggedIn() {
    const user = sessionStorage.getItem('authenticatedUser');
    if (null === user) {
      return false;
    }
    return true;
  }

  getLoggedInUserID() {
    const user = sessionStorage.getItem('authenticatedUser');
    if (null === user) {
      return null;
    }
    return user;
  }

  register(firstname, lastname, username, password) {
    return axios.post('http://localhost:9091/register', {
      firstname, lastname, username, password,
    });
  }

  fetchUserDetails(username) {
    return axios.get(`http://localhost:9091/user/${username}`);
  }

  setupAxiosInterceptor(basicAuthenticationHeader) {
    console.log(basicAuthenticationHeader);
    axios.interceptors.request.use(
      (config) => {
        if (this.isUserLoggedIn) {
          {
            config.headers.authorization = basicAuthenticationHeader;
          }
          return config;
        }
      },

    );
  }
}

export default new AuthenticationService();
