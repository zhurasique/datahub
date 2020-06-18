let userApi = "/api/user/";

var user = new Vue({
    el: "#user",
    data: function(){
        return {
            user : ''
        }
    },

    methods: {
        loadUser: function () {
            axios({
                method: "get",
                url: userApi + "logged"
            })
                .then(response => {
                    this.user = response.data;
                }).catch(error => {
                console.log(error);
            });
        }
    },
    created: function () {
        this.loadUser(this.user);
    }
});