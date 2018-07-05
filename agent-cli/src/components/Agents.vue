<template>
    <div class="row">
          <div class="col-3">
            <h3>Agent enviroment</h3><hr>
            <div class="list-group">

              <a @click="setActive(0)" href="#" id="start" class="list-group-item list-group-item-action ">Start new</a>
              <a @click="setActive(1)" href="#" id="running" class="list-group-item list-group-item-action ">Running</a>
              <a @click="setActive(2)" href="#" id="message" class="list-group-item list-group-item-action ">ACL message</a>

            </div>
          </div>
          <div class="col-6">
            <br>
            <br>
            <div class="jumbotron">  
              <AgentForm v-if="active === 0" v-bind:parentTypes="agentTypes" ></AgentForm>   
              <AgentList v-if="active === 1" v-bind:runningAgents="agentsRunning" ></AgentList>
              <ACLMessage v-if="active === 2" v-bind:runningAgents="agentsRunningACL"></ACLMessage>            
            </div>
          </div>
          <div class="col-3">
            <br>
            <br>
            <div class="card text-center">
              <div class="card-header">
                <h4>Log</h4>
              </div>
              <div class="card-body">
                <textarea class="form-control" rows="15" readonly> {{ log }}</textarea>
              </div>
              <!-- <div class="card-footer text-muted">
                2 days ago
              </div> -->
            </div>

          </div>
    </div>
   

</template>

<script>
    import AgentList from './AgentList';
    import ACLMessage from './ACLMessage';
    import AgentForm from './AgentForm';
    import * as axios from 'axios';

    let BASE_URL = 'http://localhost:8080/AgentEnvWeb/api';

    export default {
        name: 'Agents',
        components : { AgentForm, ACLMessage, AgentList },
        data () {
            return {
              agentTypes : [],
              agentsRunning : [], 
              agentsRunningACL : [], 
              active : null,       // 0 - start, 1 - running, 2 - message1
              recievedObject : {},
              log : ''
            }
        },
        methods: {
          setActive(option) {
            this.active = option;
          },
          init() {
            let url = BASE_URL + '/agents/classes';
            axios.get(url)
                    .then(x => {
                        this.agentTypes = this.agentTypes.concat(x.data);
                    });

            const url1 = BASE_URL + '/agents/running';
            axios.get(url1)
                    .then(x => {
                        this.agentsRunning = this.agentsRunning.concat(x.data);

                        let agentsRunningCpy = JSON.parse(JSON.stringify(this.agentsRunning));
                        let agentsACL = agentsRunningCpy.map((aid, idx) => { 
                          aid['localID'] = idx;
                          aid['label'] = aid.agentType.module + ':' + aid.agentType.name + ':' + 
                                            aid.name;
                          return aid;
                        });
                        this.agentsRunningACL = this.agentsRunningACL.concat(agentsACL);
                    });
          },
          updateRunning(recieved) {
            let ag = this.getNewObject(recieved);
            this.recievedObject = ag;

            this.agentsRunning = this.agentsRunning.concat([ag]);
            
            let agACL = this.getNewAgentACL(ag);
            this.agentsRunningACL = this.agentsRunningACL.concat([ agACL ]);

          },
          getNewObject(recieved) {
            let rec = JSON.parse(recieved);
            return JSON.parse(rec.object);
          },
          getNewAgentACL(agent) {
            agent['localID'] = this.agentsRunningACL.length;
            agent['label'] = agent.agentType.module + ':' + agent.agentType.name + ':' + agent.name;
            return agent;
          },
          updateTypes(recieved) {
            let type = this.getNewObject(recieved);
            this.recievedObject = type;

            this.agentTypes = this.agentTypes.concat([ type ]);
          },
          updateLog(recieved) {
            let rec = JSON.parse(recieved);
            let msg = rec.object;
            this.log += msg;
            this.log += '\n---------------------------------------------------';
          }
        },
        created() {
            this.$parent.$on('NEW_RUNNING', this.updateRunning);
            this.$parent.$on('NEW_TYPES', this.updateTypes);
            this.$parent.$on('NEW_MESSAGE', this.updateLog);


            this.init();
        }
    }
    

</script>
<style>
    
</style>