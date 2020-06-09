let departmentApi = "/api/department";
let typeApi = "/api/type/";
let categoryApi = "/api/category/";
let productApi = "/api/product";

let chosenDepartmentId;
let departmentsId = [];

var departments = new Vue({
    el: "#departments",
    data: function(){
        return {
            departments: [],
            images: [],
            links: []
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
                        this.images.push("./img/" + departmentsId[i].name.toLocaleLowerCase().trim().replace(/ /g,"-") + ".svg");
                        this.links.push("/dzial/" + departmentsId[i].name.toLocaleLowerCase().trim().replace(/ /g,"-"))
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
            categories: []
        }
    },

    methods: {
        loadTypes: function () {
            this.types = [];
            axios({
                method: "get",
                url: typeApi + "filter/department/id?id=" + chosenDepartmentId
            })
                .then(response => {
                    this.types = response.data;
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
                    this.categories = response.data;
                }).catch(error => {
                console.log(error);
            });
        }
    },
    created: function () {
        this.loadCategories(this.categories);
    }
});


document.addEventListener('DOMContentLoaded', function(){
    setTimeout(function() {
        let departments = document.getElementsByName("department");

        let type = document.getElementById("types");

        for (let i = 0; i < departments.length; i++) {
            departments[i].addEventListener('mouseover', function()  {
                chosenDepartmentId = departmentsId[i].id;
                types.loadTypes();
                types.loadCategories();
                type.style.display = "flex";
                departments[i].style.color = "#ff598a";
                departments[i].style.filter = "invert(51%) sepia(65%) saturate(2480%) hue-rotate(310deg) brightness(101%) contrast(101%)";
            }, false);
            departments[i].addEventListener('mouseout',  function() {
                departments[i].style.color = "black";
                departments[i].style.filter = "unset";
            }, false);
        }
    }, 100);
});