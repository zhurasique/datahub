let departmentApi = "/api/department/";
let typeApi = "/api/type/";
let categoryApi = "/api/category/";

let chosenDepartmentId;
let departmentsId = [];

var departments = new Vue({
    el: "#departments",
    data: function(){
        return {
            departments: [],
            images: [],
            links: [],
            promoted: ''
        }
    },

    methods: {
        loadDepartments: function () {
            axios({
                method: "get",
                url: departmentApi
            })
                .then(response => {
                    this.departments = response.data;
                    departmentsId = response.data;
                    for(let i = 0; i < departmentsId.length; i++) {
                        this.images.push("http://localhost/dashboard/images/datahub/" + departmentsId[i].image);
                        this.links.push("/" + departmentsId[i].name.toLocaleLowerCase().trim().replace(/ /g, "-").latinize());
                    }
                }).catch(error => {
                console.log(error);
            });

        }
    },
    created: function () {
        this.loadDepartments(this.departments);
    }
});

var types = new Vue({
    el: "#types",
    data: function(){
        return {
            types: [],
            types_links: [],
            categories: [],
            categories_links: []
        }
    },

    methods: {
        loadTypes: function () {
            this.types = [];
            axios({
                method: "get",
                url: typeApi + "department/id?id=" + chosenDepartmentId
            })
                .then(response => {
                    this.types_links = [];
                    this.types = response.data;
                    for(let i = 0; i < this.types.length; i++) {
                        this.types_links.push("/products?type=" + this.types[i].name)
                    }
                }).catch(error => {
                console.log(error);
            });
        },
        loadCategories: function () {
            this.categories = [];
            axios({
                method: "get",
                url: categoryApi
            })
                .then(response => {
                    this.categories_links = [];
                    this.categories = response.data;
                    for(let i = 0; i < this.categories.length; i++) {
                        this.categories_links.push("/products?category=" + this.categories[i].name)
                    }
                }).catch(error => {
                console.log(error);
            });
        }
    },
    created: function () {
        this.loadCategories(this.categories);
    }
});


var slider = new Vue({
    el: "#slider",
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
                url: "/api/product/"
            })
                .then(response => {
                    this.products = response.data;
                    for(let i = 0; i < this.products.length; i++) {
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
    },

    created: function () {
        this.loadProducts();
    }
});

var promoted = new Vue({
    el: "#promoted",
    data: function(){
        return {
            promoted: '',
            image: '',
            link: ''
        }
    },

    methods: {
        loadProducts: function () {
            this.products = [];
            axios({
                method: "get",
                url: "/api/product/id?id=144"
            })
                .then(response => {
                    this.promoted = response.data;
                    this.link = "/product?product=" + this.promoted.id;

                    this.loadImages();
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
                    let arr = response.data;
                    for(let i = 0; i < arr.length; i++){
                        if(this.promoted.id === arr[i].product.id)
                            this.image = arr[i];
                    }

                    this.image.image = "http://localhost/dashboard/images/datahub/" + this.image.image;
                }).
            catch( error => {
                console.log(error);
            });
        },
    },

    created: function () {
        this.loadProducts();
    }
});

function isInteger(num) {
    return (num ^ 0) === num;
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

document.addEventListener('DOMContentLoaded', function(){
    setTimeout(function() {
        addCurrencies()
        let departments = document.getElementsByName("department");
        let type = document.getElementById("types");

        chosenDepartmentId = 2;
        types.loadTypes();
        types.loadCategories();
        type.style.display = "flex";
        departments[0].style.color = "#ff598a";
        departments[0].style.filter = "invert(51%) sepia(65%) saturate(2480%) hue-rotate(310deg) brightness(101%) contrast(101%)";

        for (let i = 0; i < departments.length; i++) {
            departments[i].addEventListener('mouseover', function()  {
                chosenDepartmentId = departmentsId[i].id;
                types.loadTypes();
                types.loadCategories();
                type.style.display = "flex";
                for (let j = 0; j < departments.length; j++) {
                    departments[j].style.color = "black";
                    departments[j].style.filter = "unset";
                }
                departments[i].style.color = "#ff598a";
                departments[i].style.filter = "invert(51%) sepia(65%) saturate(2480%) hue-rotate(310deg) brightness(101%) contrast(101%)";
            }, false);
        }
    }, 150);
});

