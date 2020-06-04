let departmentApi = "/api/department";
let typeApi = "/api/type";
let productApi = "/api/product";

var vue = new Vue({
    el: "#app",
    data: function(){
        return {
            departments: []
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
                }).catch(error => {
                console.log(error);
            });

        },
    },
        created: function () {
            this.loadDepartments(this.departments);
        }
});

