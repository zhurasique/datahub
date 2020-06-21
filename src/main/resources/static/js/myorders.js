let productInOrderApi = "/api/productinorder/";
let orderApi = "/api/order/";
let productImagesApi = "/api/productimage/";

function isInteger(num) {
    return (num ^ 0) === num;
}

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
                url: "/api/user/" + "logged"
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

var info = new Vue({
    el: "#info",
    data: function(){
        return {
            productInOrders: [],
            images: [],
            totalPrice: 0,
            links: [],
            orders: []
        }
    },

    methods: {
        loadOrder: function () {
            axios({
                method: "get",
                url: orderApi + "user?user=" +user.user.username
            })
                .then( response => {
                    this.orders = response.data;
                }).
            catch( error => {
                console.log(error);
            });

            setTimeout(function() {
                info.loadProductsInOrder();
            }, 200);
        },

        loadProductsInOrder: function(){
            axios({
                method: "get",
                url: productInOrderApi + "user?user=" + user.user.username
            })
                .then( response => {
                    this.totalPrice = 0;
                    this.productInOrders = response.data;
                    for(let i = 0; i < this.productInOrders.length; i++) {
                        this.totalPrice = this.totalPrice + parseFloat(this.productInOrders[i]["product"]["price"]);
                        if (isInteger(this.productInOrders[i]["product"]["price"])) {
                            this.productInOrders[i]["product"]["price"] = this.productInOrders[i]["product"]["price"].toString() + ",00 zł";
                        } else {
                            this.productInOrders[i]["product"]["price"] = this.productInOrders[i]["product"]["price"].toString().trim().replace(/\./g, ",") + " zł";
                        }
                        this.links.push("/product?product=" + this.productInOrders[i].product.id);
                    }
                    if (isInteger(this.totalPrice)) {
                        this.totalPrice = this.totalPrice.toString() + ",00 zł";
                    } else {
                        this.totalPrice = this.totalPrice.toString().trim().replace(/\./g, ",") + " zł";
                    }

                    this.loadImages();
                }).
            catch( error => {
                console.log(error);
            });
        },

        loadImages: function(){
            axios({
                method: "get",
                url: productImagesApi + "unique"
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

        loadInfo: function () {
            this.loadOrder();

            setTimeout(function () {
                info.loadOrder();
            }, 200);
        }
    },

    created: function() {
        this.loadInfo();
    }
});