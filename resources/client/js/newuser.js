function UsersCreate() {

    let Usernameinput = document.getElementById("Username").value;
    let Passwordinput = document.getElementById("Password").value;
    let Password2input = document.getElementById("Password2").value;
    let FirstNameinput = document.getElementById("FirstName").value;
    let LastNameinput = document.getElementById("LastName").value;
    //code above sets variables with values corresponding to contents of input boxes

    if (Usernameinput.length === 0) {
        alert("Username must be provided"); //this code and the following if statements are checking that all inputs are valid, one by one, and once all are checked, the data is handed to the /Users/create API
    } else if (Passwordinput.length < 8) {
        alert("Password must be 8 characters or more");
    } else if (Password2input.length < 8) {
        alert("Re-entered password must 8 characters or more")
    } else if (FirstNameinput.length === 0) {
        alert("First name must be provided")
    } else if (LastNameinput.length === 0) {
        alert("Last name must be provided")
    } else if (Passwordinput !== Password2input) {
        alert("Passwords are not identical, please re-enter")
    } else {
        console.log("Invoked UsersCreate()");
        let url = "/Users/create";
        let formData = new FormData(document.getElementById("createform"));

        fetch(url, {
            method: "POST",
            body: formData,
        }).then(response => {
            return response.json();
        }).then(response => {
            if (response.hasOwnProperty("Error")) {
                alert(JSON.stringify(response));
            } else {
                window.open("login.html", "_self");
                alert("Account successfully created");
            }
        })
    }
}