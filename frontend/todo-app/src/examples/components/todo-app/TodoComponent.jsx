import React, { Component } from 'react'
import TodoDataService from './../../api/TodoDataService.js'
import AuthenticationService from '../../api/AuthenticationService.js'
import moment from 'moment'

class TodoComponent extends Component {

    constructor() {
        super()
        this.state = {
            todos: [

            ],
            message: null

        }

        this.refreshTodoList = this.refreshTodoList.bind(this)
        this.createTodo = this.createTodo.bind(this)
        this.deleteTodo = this.deleteTodo.bind(this)
        this.updateTodo = this.updateTodo.bind(this)
    }

    componentDidMount() {
        this.refreshTodoList()
    }

    createTodo() {
        this.props.history.push('/todos/new')
    }

    refreshTodoList() {
        let user = AuthenticationService.getLoggedInUserID();
        TodoDataService.retrieveTodos(user).then
            (response =>
                this.setState({
                    todos: response.data
                })
            )
    }

    deleteTodo(id, name) {
        TodoDataService.deleteTodo(id).then
            (response => {
                this.setState({
                    message: `Successfully deleted '${name}'`,
                })
                this.refreshTodoList()
            }
            ).catch(
                this.setState({
                    message: 'Oops something went wrong with the delete service',
                })

            )

    }

    updateTodo(id) {
        this.props.history.push(`/todos/${id}`)
    }

    render() {
        return (

            <div>
                <h1>Todos</h1>
                {this.state.message && <div className="alert alert-success">{this.state.message}</div>}
                <table className="table">
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Description</th>
                            <th>Target Date</th>
                            <th>Completed</th>
                            <th>Delete</th>
                            <th>Update</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            this.state.todos.map(
                                todo =>
                                    <tr key={todo.id}>
                                        <td>{todo.name}</td>
                                        <td>{todo.description}</td>
                                        <td>{moment(todo.targetDate).format('DD-MM-YYYY')}</td>
                                        <td>{todo.completed.toString()}</td>
                                        <td><button className="btn btn-warning" onClick={() => this.deleteTodo(todo.id, todo.name)}>Delete</button></td>
                                        <td><button className="btn btn-success" onClick={() => this.updateTodo(todo.id)}>Update</button></td>
                                    </tr>
                            )
                        }

                    </tbody>

                    <button className="btn btn-success" onClick={this.createTodo}>Add</button>

                </table>
            </div>


        )
    }

}

export default TodoComponent