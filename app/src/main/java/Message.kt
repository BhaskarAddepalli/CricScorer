class Message {

    var message:String?=null
    var senderId:String?=null
    var senderName:String?=null
    constructor(){

    }
    constructor(message: String?,send:String?,senderName:String?){
        this.message=message
        this.senderId=send
        this.senderName=senderName
    }
}