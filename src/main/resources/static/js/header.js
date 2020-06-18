let userApi = "/api/user/";

var header = new Vue({
    el: "#header",
    data: function(){
        return {
            user : '',
            bag : ''
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
        },

        loadBag: function () {
            axios({
                method: "get",
                url: bagApi + "name?name=" + this.user.username
            })
                .then(response => {
                    this.bag = response.data;
                    console.log("33");
                }).catch(error => {
                console.log(error);
            });
        }
    },
    created: function () {
        this.loadUser(this.user);
    }
});