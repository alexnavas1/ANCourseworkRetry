function getProgress(){
        console.log("Invoked getProgress()");     //console.log your BFF for debugging client side
        const UserID = document.getElementById("UserID").value;  //get the UserId from the HTML element with id=UserID
        //let UserID = 1; 			  //You could hard code it if you have problems
        //debugger;				  //debugger statement to allow you to step through the code in console dev F12
        const url = "/Users/getProgress/";       // API method on webserver
        fetch(url + UserID, {                // UserID as a path parameter
            method: "GET",
        }).then(response => {
            return response.json();                         //return response to JSON
        }).then(response => {
            if (response.hasOwnProperty("Error")) {         //checks if response from server has an "Error"
                alert(JSON.stringify(response));            // if it does, convert JSON object to string and alert
            } else {
                document.getElementById("DisplayOneUser").innerHTML = response.UserID + " " + response.UserName;  //output data
            }
        });
}
