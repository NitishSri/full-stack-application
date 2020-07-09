import React from 'react';
//import logo from './logo.svg';
import './App.css';
import './bootstrap.css';
//import Counter from './examples/counter/Counter'
import TodoApp from './examples/components/todo-app/TodoApp'

function App() {
  return (
    <div className="App">
      {/*<Counter></Counter>*/}
      <TodoApp/>
    </div>
  );
}

export default App;
