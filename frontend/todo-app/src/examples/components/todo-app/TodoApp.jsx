import React, { Component } from 'react'
import './TodoApp.css'
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'
//import { render } from '@testing-library/react'
import AuthenticatedRoute from './AuthenticatedRoute'
import LoginComponent from './LoginComponent'
import HeaderComponent from './HeaderComponent'
import FooterComponent from './FooterComponent'
import ErrorComponent from './ErrorComponent'
import TodoComponent from './TodoComponent'
import LogoutComponent from './LogoutComponent'
import WelcomeComponent from './WelcomeComponent'
import SingleTodoComponent from './SingleTodoComponent'
import RegistrationComponent from './RegistrationComponent'
class TodoApp extends Component {

    render() {

        return (
            <div className="TodoApp">
                <Router>
                    <>
                        <HeaderComponent />
                        <Switch>
                            <Route path="/" exact component={LoginComponent} ></Route>
                            <Route path="/login" component={LoginComponent} ></Route>
                            <Route path="/register" component={RegistrationComponent} ></Route>
                            <AuthenticatedRoute path="/welcome/:name" component={WelcomeComponent} ></AuthenticatedRoute>
                            <AuthenticatedRoute path="/todos/:id" component={SingleTodoComponent} ></AuthenticatedRoute>
                            <AuthenticatedRoute path="/todos" component={TodoComponent} ></AuthenticatedRoute>
                            <AuthenticatedRoute path="/logout" component={LogoutComponent} ></AuthenticatedRoute>                           
                            <Route component={ErrorComponent} ></Route>
                        </Switch>
                        <FooterComponent />
                    </>
                </Router>
            </div>


        )
    }

}

export default TodoApp