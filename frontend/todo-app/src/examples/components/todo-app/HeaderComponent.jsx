import React, { Component } from 'react'
import AuthenticationService from './AuthenticationService.js'
import { Link } from 'react-router-dom'
import { withRouter } from 'react-router';

class HeaderComponent extends Component {

    render() {

        const userLoggedIn = AuthenticationService.isUserLoggedIn();
        return (
            <header>
                <nav className="navbar navbar-expand-md navbar-dark bg-dark">
                    <div className="header">Nitish Todo List</div>
                    <ul className="navbar-nav">

                        {userLoggedIn && <li><Link to="/welcome" className="nav-link header">Home</Link></li>}
                        {userLoggedIn && <li><Link to="/todos" className="nav-link header">Todos</Link></li>}

                    </ul>
                    <ul className="navbar-nav navbar-collapse justify-content-end">

                        {!userLoggedIn && <li><Link to="/login" className="nav-link header">Login</Link></li>}
                        {userLoggedIn && <li><Link to="/logout" onClick={AuthenticationService.logout} className="nav-link header">Logout</Link></li>}

                    </ul>

                </nav>
            </header>

        )
    }

}

export default withRouter(HeaderComponent);