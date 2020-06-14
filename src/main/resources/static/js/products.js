let typeApi = "http://localhost:8080/api/type/";
let categoryApi = "http://localhost:8080/api/category/";
let productApi = "http://localhost:8080/api/product/";

function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, '\\$&');
    var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, ' '));
}


var byType = getParameterByName('type');
var byCategory = getParameterByName('category');

var qParam = '';
var endPoint = '';

if(byType != null){
    qParam = byType.toString();
    endPoint = "type/name?type=";
}

if(byCategory != null){
    qParam = byCategory.toString();
    endPoint = "category/name?category=";
}

var products = new Vue({
    el: "#products",
    data: function(){
        return {
            products: [],
            images: []
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
                        this.images.push("./static/img/" + this.products[i].name.toLocaleLowerCase().trim().replace(/ /g,"-") + ".jpg");
                        //this.links.push("/dzial/" + this.types[i].name.toLocaleLowerCase().trim().replace(/ /g,"-"))
                    }
                }).catch(error => {
                console.log(error);
            });
        }
    },
    created: function () {
        this.loadProducts(this.products);
    }
});
