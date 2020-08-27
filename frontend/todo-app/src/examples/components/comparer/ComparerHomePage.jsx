import React, { Component } from 'react'
import { Link } from 'react-router-dom'
import ComparerThreadService from '../../api/ComparerThreadService.js'

class ComparerHomePage extends Component {

    constructor() {
        super()

        this.state = {
            noresult: false,
            serviceError: false,
            errorMessage: '',
            topicOne: '',
            topicTwo: '',
            resultFound: false,
            foundThreads: ''

        }

        this.searchThread = this.searchThread.bind(this)
        this.createThread = this.createThread.bind(this)
        this.openThread = this.openThread.bind(this)
        this.handleChangedEvent = this.handleChangedEvent.bind(this)
    }

    handleChangedEvent(event) {
        this.setState({ [event.target.name]: event.target.value })
    }

    searchThread() {
        this.state.resultFound = false;
        const username = sessionStorage.getItem("authenticatedUser");
        ComparerThreadService.searchThread({
            topicOne: this.state.topicOne,
            topicTwo: this.state.topicTwo,
            authorUsername: username
        }).then
            (response => {
                if (response.data == '') {
                    this.setState({
                        noresult: true
                    })
                } else {
                    this.setState({
                        noresult: false,
                        resultFound: true,
                        foundThreads: response.data
                    })
                }
            }
            ).catch(
                this.setState({
                    serviceError: true,
                    message: 'Oops something went wrong with the Search Thread service'
                })

            )
    }

    createThread() {
        const username = sessionStorage.getItem("authenticatedUser");
        ComparerThreadService.createThread({
            topicOne: this.state.topicOne,
            topicTwo: this.state.topicTwo,
            authorUsername: username
        }).then
            (response => {
                this.setState({
                    noresult: response.data.noresult
                })
                this.props.history.push({
                    pathname: '/threads',
                    state: { detail: response.data }
                })
            }
            ).catch(
                this.setState({
                    serviceError: true,
                    message: 'Oops something went wrong with the Create Thread service'
                })

            )
    }

    openThread(threadName) {
        ComparerThreadService.openThread(threadName).then
            (response => {
                this.props.history.push({
                    pathname: '/threads',
                    state: { detail: response.data }
                })
            }
            ).catch(
                this.setState({
                    serviceError: true,
                    message: 'Oops something went wrong with the Open Thread service'
                })

            )

    }

    render() {
        var threadArray = JSON.parse(JSON.stringify(this.state.foundThreads));
        console.log(threadArray)
        var resultThreads = []
        for (var i = 0; i < threadArray.length; i++) {
            var threadDisplayName = JSON.stringify(threadArray[i].threadDisplayName).slice(1, -1)
            var threadName = threadArray[i].threadName
            resultThreads.push(<a className="link-box btn-success" onClick={() => this.openThread(threadName)}><div>{threadDisplayName}</div></a>);
        }
        return (
            <>
                <h3>Welcome to the comparer application</h3>

                <div className="table_class">
                    <div className="table-cell">
                        <h1>Item One</h1>
                        <div><input type="text" className="un" onChange={this.handleChangedEvent} name="topicOne" value={this.state.topicOne} /></div>
                    </div>
                    <div className="table-cell plattform">
                        <h1>Item Two</h1>
                        <div><input type="text" className="un" onChange={this.handleChangedEvent} name="topicTwo" value={this.state.topicTwo} /></div>
                    </div>
                    <div className="table-cell enterprise">
                        <h1>Hit the button</h1>
                        <button className="btn-success btn" onClick={this.searchThread}>Search</button>
                    </div>
                </div>


                {this.state.resultFound &&
                    <div className="table_class">
                        <div className="table-cell">
                            {resultThreads}
                        </div>
                    </div>
                }

                {this.state.noresult &&
                    <div>
                        <h3>Sorry No related thread found. Would you like to create one ? <button className="btn-success btn" onClick={this.createThread}>Create</button></h3>
                    </div>
                }
            </>
        )


    }


}

export default ComparerHomePage