import React, { Component } from 'react';
import AuthenticationService from '../../api/AuthenticationService.js';


class LoginComponent extends Component {
  constructor(props) {
    super(props);

    this.state = {
      username: 'nitishsr',
      password: '',
      inValidLogin: false,
      serviceError: false,
    };

    this.handleChangedEvent = this.handleChangedEvent.bind(this);
    this.loginClicked = this.loginClicked.bind(this);
    this.registerClicked = this.registerClicked.bind(this);
  }

  handleChangedEvent(event) {
    this.setState({ [event.target.name]: event.target.value });
  }

  loginClicked() {
    AuthenticationService.validateCredentials(this.state.username, this.state.password).then(
      (response) => {
        if (response.data.invalidCredentials === true) {
          this.setState({ inValidLogin: true });
          this.setState({ serviceError: false });
        } else {
          AuthenticationService.loginUser(this.state.username, response.data.token);
          this.props.history.push('/welcome');
        }
      },
    )
      .catch(
        this.setState({ serviceError: true }),
      );

    /*
     * if (!(this.state.username === 'nitishsr' && this.state.password === 'nitish')) {
     *   this.setState({ inValidLogin: true })
     * } else {
     *  AuthenticationService.loginUser(this.state.username, this.state.password)
     * this.props.history.push(`/welcome/${this.state.username}`)
     * }
     */
  }

  registerClicked() {
    this.props.history.push('/register');
  }


  render() {
    return (
      <div>
        <h3>Todo Application</h3>
        <div className="container">
          {this.state.inValidLogin && <div className="alert alert-warning">Username and password is not valid !!</div>}
          {this.state.serviceError && <div className="alert alert-warning">Someting went wrong with the login service</div>}
          <div>Username : <input type="text" className="un" onChange={this.handleChangedEvent} name="username" value={this.state.username} /></div>
          <div>Password : <input type="password" className="pass" onChange={this.handleChangedEvent} name="password" value={this.state.password} /></div>
          <button className="submit  btn-success btn" align="center" onClick={this.loginClicked}>Login</button>
          <button className="submit btn-success btn" onClick={this.registerClicked} align="center">Sign Up</button>
        </div>
      </div>


    );
  }
}


export default LoginComponent;
