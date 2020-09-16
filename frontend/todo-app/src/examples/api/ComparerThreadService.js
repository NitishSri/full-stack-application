import axios from 'axios';

class ComparerThreadService {
  searchThread(threadDetails) {
    return axios.post('http://localhost:9093/search/thread', threadDetails);
  }

  createThread(threadDetails) {
    return axios.post('http://localhost:9093/create/thread', threadDetails);
  }

  openThread(threadName) {
    return axios.get(`http://localhost:9093/thread/${threadName}`);
  }

  postComment(threadComments) {
    return axios.post('http://localhost:9093/post/comment', threadComments);
  }

  deleteComment(threadName, commentID) {
    return axios.delete(`http://localhost:9093/delete/comment/${threadName}/${commentID}`);
  }
}

export default new ComparerThreadService();
