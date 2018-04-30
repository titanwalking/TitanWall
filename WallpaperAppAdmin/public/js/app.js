var config = {
  apiKey: "AIzaSyAD2stCPJySZIRZyKgs9QXRB5_174wm4pI",
  authDomain: "titan-wallpaper.firebaseapp.com",
  databaseURL: "https://titan-wallpaper.firebaseio.com",
  projectId: "titan-wallpaper",
  storageBucket: "titan-wallpaper.appspot.com",
  messagingSenderId: "171923747943"
};
firebase.initializeApp(config);

firebase.auth.Auth.Persistence.LOCAL;

$("#btn-login").click(function () {

  var email = $("#email").val();
  var password = $("#password").val();

  var result = firebase.auth().signInWithEmailAndPassword(email, password);

  result.catch(function (error) {
    var errorCode = error.code;
    var errorMessage = error.message;

    console.log(errorCode);
    console.log(errorMessage);
  });

});

$("#btn-logout").click(function () {
  firebase.auth().signOut();
})

function switchView(view) {
  $.get({
    url: view,
    cache: false,
  }).then(function (data) {
    $("#container").html(data);
  });
}