let departmentName = "Dom i ogród";
let typeApi = "/api/type/";
let categoryApi = "/api/category/";

var types = new Vue({
    el: "#types",
    data: function(){
        return {
            types: [],
            categories: [],
            images: [],
            departmentName: departmentName,
            depHref: "/products?department=" + departmentName,
            types_links : [],
            categories_links : []
        }
    },

    methods: {
        loadTypes: function () {
            this.types = [];
            axios({
                method: "get",
                url: typeApi + "department/name?department=" + departmentName
            })
                .then(response => {
                    this.types = response.data;
                    for(let i = 0; i < this.types.length; i++) {
                        this.images.push("http://localhost/dashboard/images/datahub/" + this.types[i].image);
                        this.types_links.push("/products?type=" + this.types[i].name);
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
        this.loadTypes(this.types);
        this.loadCategories(this.categories);
    }
});
