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
      const data = this.state.responseData;
      console.log('data');
      console.log(data);

      const topicOnecomments = {};
      const topicTwocomments = {};
      topicOnecomments.commentAuthor = sessionStorage.getItem('authenticatedUser');
      topicOnecomments.like = 0;
      topicOnecomments.dislike = 0;
      topicOnecomments.comment = this.state.topicOneComment;

      topicTwocomments.commentAuthor = sessionStorage.getItem('authenticatedUser');
      topicTwocomments.like = 0;
      topicTwocomments.dislike = 0;
      topicTwocomments.comment = this.state.topicTwoComment;
      data.topicOne.comments = [];
      data.topicTwo.comments = [];
      data.topicOne.comments.push(topicOnecomments);
      data.topicTwo.comments.push(topicTwocomments);

      ComparerThreadService.postComment(data).then
      ((response) => {
        history.go(0);
      })
        .catch(
          this.setState({
            serviceError: true,
            message: 'Oops something went wrong with the Open Thread service',
          }),

        );
      this.state.topicOneComment = '';
      this.state.topicTwoComment = '';
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

    render() {
      if (!this.state.responseData) {
        return (<div>loading</div>);
      }
      const { newThread } = this.state.responseData;

      if (!newThread) {
        var commentTopicOne = [];
        var commentTopicTwo = [];
        for (var i = 0; i < this.state.responseData.topicOne.comments.length; i++) {
          commentTopicOne.push(<div><textarea className="un" name="commentsOne" disabled rows="1" cols="20">{JSON.stringify(this.state.responseData.topicOne.comments[i].comment)}</textarea></div>);
        }
        for (var i = 0; i < this.state.responseData.topicTwo.comments.length; i++) {
          commentTopicTwo.push(<div><textarea className="un" name="commentsTwo" disabled rows="1" cols="20">{JSON.stringify(this.state.responseData.topicTwo.comments[i].comment)}</textarea></div>);
        }
      }


      return (
        <>
          <h3>{this.state.responseData.threadDisplayName}</h3>
          <div class="table_class table-wrapper">
            <div class="table-cell">
              <h1>{this.state.responseData.topicOne.name}</h1>
              {!newThread && commentTopicOne}
              <div><textarea className="un" onChange={this.handleChangedEvent} value={this.state.topicOneComment} name="topicOneComment" rows="4" cols="50"></textarea></div>
            </div>
            <div class="table-cell">
              <h1>{this.state.responseData.topicTwo.name}</h1>
              {!newThread && commentTopicTwo}
              <div><textarea className="un" onChange={this.handleChangedEvent} value={this.state.topicTwoComment} name="topicTwoComment" rows="4" cols="50"></textarea></div>
            </div>
          </div>
          <button className="btn-success btn" onClick={this.postComment}>POST</button>
        </>
      );
    }
}

export default ComparerTopicResultPage;
