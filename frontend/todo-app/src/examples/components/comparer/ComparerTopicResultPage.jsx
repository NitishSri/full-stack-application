import React, { Component } from 'react'
import { Link } from 'react-router-dom'

class ComparerTopicResultPage extends Component {

    render() {
        const data = this.props.location.state.detail
        var newThread = false;
        console.log(data)
        var commentTopicOne = [];
        var commentTopicTwo = [];
        if(data.topicOne.comments==null){
            newThread = true;
        }
        if(!newThread){
            for (var i = 0; i < data.topicOne.comments.length; i++) {
                commentTopicOne.push(<div><textarea className="un" name="commentsOne" disabled rows="1" cols="20">{JSON.stringify(data.topicOne.comments[i].comment)}</textarea></div>);
            }
            for (var i = 0; i < data.topicTwo.comments.length; i++) {
                commentTopicTwo.push(<div><textarea className="un" name="commentsTwo" disabled rows="1" cols="20">{JSON.stringify(data.topicTwo.comments[i].comment)}</textarea></div>);
            }
        }
        
        return (
            <>
                <h3>{data.threadDisplayName}</h3>
                <div class="table_class">
                    <div class="table-cell">
                        <h1>{data.topicOne.name}</h1>
                        {commentTopicOne}
                        <div><textarea className="un" name="topicOne" rows="4" cols="50"></textarea></div>
                    </div>
                    <div class="table-cell">
                        <h1>{data.topicTwo.name}</h1>
                        {commentTopicTwo}
                        <div><textarea className="un" name="topicTwo" rows="4" cols="50"></textarea></div>
                    </div>
                </div>
                <button className="btn-success btn">POST</button>
            </>
        )


    }


}

export default ComparerTopicResultPage