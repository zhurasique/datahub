let productApi = "/api/product/";
let bagApi = "/api/bag/";
let productImageApi = "/api/productimage/";

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

var product = new Vue({
    el: "#product",
    data: function(){
        return {
            product: [],
            bag: [],
            productInBag: [],
            productImages: []
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

                    this.loadProductImages();
                }).catch(error => {
                console.log(error);
            });
        },

        orderProduct: function() {
            this.createBag();

            showPopup();

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

        loadProductImages: function(){
            axios({
                method: "get",
                url: productImageApi + "product?name=" + this.product.name
            })
                .then(response => {
                    console.log(response.data);
                    this.productImages = response.data;
                    for(let i = 0; i < this.productImages.length; i++)
                        this.productImages[i]["image"] = "http://localhost/dashboard/images/datahub/" + this.productImages[i]["image"];
                }).catch(error => {
                console.log(error);
            });
        },

        loadProductToBag: function() {
            let formData = new FormData();

            console.log(this.bag[0].name);
            formData.append("product", this.product.id);
            formData.append("bag", product.bag[0].name);


            axios.post( "/api/productinbag/",
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

var slider = new Vue({
    el: "#slider",
    data: function(){
        return {
            products: [],
            category: '',
            images: [],
            links: [],
            list: []
        }
    },

    methods: {
        loadProducts: function () {
            this.products = [];
            this.category = product.product.category.name;
            axios({
                method: "get",
                url: "/api/product/category/name?category=" + this.category
            })
                .then(response => {
                    this.products = response.data;
                    for(let i = 0; i < this.products.length; i++) {
                        this.list.push(this.products[i].id);
                        this.links.push("/product?product=" + this.products[i].id);
                    }

                    this.loadImages();
                }).catch(error => {
                console.log(error);
            });
        },

        loadImages: function(){
            axios({
                method: "get",
                url: "/api/productimage/slider?list=" + this.list
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
    },

    created: function () {
        setTimeout(function() {
            slider.loadProducts();
        }, 200);
    }
});

function nextSlide(n) {
    displaySlides(slide_index += n);
}

function currentSlide(n) {
    displaySlides(slide_index = n);
}

var slide_index = 1;
displaySlides(slide_index);


function displaySlides(n) {
    setTimeout( function () {
        var i;
        var slides = document.getElementsByClassName("showSlide");
        if (n > slides.length) { slide_index = 1 }
        if (n < 1) { slide_index = slides.length }
        for (i = 0; i < slides.length; i++) {
            slides[i].style.display = "none";
        }
        slides[slide_index - 1].style.display = "block";
    }, 300)
}

setTimeout(function() {
    let val = document.getElementsByClassName("price");
    for(let i = 0; i < val.length; i++){
        if(isInteger(val[i].textContent)){
            val[i].innerHTML = val[i].textContent.toString() + ",00 zł";
        }else{
            val[i].innerHTML = val[i].textContent.toString().trim().replace(/\./g,",") + " zł";
        }
    }
}, 320);

function showPopup() {
    let popup = document.getElementById("popup");
    popup.style.display = "unset";

    setTimeout(function () {
        popup.style.display = "none";
    }, 3000)
}