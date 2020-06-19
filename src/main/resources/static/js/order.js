let userApi = "/api/user/";
let productInOrderApi = "/api/productinorder/";
let productInBagApi = "/api/productinbag/";
let orderApi = "/api/order/";

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
            order: [],
            phone: '',
            pcode: '',
            address: '',
            city: ''
        }
    },

    methods: {
        loadProductsInBag: function () {
            axios({
                method: "get",
                url: productInBagApi + "bag?bag=" + user.user.username + "_bag"
            })
                .then(response => {
                    this.productInBag = response.data;
                }).catch(error => {
                console.log(error);
            });
        },

        saveOrder: function(){
            this.loadProductsInBag();
            this.createOrder();

            setTimeout(function() {
                order.pushProductsToOrder();
            }, 2000);
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
            console.log(this.productInBag);
            formData.append("bag", this.productInBag[0].bag.name);
            console.log(this.productInBag.length);
            formData.append("length", this.productInBag.length);
            formData.append("number", this.order[0].number);

            axios.post( productInOrderApi,
                formData,
            ).then(response => {
                console.log(response.data);
            }).catch(error => {
                console.log(error);
            });
        }
    }
});