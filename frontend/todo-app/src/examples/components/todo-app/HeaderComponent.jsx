import React, { Component } from 'react'
import AuthenticationService from '../../api/AuthenticationService.js'
import { Link } from 'react-router-dom'
import { withRouter } from 'react-router';

class HeaderComponent extends Component {

    constructor(props) {
        super(props)
        this.state = {
            firstname: '',
            lastname: '',
            serviceError: false,
            message: ''
        }
        this.fetchUserInfo = this.fetchUserInfo.bind(this)

    }

    // componentDidUpdate(prevState) {
    //     if (prevState.firstName !== this.state.firstname) {
    //         const username = sessionStorage.getItem("authenticatedUser");
    //         this.fetchUserInfo(username)
    //     }      
    // }


    fetchUserInfo(username){        
            AuthenticationService.fetchUserDetails(username).then
            (response => {
                this.setState({
                    firstname: response.data.firstname, 
                    lastname: response.data.lastname, 
                    message: null
                })
            }
            ).catch(
                this.setState({
                    serviceError: true,
                    message: 'Oops something went wrong with the Get User service'
                })

            )
    }
    
    
    render() {
        
        const userLoggedIn = AuthenticationService.isUserLoggedIn();
        return (
            <header>
                <nav className="navbar navbar-expand-md navbar-dark bg-dark">
                    <div className="header">Application</div>
                    <ul className="navbar-nav">

                        {userLoggedIn && <li><Link to="/welcome" className="nav-link header">Home</Link></li>}
                        {userLoggedIn && <li><Link to="/todos" className="nav-link header">Todos</Link></li>}

                    </ul>
                    <ul className="navbar-nav navbar-collapse justify-content-end">               
                        {!userLoggedIn && <li><Link to="/login" className="nav-link header">Login</Link></li>}
                        {userLoggedIn && <div class="dropdown">
                            {/* <button class="dropbtn">{this.state.firstname} {this.state.lastname}</button> */}
                            <button class="dropbtn">Profile</button>
                            <div class="dropdown-content">
                                <Link to="/logout" onClick={AuthenticationService.logout} className="nav-link header">Logout</Link>
                            </div>
                        </div>}
                    </ul>

                </nav>
            </header>

        )
    }

}

export default withRouter(HeaderComponent);