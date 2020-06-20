let userApi = "/api/user/";
let productInOrderApi = "/api/productinorder/";
let productInBagApi = "/api/productinbag/";
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

var order = new Vue({
    el: "#order",
    data: function(){
        return {
            productInBag: [],
            showedProducts: [],
            totalPrice: 0,
            links: [],
            order: [],
            phone: '',
            pcode: '',
            address: '',
            city: '',
            images: []

        }
    },

    methods: {
        loadProductsInBag: function () {
            axios({
                method: "get",
                url: productInBagApi + "bag?bag=" + user.user.username + "_bag"
            })
                .then(response => {
                    this.totalPrice = 0;
                    this.productInBag = response.data;
                    this.showedProducts = response.data;
                    for(let i = 0; i < this.showedProducts.length; i++) {
                        this.totalPrice = this.totalPrice + parseFloat(this.showedProducts[i]["product"]["price"]);
                        if (isInteger(this.showedProducts[i]["product"]["price"])) {
                            this.showedProducts[i]["product"]["price"] = this.showedProducts[i]["product"]["price"].toString() + ",00 zł";
                        } else {
                            this.showedProducts[i]["product"]["price"] = this.showedProducts[i]["product"]["price"].toString().trim().replace(/\./g, ",") + " zł";
                        }
                        this.links.push("/product?product=" + this.showedProducts[i].product.id);
                    }
                    if (isInteger(this.totalPrice)) {
                        this.totalPrice = this.totalPrice.toString() + ",00 zł";
                    } else {
                        this.totalPrice = this.totalPrice.toString().trim().replace(/\./g, ",") + " zł";
                    }
                }).catch(error => {
                console.log(error);
            });
        },

        saveOrder: function(){
            this.loadProductsInBag();
            this.createOrder();

            setTimeout(function() {
                order.pushProductsToOrder();
                    setTimeout(function() {
                        document.location.href="/thank"
                    }, 200);
            }, 200);
        },

        createOrder: function(){
            let formData = new FormData();

            formData.append("username", user.user.username);
            formData.append("phone", this.phone);
            formData.append("pcode", this.pcode);
            formData.append("address", this.address);
            formData.append("city", this.city);

            axios.post( orderApi,
                formData,
            ).then(response => {
                console.log(response.data);
                this.order.push(response.data);
            }).catch(error => {
                console.log(error);
            });
        },

        pushProductsToOrder: function () {
            let formData = new FormData();
            formData.append("bag", this.productInBag[0].bag.name);
            formData.append("length", this.productInBag.length);
            formData.append("number", this.order[0].number);

            axios.post( productInOrderApi,
                formData,
            ).then(response => {
                console.log(response.data);
            }).catch(error => {
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

        delProductFromBag: function(product) {
            axios({
                method: "delete",
                url: productInBagApi + product.id
            })
                .then( response => {
                   this.loadProductsInBag();
                   this.loadImages();
                }).
            catch( error => {
                console.log(error);
            });
        },
    },

    created: function () {
        setTimeout(function() {
            order.loadProductsInBag(this.productInBag);
            order.loadImages(this.images);
        }, 200);
    }
});
