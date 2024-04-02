const radioButtons = document.querySelectorAll(".input_by_radio");

// Adding event listener to each radio button
radioButtons.forEach(radioButton => {
    radioButton.addEventListener('change', function(e) {
        const selectedValue = e.target.value
        console.log(selectedValue)
        // Displaying corresponding input based on selected radio button
        if (selectedValue === 'minute') {
         $("#minuteInput").show();
          $("#hourInput").hide();
           $("#dayInput").hide();
        
         
        } else if (selectedValue === 'hour') {
           $("#minuteInput").hide()
          $("#hourInput").show()
           $("#dayInput").hide();
        } else if (selectedValue === 'day') {
          $("#minuteInput").hide();
          $("#hourInput").hide();
           $("#dayInput").show();
        }
    });	
});





var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var chat_access_form = document.querySelector('#chat_access_form');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');  
var connectingElement = document.querySelector('.connecting');
let roomId=document.getElementById("id_room_discuss");
let studentID=roomId.getAttribute("data-id");

var stompClient = null;
var username = null;




if(chat_access_form){
	 connect()
}

function connect() {
    username ="test name"
        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        // Connect to the specific room
        stompClient.connect({}, function() {
            onConnected(roomId.value);
        }, onError);
 
}



function onConnected() {
    // Subscribe to the Public Topic
    stompClient.subscribe(`/topic/public/${roomId.value}`, onMessageReceived);

    // Tell your username to the server
    stompClient.send(`/app/chat.addUser/${roomId.value}`,
        {},
        JSON.stringify({sender: username, type: 'JOIN'})
    )

    connectingElement.classList.add('hidden');
}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    if(messageContent && stompClient) {
        var chatMessage = {
            sender: username,
            content: messageInput.value,
            id:studentID,
            room_id:roomId.value,
            type: 'CHAT'
        };
        stompClient.send(`/app/chat.sendMessage/${roomId.value}`, {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}


function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

if(message.type=="OVER"){

	Toastify({
  text: "This discuss is expried, mesaage can't send",
  duration:2000,
   style: {
    background: "orange",
  },
  offset: {
    x: 200, // horizontal axis - can be a number or a string indicating unity. eg: '2em'
    y: 200 // vertical axis - can be a number or a string indicating unity. eg: '2em'
  },
}).showToast();
}


if(message.type=="CHAT"){
	 var messageElement = document.createElement('div');
    
    
    if(message.id==studentID){
		 messageElement.innerHTML=`
		<div class="outgoing_msg">
		  <div class="sent_msg">
			<p>${message.content}</p>
			<span class="time_date"> 11:01 AM    |    Today</span> </div>
		</div>`
	}else{
	 messageElement.innerHTML=	`<div class="incoming_msg">
		  <div class="incoming_msg_img"> <img src="https://ptetutorials.com/images/user-profile.png" alt="sunil"> </div>
		  <div class="received_msg">
			<div class="received_withd_msg">
			<span style="font-size:18px" class="time_date">${message.sender}</span>
			  <p>${message.content}</p>
			  <span class="time_date"> 11:01 AM    |    Today</span>
			  </div>
		  </div>
		</div>
		`
	}
   
    
    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}
   
}



messageForm.addEventListener('submit', sendMessage, true)