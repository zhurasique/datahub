let userApi = "/api/user/";
let productInBagApi = "/api/productinbag/"

var header = new Vue({
    el: "#header",
    data: function(){
        return {
            user : '',
            bag : [],
            images: [],
            links: []
        }
    },

    methods: {
        loadHeader: function () {
            this.loadUser();

            setTimeout(function () {
                    header.loadProductsToBag();
                    header.loadImages();
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
                    this.bag = response.data;
                    for(let i = 0; i < this.bag.length; i++){
                        this.links.push("/product?product=" +  this.bag[i].product.id);
                    }
                }).catch(error => {
                console.log(error);
            });
        },

        loadImages: function(){
            axios({
                method: "get",
                url: "/api/productimage/" + "unique"
            })
                .then( response => {
                    this.images = response.data;

                    for(let i = 0; i < this.images.length; i++)
                        this.images[i]["image"] = "http://localhost/dashboard/images/datahub/" + this.images[i]["image"];

                }).
            catch( error => {
                console.log(error);
            });
        },

        delProductFromBag: function(product) {
            axios({
                method: "delete",
                url: "/api/productinbag/" + product.id
            })
                .then( response => {
                    this.loadProductsToBag();
                    this.loadImages();
                }).
            catch( error => {
                console.log(error);
            });
        }
    },
    created: function () {
        this.loadHeader();
    }
});

document.addEventListener('DOMContentLoaded', function(){
    setTimeout(function() {
        let dropBag = document.getElementById("dropdown-bag");
        if(typeof header.user.username === 'undefined'){
            dropBag.style.left = "55%";
        }else{
            dropBag.style.left = "57.5%";
        }
    }, 100);
});