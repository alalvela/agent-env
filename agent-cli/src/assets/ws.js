port = 8080;    //window.port pristup


if (!("WebSocket" in window)) {
    alert('NEMA websoketa');
} else {
    // var host = "ws://localhost:8080/ChatAppWeb/wsep";
    var host = "ws://localhost:" + port + "/AgentEnvWeb/wsep"; 

    try {
        socket = new WebSocket(host);

        socket.onopen = function() {
            alert('OTVORIO SOKET')
        }

        socket.onmessage = function(msg) {
            window.vueinstance.testMethod(msg.data);
        }

        socket.onclose = function() {
            socket = null;
        }

    } catch (exception) {
    }
}