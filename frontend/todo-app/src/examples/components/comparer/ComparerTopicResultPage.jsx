/* eslint-disable no-var */
import React, { Component } from 'react';
import ComparerThreadService from '../../api/ComparerThreadService.js';
import createHistory from 'history/createBrowserHistory';

class ComparerTopicResultPage extends Component {
  constructor(props) {
    super(props);

    this.state = {
      topicOneComment: '',
      topicTwoComment: '',
      responseData: null,
      serviceError: false,
    };

    this.handleChangedEvent = this.handleChangedEvent.bind(this);
  }


  postComment = () => {
    const history = createHistory();
    const data = {};

    data.commentAuthor = sessionStorage.getItem('authenticatedUser');
    data.commentLike = 0;
    data.commentDislike = 0;
    data.commentOne = this.state.topicOneComment;
    data.commentTwo = this.state.topicTwoComment;
    data.threadName = this.props.location.state.threadName;


    ComparerThreadService.postComment(data)
      .then((response) => {
        history.go(0);
      })
      .catch(
        this.setState({
          serviceError: true,
          message: 'Oops something went wrong with the Open Thread service',
        }),

      );
    this.setState({
      topicOneComment: '', topicTwoComment: '',
    });
  }

  handleChangedEvent(event) {
    this.setState({ [event.target.name]: event.target.value });
  }

  componentDidMount() {
    const { threadName } = this.props.location.state;
    ComparerThreadService.openThread(threadName)
      .then((response) => {
        this.setState({
          responseData: response.data,
        });
      })
      .catch(() => this.setState({
        serviceError: true,
        message: 'Oops something went wrong with the Open Thread service',
      }));
  }

  deleteComment = (threadName, commentID) => {
    const history = createHistory();
    ComparerThreadService.deleteComment(threadName, commentID)
      .then((response) => {
        history.go(0);
      })
      .catch(() => this.setState({
        serviceError: true,
        message: 'Oops something went wrong with the Delete Comment service',
      }));
  }

  likeComment = (loggedUser, threadName, commentID) => {
    const history = createHistory();
    ComparerThreadService.likeComment(loggedUser, threadName, commentID)
      .then((response) => {
        history.go(0);
      })
      .catch(() => this.setState({
        serviceError: true,
        message: 'Oops something went wrong with the Like Comment service',
      }));

  }

  dislikeComment = (loggedUser, threadName, commentID) => {
    const history = createHistory();
    ComparerThreadService.dislikeComment(loggedUser, threadName, commentID)
      .then((response) => {
        history.go(0);
      })
      .catch(() => this.setState({
        serviceError: true,
        message: 'Oops something went wrong with the Dislike Comment service',
      }));

  }

  render() {
    if (!this.state.responseData) {
      return (<div>loading</div>);
    }
    const commentsLength = this.state.responseData.comments.length;
    const loggedUser = sessionStorage.getItem('authenticatedUser');
    let sameUser = false;

    if (commentsLength > 0) {
      var commentTopicOne = [];
      var commentTopicTwo = [];
      for (var i = 0; i < this.state.responseData.comments.length; i++) {
        if (loggedUser === this.state.responseData.comments[i].commentAuthor) {
          sameUser = true;
        } else {
          sameUser = false;
        }
        const { threadName } = this.state.responseData.comments[i];
        const { commentID } = this.state.responseData.comments[i];
        const { commentLike } = this.state.responseData.comments[i];
        const { commentDislike } = this.state.responseData.comments[i];
        commentTopicOne.push(<div><a href='' onClick={() => this.likeComment(loggedUser, threadName, commentID)}>Like ({commentLike})</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href='' onClick={() => this.dislikeComment(loggedUser, threadName, commentID)}>Dislike ({commentDislike})</a>&nbsp;&nbsp;&nbsp;&nbsp;{sameUser && <a href='' onClick={() => this.deleteComment(threadName, commentID)}>Delete</a>}<textarea className="un" name="commentsOne" disabled rows="1" cols="20">{JSON.stringify(this.state.responseData.comments[i].commentOne)}</textarea></div>);
        commentTopicTwo.push(<div><textarea className="un" name="commentsTwo" disabled rows="1" cols="20">{JSON.stringify(this.state.responseData.comments[i].commentTwo)}</textarea></div>);
      }
    }


    return (
      <>
        <h3>{this.state.responseData.threadDisplayName}</h3>
        <div class="table_class table-wrapper">
          <div class="table-cell">
            <h1>{this.state.responseData.topicOne}</h1>
            {commentsLength > 0 && commentTopicOne}
            <div><textarea className="un" onChange={this.handleChangedEvent} value={this.state.topicOneComment} name="topicOneComment" rows="4" cols="50"></textarea></div>
          </div>
          <div class="table-cell">
            <h1>{this.state.responseData.topicTwo}</h1>
            {commentsLength > 0 && commentTopicTwo}
            <div><textarea className="un" onChange={this.handleChangedEvent} value={this.state.topicTwoComment} name="topicTwoComment" rows="4" cols="50"></textarea></div>
          </div>
        </div>
        <button className="btn-success btn" onClick={this.postComment}>POST</button>
      </>
    );
  }
}

export default ComparerTopicResultPage;
