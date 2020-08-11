import React, { Component } from 'react'
import { Link } from 'react-router-dom'
import HelloWorldService from './../../api/HelloWorldService.js'

class WelcomeComponent extends Component {

    constructor() {
        super()

        this.retrieveMessage = this.retrieveMessage.bind(this)

        this.state = {
            welcomeMessage: '',
            errorMessage: ''

        }
        this.handleSuccessMessage = this.handleSuccessMessage.bind(this)
    }

    render() {
        return (
            <>
                <h1>Welcome !!</h1>
                <div className="container">{this.state.errorMessage}</div>
                <div className="container">Welcome. What would like to do here ?</div>

                <div class="table_class">
                    <div class="table-cell">
                        <h3>Would you like to add Notes</h3>
                        <Link class="btn" to="/todos">here</Link>
                    </div>
                    <div class="table-cell plattform">
                        <h3>Use Comparer</h3>
                        <a href="" class="btn">Comparer</a>
                    </div>
                    <div class="table-cell enterprise">
                        <h3>Free Space</h3>
                        <a href="" class="btn">Try me</a>
                    </div>
                </div>
            </>
        )


    }

    retrieveMessage() {
        HelloWorldService.retriveMessage().then(response => this.handleSuccessMessage(response)).catch(error => this.handleError(error))
    }

    handleSuccessMessage(response) {
        this.setState({
            welcomeMessage: response.data

        })
    }

    handleError(error) {

        let errorMessage = ''

        if (error.message) {
            errorMessage += error.message
        }

        if (error.response.data) {
            errorMessage += error.response.data.message
        }
        this.setState({
            errorMessage: error.response.data.message

        })
    }

}

export default WelcomeComponent