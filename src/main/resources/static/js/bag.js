function isInteger(num) {
    return (num ^ 0) === num;
}

var logged = new Vue({
    el: "#logged",
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

var bag = new Vue({
    el: "#bagg",
    data: function(){
        return {
            productInBag: [],
            showedProducts: [],
            totalPrice: 0,
            links: [],
            images: []
        }
    },

    methods: {
        loadProductsInBag: function () {
            axios({
                method: "get",
                url: "/api/productinbag/" + "bag?bag=" + logged.user.username + "_bag"
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
            bag.loadProductsInBag(this.productInBag);
            bag.loadImages(this.images);
        }, 200);
    }
});