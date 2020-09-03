import React, { Component } from 'react';
import './Counter.css';
// import { render } from '@testing-library/react';
import PropTypes from 'prop-types';


class Counter extends Component {
  constructor() {
    super();
    this.state = {
      counter: 0,
    };

    this.increment = this.increment.bind(this);
    this.decrement = this.decrement.bind(this);
    this.reset = this.reset.bind(this);
  }

  increment(by) {
    this.setState(
      (previousState) =>  {
        return { counter: previousState.counter + by };
      },
    );
  }

  decrement(by) {
    this.setState(
      (previousState) =>  {
        return { counter: previousState.counter - by };
      },
    );
  }

  reset() {
    this.setState({
      counter: 0,
    });
  }

  render() {
    return (
      <div className="counter">
        <CounterButton incrementMethod={this.increment} decrementMethod={this.decrement}></CounterButton>
        <CounterButton incrementMethod={this.increment} decrementMethod={this.decrement} by = {5}></CounterButton>
        <CounterButton incrementMethod={this.increment} decrementMethod={this.decrement} by = {10}></CounterButton>
        <span className="count">{this.state.counter}</span>
        <div><button className="reset" onClick={this.reset}>Reset</button></div>
      </div>
    );
  }
}


class CounterButton extends Component {
  /*
   *Do not need this because directly calling parent increment method
   *constructor(){
   *    super();
   *    this.state = {
   *        counter : 0
   *    }
   *
   *this.increment = this.increment.bind(this)
   *this.decrement = this.decrement.bind(this)
   *}
   */

  render() {
    return (
      <div className="counterbutton">
        <button onClick={() => this.props.incrementMethod(this.props.by)}>+{this.props.by}</button>
        <button onClick={() => this.props.decrementMethod(this.props.by)}>-{this.props.by}</button>
        {/* <span className="count">{this.state.counter}</span>*/}
      </div>
    );
  }

  /*
   *increment = () => {  Doing this you dont need to bind the method
   *increment(){
   *
   *
   * this.props.incrementMethod(this.props.by)
   *}
   *
   *decrement(){
   *     this.props.decrementMethod(this.props.by)
   *  }
   */
}

// Give the default value 1
CounterButton.defaultProps =  {
  by: 1,
};

// Give the strict type of the prop by in this case used
CounterButton.propTypes = {
  by: PropTypes.number,
};

export default Counter;

