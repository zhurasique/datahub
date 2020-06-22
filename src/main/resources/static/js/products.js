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
            sortType: 'oldest',
            sortOptions: [
                { text: 'Najnowsze', value: 'newest' },
                { text: 'Najstarsze', value: 'oldest' },
                { text: 'Cena: od najwyższej', value: 'expensive' },
                { text: 'Cena: od najniższej', value: 'cheap' }
            ],
            filteredProducts: [],
            filteredImages: [],
            filteredLinks: [],
            pages: 0,
            page: 0
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

                    this.sortBy(this.sortType);
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
            this.showPage();

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
            this.sortType = sortType;
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
            }else if(sortType === "oldest"){
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

                this.loadImages();
            }else if(sortType === "newest"){
                this.products.reverse();
            }
        },

        splitToPages: function () {
            if(isInteger(this.products.length/10)){
                this.pages = Math.trunc(this.products.length/10);
            }else{
                this.pages = Math.trunc(this.products.length/10) + 1;
            }
        },

        showPage: function(){
            this.loadProducts();
            this.loadImages();

            setTimeout(function() {
                products.splitToPages();

                let tmp = [];
                let count = 0;

                if((products.products.length - products.page * 10) > 10){
                    count = products.page * 10 + 10;
                }else{
                    count = products.products.length;
                }

                for (let i = products.page * 10; i < count; i++) {
                    tmp.push(products.products[i]);
                }

                products.products = [];

                for (let i = 0; i < tmp.length; i++) {
                    products.products.push(tmp[i]);
                }
            }, 150);
        },

        previousPage: function () {
            if(this.page !== 0){
                this.page--;
                this.showPage();
                addCurrencies();
            }
        },

        nextPage: function () {
            if(this.page + 1 !== this.pages) {
                console.log(this.page + " " + this.pages);
                this.page++;
                this.showPage();
                addCurrencies();
            }
        }
    },
    created: function () {
        this.showPage();

        if(byType !== undefined){
            this.loadCategories(this.categories);
        }
    }
});

document.addEventListener('DOMContentLoaded', function(){
    addCurrencies();

    document.getElementById("sort").addEventListener("change", function(e) {
        console.log(this.value);
        if (this.value === "oldest") {
            addCurrencies();
        }
    });
});