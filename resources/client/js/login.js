function postUserLogin() {
    //debugger;
    console.log("Invoked UsersLogin() ");
    let url = "/Users/login";
    var formData = new FormData(document.getElementById('LoginForm'));

    fetch(url, {
        method: "POST",
        body: formData,
    }).then(response => {
        return response.json();                 //now return that promise to JSON
}).then(response => {
        if (response.hasOwnProperty("Error")) {
        alert(JSON.stringify(response));        // if it does, convert JSON object to string and alert
    } else {
        Cookies.set("Token", response.Token);
        Cookies.set("Username", response.Username);
        window.open("index.html", "_self");       //open index.html in same tab
    }
});
}
