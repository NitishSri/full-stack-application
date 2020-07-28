import React, { Component } from 'react'
import AuthenticationService from '../../api/AuthenticationService.js'


class LoginComponent extends Component {

    constructor(props) {
        super(props)

        this.state = {
            username: 'nitishsr',
            password: '',
            inValidLogin: false,
            serviceError: false
        }

        this.handleChangedEvent = this.handleChangedEvent.bind(this)
        this.loginClicked = this.loginClicked.bind(this)
    }

    handleChangedEvent(event) {
        this.setState({ [event.target.name]: event.target.value })
    }

    loginClicked() {
        AuthenticationService.validateCredentials(this.state.username, this.state.password).then(
            response => {
                if (response.data.invalidCredentials === true) {
                    this.setState({ inValidLogin: true })
                    this.setState({ serviceError: false })
                } else {
                    AuthenticationService.loginUser(this.state.username, response.data.token)
                    this.props.history.push(`/welcome/${response.data.firstname}`)
                }
            }
        ).catch(
            this.setState({ serviceError: true })
        )

        //if (!(this.state.username === 'nitishsr' && this.state.password === 'nitish')) {
        //   this.setState({ inValidLogin: true })
        //} else {
        //  AuthenticationService.loginUser(this.state.username, this.state.password)
        // this.props.history.push(`/welcome/${this.state.username}`)
        // }
    }



    render() {

        return (
            <div>
                <h3>Login</h3>
                <div className="container">
                    {this.state.inValidLogin && <div className="alert alert-warning">Username and password is not valid !!</div>}
                    {this.state.serviceError && <div className="alert alert-warning">Someting went wrong with the login service</div>}
                        Username : <input type="text" onChange={this.handleChangedEvent} name="username" value={this.state.username} />
                        Password : <input type="password" onChange={this.handleChangedEvent} name="password" value={this.state.password} />
                        <button className="btn btn-success" onClick={this.loginClicked}>Login</button>
                </div>
            </div>


        )
    }

}


export default LoginComponent