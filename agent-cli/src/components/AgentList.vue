<template>
    <div class="containter ">
        <div class="row">
            <div class="col-md-2"><h4>Name</h4></div>
            <div class="col-md-2"><h4>Module</h4></div>
            <div class="col-md-2"><h4>Type</h4></div>
            <div class="col-md-2"><h4>Host alias</h4></div>
        </div><hr>

        <template v-for="agent in runningAgents">
         <div class="row">
            <div class="col-md-2">{{ agent.name }}</div>
            <div class="col-md-2">{{ agent.agentType.module }}</div>
            <div class="col-md-2">{{ agent.agentType.name }}</div>
            <div class="col-md-2">{{ agent.agentCenter.alias }}</div>
            <div class="col-md-2"><a @click="stop(agent)" href="#">Stop</a></div>
        </div><hr>
    </template>
    </div>    

</template>

<script>
    import * as axios from 'axios';

    let BASE_URL = 'http://localhost:8080/AgentEnvWeb/api';

    export default {
        name: 'AgentList',
        props : [ 'runningAgents' ], 
        data() {
            return {
                running : []
            }
        },
        methods : {
            stop(agent) {
                let aid = this.format(agent);
                let url = BASE_URL + '/agents/running/' + aid; //+aid u onom retardiranom formatu 
                                                            // name!address,alias!name,module

                axios.delete(url)
                        .catch(function (error) {
                            console.log(error);
                        });
            },
            format(aid) {
                return aid.name + '!' + aid.agentCenter.address + ',' + aid.agentCenter.alias + '!' + 
                        aid.agentType.name + ',' + aid.agentType.module;
            }
        },
        mounted() {
            this.running = this.runningAgents;
        }
    }
</script>

<style>
    
</style>