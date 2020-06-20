let userApi = "/api/user/";
let productApi = "/api/product/";
let bagApi = "/api/bag/";
let productInBagApi = "/api/productinbag/";

function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, '\\$&');
    var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, ' '));
}

function isInteger(num) {
    return (num ^ 0) === num;
}

var byId = getParameterByName('product');

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

var product = new Vue({
    el: "#product",
    data: function(){
        return {
            product : [],
            bag: [],
            productInBag: []
        }
    },

    methods: {
        loadProduct: function () {
            axios({
                method: "get",
                url: productApi + "id?id=" + byId.toString()
            })
                .then(response => {
                    console.log(response.data);
                    this.product = response.data;
                }).catch(error => {
                console.log(error);
            });
        },

        orderProduct: function() {
            this.createBag();
            setTimeout(function() {
                console.log(product.bag);
                product.loadProductToBag();
            }, 500);
        },

        createBag: function() {
            let formData = new FormData();

            formData.append("username", user.user.username);

            axios.post( bagApi,
                formData,
            ).then(response => {
                console.log(response.data);
                this.bag.push(response.data);
            }).catch(error => {
                console.log(error);
            });
        },

        loadProductToBag: function() {
            let formData = new FormData();

            console.log(this.bag[0].name);
            formData.append("product", this.product.id);
            formData.append("bag", product.bag[0].name);


            axios.post( productInBagApi,
                formData,
            ).then(response => {
                this.productInBag.push(response.data);
            }).catch(error => {
                console.log(error);
            });
        },
    },
    created: function () {
        this.loadProduct(this.product);
    }
});