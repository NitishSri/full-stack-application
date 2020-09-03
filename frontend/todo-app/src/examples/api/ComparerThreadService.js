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
}

export default new ComparerThreadService();
