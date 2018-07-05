<template>
    <div class="container text-center text-lg-left">
        <div class="row justify-content-center">
        <div class="col-md-6">
        <div class="card">
        <header class="card-header">
            <h5 class="card-title mt-2">Start agent</h5>
        </header>
        <article class="card-body">
            <form id="form">

                <div class="form-group">
                    <label>Name:</label>
                    <input v-model="data.name" type="text" class="form-control" placeholder="">
                </div>

                <div class="form-group">
                    <label>Type: </label>
                    <div> 
                        <template>
                          <div>
                            <multiselect v-model="data.type" track-by="name" label="label" :options="typeListView"></multiselect>
                          </div>
                        </template>
                    </div>
                </div>
                <br>

                <div class="form-group">
                    <button @click="startAgent" type="button" class="btn btn-primary btn-block"> Start  </button>
                </div>

            </form>
        </article> 
        </div> 
        </div> 
        </div> 
    </div> 
</template>

<script>

    import Multiselect from 'vue-multiselect'
    import * as axios from 'axios';

    let BASE_URL = 'http://localhost:8080/AgentEnvWeb/api';
    
    export default {
        name : 'AgentForm',
        props : [ 'parentTypes' ], 
        components : { Multiselect },
        data () {
            return {
                data : {
                    name : '',
                    type : ''
                },
            } 
        },
        methods : {
            typeList() {
                let types = this.parentTypes;
                let tList = [];

                for (let i = 0, len = types.length; i < len; i++) {
                    let entries = Object.entries(types[i]);
                    for (let j = 0, lenJ = entries.length; j < lenJ; j++) {
                        let objList = entries[j];
                        let nodeName = objList[0];
                        let nodeTypes = objList[1];
                        for (let k = 0, lenK = nodeTypes.length; k < lenK; k++) {
                            tList.push(nodeName+':'+nodeTypes[k]);
                        }
                    }
                }
                return tList;
            },
            startAgent() {
                if (this.data.name === '' || this.data.type === null || this.data.type === '') {
                    alert('Required fields empty!');
                    return;
                }
                
                let url = BASE_URL + '/agents/running/' + this.data.type.name + '/' + this.data.name;

                axios.put(url)
                        .catch(function (error) {
                            console.log(error);
                        });
            }
        },
        computed :  {
            typeListView() {
                let list = this.typeList();
                let ret = list.map(x => {
                    let parts = x.split(':');
                    let labell = parts[1] + ':' + parts[2];
                    return {
                        name : x,
                        label : labell
                    };
                })
                return ret;
            }
        },
        created() {
            
        }
    }
    

</script>

<style src="vue-multiselect/dist/vue-multiselect.min.css"></style>
