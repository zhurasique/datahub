let departmentApi = "/api/department/";
let typeApi = "/api/type/";
let categoryApi = "/api/category/";
let userApi = "/api/user/"

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
                url: typeApi + "department/id?id=" + chosenDepartmentId
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

var user = new Vue({
    el: "#user",
    data: function(){
        return {
            user : ''
        }
    },

    methods: {
        loadUser: function () {
            this.user = '';
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
                for (let j = 0; j < departments.length; j++) {
                    departments[j].style.color = "black";
                    departments[j].style.filter = "unset";
                }
                departments[i].style.color = "#ff598a";
                departments[i].style.filter = "invert(51%) sepia(65%) saturate(2480%) hue-rotate(310deg) brightness(101%) contrast(101%)";
            }, false);
        }
    }, 100);
});