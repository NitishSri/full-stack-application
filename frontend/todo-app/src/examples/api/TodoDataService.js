import axios from 'axios';

class TodoDataService {
  retrieveTodos(name) {
    return axios.get(`http://localhost:9090/users/${name}/todos`);
  }

  retrieveSingleTodo(id) {
    return axios.get(`http://localhost:9090/todos/fetch/${id}`);
  }

  createTodo(todo) {
    return axios.post('http://localhost:9090/todos/add', todo);
  }

  deleteTodo(id) {
    return axios.delete(`http://localhost:9090/todos/${id}`);
  }

  updateTodo(todo) {
    return axios.put('http://localhost:9090/todos/update', todo);
  }
}

export default new TodoDataService();
