import React, { Component } from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import moment from 'moment';
import AuthenticationService from '../../api/AuthenticationService.js';
import TodoDataService from '../../api/TodoDataService';

class SingleTodoComponent extends Component {
  constructor(props) {
    super(props);
    this.state = {
      id: this.props.match.params.id,
      name: '',
      description: '',
      targetDate: moment(new Date()).format('yyyy-MM-DD'),
      completed: false,
      userid: '',
      message: null,
    };

    this.onSubmitTodo = this.onSubmitTodo.bind(this);
    this.validate = this.validate.bind(this);
    this.fetchSingleTodo = this.fetchSingleTodo.bind(this);
  }

  validate(values) {
    const errors = {};
    if (!values.name) {
      errors.name = 'Enter the name';
    } else if (values.name.length < 3) {
      errors.name = 'Name atleast require 3 character';
    } else if (!values.description) {
      errors.description = 'Enter the description';
    } else if (!moment(values.targetDate).isValid) {
      errors.targetDate = 'Date is not valid';
    }
    return errors;
  }

  onSubmitTodo(values) {
    const user = AuthenticationService.getLoggedInUserID();
    if (this.state.id === 'new') {
      TodoDataService.createTodo({
        name: values.name, description: values.description,
        targetDate: values.targetDate, completed: values.completed, userId: user,
      }).then
      (
        () => this.props.history.push('/todos'),
      )
        .catch(
          this.setState({
            message: 'Oops something went wrong with the Create service',
          }),
        );
    } else {
      TodoDataService.updateTodo({
        id: this.state.id, name: values.name, description: values.description,
        targetDate: values.targetDate, completed: values.completed, userId: user,
      }).then
      (
        () => this.props.history.push('/todos'),
      )
        .catch(
          this.setState({
            message: 'Oops something went wrong with the Update service',
          }),
        );
    }
  }

  fetchSingleTodo(id) {
    TodoDataService.retrieveSingleTodo(id).then
    ((response) => {
      this.setState({
        name: response.data.name,
        description: response.data.description,
        targetDate: moment(response.data.targetDate).format('yyyy-MM-DD'),
        completed: response.data.completed,
        message: null,
      });
    })
      .catch(
        this.setState({
          message: 'Oops something went wrong with the Get service',
        }),

      );
  }

  componentDidMount() {
    if (this.state.id === 'new') {
      return;
    }
    this.fetchSingleTodo(this.state.id);
  }

  render() {
    const { name, description, targetDate, completed } = this.state;
    return (

      <div>
        <h1>Todo</h1>
        {this.state.message && <div className="alert alert-warning">{this.state.message}</div>}
        <div className="container">
          <Formik
            initialValues={{ name, description, targetDate, completed }}
            onSubmit={this.onSubmitTodo}
            validate={this.validate}
            validateOnBlur={false}
            validateOnChange={false}
            enableReinitialize={true}

          >
            {

              props => (
                <Form>
                  <ErrorMessage component="div" className="alert alert-warning" name="name" />
                  <ErrorMessage component="div" className="alert alert-warning" name="description" />
                  <ErrorMessage component="div" className="alert alert-warning" name="completed" />
                  <ErrorMessage component="div" className="alert alert-warning" name="targetDate" />
                  <fieldset className="form-group">
                    <label><b>Name</b></label>
                    <Field className="form-control" type="text" name="name"></Field>
                  </fieldset>
                  <fieldset className="form-group">
                    <label><b>Description</b></label>
                    <Field className="form-control" type="text" name="description"></Field>
                  </fieldset>
                  <fieldset className="form-group">
                    <label><b>Completed</b></label>
                    <Field className="form-control" type="text" name="completed"></Field>
                  </fieldset>
                  <fieldset className="form-group">
                    <label><b>Target Date</b></label>
                    <Field className="form-control" type="date" name="targetDate"></Field>
                  </fieldset>
                  <button className="btn btn-success">Save</button>
                </Form>

              )
            }

          </Formik>


        </div>

      </div>

    );
  }
}

export default SingleTodoComponent;
