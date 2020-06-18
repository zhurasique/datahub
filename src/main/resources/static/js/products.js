let typeApi = "/api/type/";
let categoryApi = "/api/category/";
let productApi = "/api/product/";
let productImagesApi = "/api/productimage/";

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

var byType = getParameterByName('type');
var byCategory = getParameterByName('category');

var qParam = '';
var endPoint = '';

if(byType != null){
    qParam = byType.toString();
    endPoint = "type/name?type=";
}

if(byCategory != null) {
    qParam = byCategory.toString();
    endPoint = "category/name?category=";
}

var products = new Vue({
    el: "#products",
    data: function(){
        return {
            products: [],
            images: [],
            links: []
        }
    },

    methods: {
        loadProducts: function () {
            this.products = [];
            axios({
                method: "get",
                url: productApi + endPoint + qParam
            })
                .then(response => {
                    this.products = response.data;
                    for(let i = 0; i < this.products.length; i++) {
                        if(isInteger(this.products[i]["price"])){
                            this.products[i]["price"] = this.products[i]["price"].toString() + ",00 zł";
                        }else{
                            this.products[i]["price"] = this.products[i]["price"].toString().trim().replace(/\./g,",") + " zł";
                        }
                        this.links.push("/product?product=" + this.products[i].id);
                    }
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
    },
    created: function () {
        this.loadProducts(this.products);
        this.loadImages(this.images);
    }
});
