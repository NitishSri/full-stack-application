import React, { Component } from 'react';
import AuthenticationService from '../../api/AuthenticationService.js';
import { Formik, Form, Field, ErrorMessage } from 'formik';

class RegistrationComponent extends Component {
  constructor(props) {
    super(props);

    this.state = {
      firstname: '',
      lastname: '',
      username: '',
      password: '',
      confirmpassword: '',
      serviceError: false,
      userExist: false,
      message: null,
    };

    this.register = this.register.bind(this);
    this.validate = this.validate.bind(this);
    this.handleChangedEvent = this.handleChangedEvent.bind(this);
  }

  handleChangedEvent(event) {
    this.setState({ [event.target.name]: event.target.value });
  }

  validate(values) {
    const errors = {};
    if (!values.firstname) {
      errors.firstname = 'Enter the firstname';
    } else if (!values.lastname) {
      errors.lastname = 'Enter the lastname';
    } else if (!values.username) {
      errors.username = 'Enter the username';
    } else if (!values.password) {
      errors.password = 'Enter the password';
    } else if (!values.confirmpassword) {
      errors.confirmpassword = 'Enter the confirm password';
    } else if (values.confirmpassword != values.password) {
      errors.confirmpassword = 'Password and confirm password do not match';
    }
    return errors;
  }

  register(values) {
    AuthenticationService.register(values.firstname, values.lastname, values.username, values.password).then(
      (response) => {
        if (response.data.userExists === true) {
          this.setState({ userExist: true });
          this.setState({ serviceError: false });
          this.setState({ message: 'User already exist. Please enter different username' });
        } else {
          AuthenticationService.validateCredentials(values.username, values.password).then(
            (response) => {
              if (response.data.invalidCredentials === true) {
                this.setState({ inValidLogin: true });
                this.setState({ serviceError: false });
              } else {
                AuthenticationService.loginUser(values.username, response.data.token);
                this.props.history.push(`/welcome/${response.data.firstname}`);
              }
            },
          )
            .catch(
              this.setState({ serviceError: true, message: 'Oops something went wrong with the Register service' }),
            );
        }
      },
    )
      .catch(
        this.setState({ serviceError: true, message: 'Oops something went wrong with the Register service' }),
      );
  }


  render() {
    const { firstname, lastname, username, password, confirmpassword } = this.state;
    return (
      <div>
        <h3>Todo Application</h3>
        {this.state.message && <div className="alert alert-warning">{this.state.message}</div>}
        <div className="container">
          <Formik
            initialValues={{ firstname, lastname, username, password, confirmpassword }}
            onSubmit={this.register}
            validate={this.validate}
            validateOnBlur={false}
            validateOnChange={false}
            enableReinitialize={true}
          >

            {

              props => (
                <Form>
                  <ErrorMessage component="div" className="alert alert-warning" name="firstname" />
                  <ErrorMessage component="div" className="alert alert-warning" name="lastname" />
                  <ErrorMessage component="div" className="alert alert-warning" name="username" />
                  <ErrorMessage component="div" className="alert alert-warning" name="password" />
                  <ErrorMessage component="div" className="alert alert-warning" name="confirmpassword" />
                  <table>
                    <tr>
                      <fieldset className="form-group">
                        <td><label><b>Firstname</b></label></td>
                        <td><Field className="form-control un" size="100" type="text" name="firstname"></Field></td>
                      </fieldset>
                    </tr>
                    <tr>
                      <fieldset className="form-group">
                        <td> <label><b>Lastname</b></label></td>
                        <td><Field className="form-control un" size="100" type="text" name="lastname"></Field></td>
                      </fieldset>
                    </tr>
                    <tr>
                      <fieldset className="form-group">
                        <td><label><b>Username</b></label></td>
                        <td><Field className="form-control un" size="100" type="text" name="username"></Field></td>
                      </fieldset>
                    </tr>
                    <tr>
                      <fieldset className="form-group">
                        <td><label><b>Password</b></label></td>
                        <td><Field className="form-control un" size="100" type="password" name="password"></Field></td>
                      </fieldset>
                    </tr>
                    <tr>
                      <fieldset className="form-group">
                        <td><label><b>Confirm Password</b></label></td>
                        <td><Field validateOnChange="" className="form-control un" size="100" type="password" name="confirmpassword"></Field></td>
                      </fieldset>
                    </tr>
                  </table>
                  <button className="submit  btn-success btn">Register</button>
                </Form>

              )
            }
          </Formik>
        </div>
      </div>


    );
  }
}

export default RegistrationComponent;
