let departmentApi = "/api/department";
let typeApi = "/api/type";

var department = new Vue({
    el: "#department",
    data: function(){
        return {
            departments : [],
            department_name: '',
            file: ''
        }
    },

    methods: {
        loadDepartments: function () {
            axios({
                method: "get",
                url: departmentApi
            })
                .then( response => {
                    this.departments = response.data;
                    return response.data;
                }).
            catch( error => {
                console.log(error);
            });
        },

        delDepartment: function(department) {
            axios({
                method: "delete",
                url: departmentApi + "/" + department.id
            })
                .then( response => {
                    this.departments.splice(this.departments.indexOf(department), 1)
                }).
            catch( error => {
                console.log(error);
            });
        },

        saveDepartment: function() {
            let formData = new FormData();
            formData.append("name", this.department_name);
            formData.append('image', this.file);

            axios.post( departmentApi,
                formData,
                {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                }
            ).then(response => {
                this.departments.push(response.data);
                type.loadTypes();
            }).catch(error => {
                console.log(error);
            });
        },

        handleFileUpload() {
            this.file = this.$refs.file.files[0];
            console.log('>>>> 1st element in files array >>>> ', this.file);
        }
    },

    created: function() {
        this.loadDepartments(this.departments);
    }
});

var type = new Vue({
    el: "#type",
    data: function(){
        return {
            types : [],
            type_name: '',
            type_image: '',
            type_department: '',
            departments: []
        }
    },

    methods: {
        loadDepartments: function () {
            axios({
                method: "get",
                url: departmentApi
            })
                .then( response => {
                    this.departments = response.data;
                    return response.data;
                }).
            catch( error => {
                console.log(error);
            });
        },

        loadTypes: function () {
            this.loadDepartments();
            axios({
                method: "get",
                url: typeApi
            })
                .then( response => {
                    this.types = response.data;
                }).
            catch( error => {
                console.log(error);
            });
        },

        delTypes: function(type) {
            axios({
                method: "delete",
                url: typeApi + "/" + type.id
            })
                .then( response => {
                    this.types.splice(this.types.indexOf(type), 1)
                }).
            catch( error => {
                console.log(error);
            });
        },

        saveType: function() {
            let formData = new FormData();
                formData.append("name", this.type_name);
                formData.append("image", this.file);
                formData.append("department", this.type_department.name);

            axios.post( typeApi,
                formData,
                {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                }
            ).then(response => {
                this.types.push(response.data);
                this.loadTypes();
            }).catch(error => {
                console.log(error);
            });
        },

        handleFileUpload() {
            this.file = this.$refs.file.files[0];
            console.log('>>>> 1st element in files array >>>> ', this.file);
        }
    },

    created: function() {
        this.loadTypes(this.types);
    }
});