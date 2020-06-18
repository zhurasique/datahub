let registrationApi = "/api/user/";

var registration = new Vue({
    el: "#registration",
    data: function(){
        return {
            username: '',
            email: '',
            password: '',
            name: '',
            surname: ''
        }
    },

    methods: {
        registerUser: function () {
            axios({
                method: "post",
                url: registrationApi,
                data: {
                    username: this.username,
                    email:  this.email,
                    password: this.password,
                    surname: this.surname,
                    name: this.name
                }
            }).then(
                document.location.href="/login"
            ).catch(error => {
                console.log(error);
            });
        }
    }
});