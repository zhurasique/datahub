let userApi = "/api/user/";
let productInBagApi = "/api/productinbag/"

var header = new Vue({
    el: "#header",
    data: function(){
        return {
            user : '',
            bag : []
        }
    },

    methods: {
        loadHeader: function () {
            this.loadUser();

            setTimeout(function () {
                    header.loadProductsToBag();
                }, 500);
        },

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

        loadProductsToBag: function () {
            axios({
                method: "get",
                url: productInBagApi + "bag?bag=" + this.user.username + "_bag"
            })
                .then(response => {
                    console.log(productInBagApi + "bag?bag=" + this.user.username + "_bag");
                    this.bag = response.data;
                }).catch(error => {
                console.log(error);
            });
        },
    },
    created: function () {
        this.loadHeader();
    }
});

document.addEventListener('DOMContentLoaded', function(){
    setTimeout(function() {
        let dropBag = document.getElementById("dropdown-bag");
        if(typeof header.user.username === 'undefined'){
            dropBag.style.left = "64%";
        }else{
            dropBag.style.left = "66.5%";
        }
    }, 100);
});