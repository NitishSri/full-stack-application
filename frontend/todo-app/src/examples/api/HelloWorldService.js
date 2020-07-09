import axios from 'axios'

class HelloWorldService {

    retriveMessage() {
        return axios.get("http://localhost:9090/hello")
    }

    loginUser(){
        return axios.post("http://localhost:9090/login",{firstname : "nitish", password : "nitish"})
    }

}

export default new HelloWorldService()