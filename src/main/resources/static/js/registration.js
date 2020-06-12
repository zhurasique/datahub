let departmentApi = "/api/user/";

var registration = new Vue({
    el: "#registration",
    data: function(){
        return {
            username: '',
            email: '',
            password: ''
        }
    },

    methods: {
        registerUser: function () {
            axios({
                method: "post",
                url: departmentApi,
                data: {
                    username: this.username,
                    email:  this.email,
                    password: this.password
                }
            }).then(response => {
                console.log(response.data);
            }).catch(error => {
                console.log(error);
            });
        }
    }
});