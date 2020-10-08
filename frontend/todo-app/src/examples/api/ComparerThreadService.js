import axios from 'axios';

class ComparerThreadService {
  searchThread(threadDetails) {
    return axios.post('http://localhost:9093/search/thread', threadDetails);
  }

  createThread(threadDetails) {
    return axios.post('http://localhost:9093/create/thread', threadDetails);
  }

  openThread(threadName, loggedInUser) {
    return axios.get(`http://localhost:9093/thread/${threadName}/${loggedInUser}`);
  }

  postComment(threadComments) {
    return axios.post('http://localhost:9093/post/comment', threadComments);
  }

  deleteComment(threadName, commentID) {
    return axios.delete(`http://localhost:9093/delete/comment/${threadName}/${commentID}`);
  }

  likeComment(loggedUser, threadName, commentID) {
    return axios.put(`http://localhost:9093/like/comment/${loggedUser}/${threadName}/${commentID}`);
  }

  dislikeComment(loggedUser, threadName, commentID) {
    return axios.put(`http://localhost:9093/dislike/comment/${loggedUser}/${threadName}/${commentID}`);
  }
}

export default new ComparerThreadService();
