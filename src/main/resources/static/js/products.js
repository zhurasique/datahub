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

function addCurrencies(){
    setTimeout(function() {
        let val = document.getElementsByClassName("price");
        for(let i = 0; i < val.length; i++){
            if(isInteger(val[i].textContent)){
                val[i].innerHTML = val[i].textContent.toString() + ",00 zł";
            }else{
                val[i].innerHTML = val[i].textContent.toString().trim().replace(/\./g,",") + " zł";
            }
        }
    }, 200);
}

function isInteger(num) {
    return (num ^ 0) === num;
}

var byType = getParameterByName('type');
var byCategory = getParameterByName('category');
var bySearch = getParameterByName('search')

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
            links: [],
            byType: byType,
            byCategory: byCategory,
            bySearch: bySearch,
            categories: [],
            sortType: 'sort',
            sortOptions: [
                { text: 'Najnowsze', value: 'newest' },
                { text: 'Cena: od najwyższej', value: 'expensive' },
                { text: 'Cena: od najniższej', value: 'cheap' }
            ],
            filteredProducts: [],
            filteredImages: [],
            filteredLinks: []
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

        loadCategories: function () {
            axios({
                method: "get",
                url: categoryApi + "type/name?type=" + byType
            })
                .then( response => {
                   this.categories = response.data;
                }).
            catch( error => {
                console.log(error);
            });
        },

        filterPrice: function (price) {
            this.loadProducts();
            this.loadImages();

            this.filteredLinks = [];
            this.filteredImages = [];
            this.filteredProducts = [];

            setTimeout(function() {

                for(let i = 0; i < products.products.length; i++){
                    if (products.products[i].price < price){
                        console.log("sliced");
                        products.filteredProducts.push(products.products[i]);
                        products.filteredImages.push(products.images[i]);
                        products.filteredLinks.push(products.links[i]);
                    }
                }

                products.products = [];
                products.images = [];
                products.links = [];

                for(let i = 0; i < products.filteredProducts.length; i++){
                    products.products.push(products.filteredProducts[i]);
                    products.images.push(products.filteredImages[i]);
                    products.links.push(products.filteredLinks[i]);
                }
                addCurrencies();
            }, 200);

        },

        sortBy: function (sortType) {
            if(sortType === "expensive"){
                for(let i = 0; i < this.products.length - 1; i++) {
                    for (let j = 0; j < this.products.length - i - 1; j++) {
                        if (this.products[j].price < this.products[j + 1].price) {
                            var tmp = [];
                            tmp.push(this.products[j]);
                            this.products[j] = this.products[j + 1];
                            this.products[j + 1] = tmp[0];
                        }
                    }
                }
            }else if(sortType === "cheap"){
                for(let i = 0; i < this.products.length - 1; i++) {
                    for (let j = 0; j < this.products.length - i - 1; j++) {
                        if (this.products[j].price > this.products[j + 1].price) {
                            var tmp = [];
                            tmp.push(this.products[j]);
                            this.products[j] = this.products[j + 1];
                            this.products[j + 1] = tmp[0];
                        }
                    }
                }
            }else if(sortType === "newest"){
                this.loadProducts();
            }
        }
    },
    created: function () {
        this.loadProducts(this.products);
        this.loadImages(this.images);

        if(byType !== undefined){
            this.loadCategories(this.categories);
        }
    }
});

document.addEventListener('DOMContentLoaded', function(){
    addCurrencies();

    document.getElementById("sort").addEventListener("change", function(e) {
        console.log(this.value);
        if (this.value === "newest") {
            addCurrencies();
        }
    });
});