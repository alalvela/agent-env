<template>
    <div class="container text-center text-lg-left">
        <div class="row justify-content-center">
        <div class="col-md-10">
        <div class="card">
        <header class="card-header">
            <!-- <a href="#" @click="$router.push(-1)" class="float-right btn btn-outline-primary mt-1">Back</a> -->
            <h5 class="card-title mt-2">New ACL message</h5>
        </header>
        <article class="card-body">
            <form id="form">

            <div class="form-group">
                <label>Performative: </label>
                <div> 
                    <template>
                      <div>
                        <multiselect v-model="data.performative" :options="performatives"></multiselect>
                      </div>
                    </template>
                </div>
            </div>

            <div class="form-group">
                <label>Sender: </label>
                <div> 
                    <template>
                      <div>
                        <multiselect v-model="data.sender" :searchable="true" track-by="localID" label="label"
                        :options="runningAgents"></multiselect>
                      </div>
                    </template>
                </div>
            </div>

            <div class="form-group">
                <label>Recievers: </label>
                <div> 
                    <template>
                      <div>
                        <multiselect v-model="data.recievers" :searchable="true" track-by="localID" label="label"
                        :options="runningAgents" :multiple="true"></multiselect>
                      </div>
                    </template>
                </div>
            </div> 

            <div class="form-group">
                <label>Content:</label>
                <input v-model="data.content" type="text" class="form-control" placeholder="">
            </div>
            <br>

            <div class="form-group">
                <button @click="sendMessage" type="button" class="btn btn-primary btn-block"> Send  </button>
            </div> 


            </form>
        </article> 
        </div> 
        </div> 
        </div> 
    </div> 
 <!-- kopiraj formu od accommodationa-a -->
</template>

<script>

    import Multiselect from 'vue-multiselect'
    import * as axios from 'axios';

    let BASE_URL = 'http://localhost:8080/AgentEnvWeb/api';

    export default {
        name: 'Message',
        components : { Multiselect },
        props : [ 'runningAgents' ],
        data () {
            return {
                data : {
                    performative : '',
                    sender : '',
                    recievers : [],
                    content : ''
                },
                performatives : [],
            }
        },
        methods : {
            sendMessage() {
                let data = this.proccessData();

                let url = BASE_URL + '/messages';

                axios.post(url, data)
                        .catch(function (error) {
                            console.log(error);
                        });
            },
            proccessData() {
                let sender = JSON.parse(JSON.stringify(this.data.sender));
                delete sender['localID'];
                delete sender['label'];

                let recievers = JSON.parse(JSON.stringify(this.data.recievers));
                recievers = recievers.map(rec => {
                    delete rec['localID'];
                    delete rec['label'];
                    return rec;
                });

                let data = JSON.parse(JSON.stringify(this.data));
                data['sender'] = sender;
                data['recievers'] = recievers;

                return data;
            }
        },
        created() {
            //get performatives
            let url = BASE_URL + '/messages';

            axios.get(url)
                    .then(x => {
                        this.performatives = this.performatives.concat(x.data);
                    });
        }
    }
    

</script>

<style>
    
</style>