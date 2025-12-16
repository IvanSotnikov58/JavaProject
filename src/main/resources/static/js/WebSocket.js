
<script>
    const NGROK_URL = "https://yang-wersh-waveringly.ngrok-free.dev";
    const NGROK_WS = "wss://yang-wersh-waveringly.ngrok-free.dev";
let socket = null;

function connectWebSocket() {
    const matchId = document.getElementById('matchIdInput').value;
    if (!matchId) {
        console.log('Match Id не указан socket не подключается');
        return;
    }

   //zakrivaem staroe soedinenie
    if (socket && socket.readyState !== WebSocket.CLOSED) {
        socket.close();
    }

    //lokalka
    socket = new WebSocket(`${LOCAL_WS}/ws/match?matchId=${matchId}`);

    socket.onopen = () => {
        console.log('WebSocket podkljuchen', socket.url);
        appendLog('WebSocket Podkljuchen k serveru');


    };

    socket.onmessage = (event) => {
        try {
            const matchState = JSON.parse(event.data);
            console.log('Obnovlenie:', matchState);

            //obnovljaem status
            document.getElementById('mstatus').textContent = matchState.status || 'UNKNOWN';

            // log
            appendLog(`obnova mathca #${matchState.matchId}: status ${matchState.status}`);

            // pokazivaem hod
            if (matchState.turns && matchState.turns.length > 0) {
                const lastTurn = matchState.turns[matchState.turns.length - 1];
                appendLog(`posledniy hod: player ${lastTurn.player}, Karta ${lastTurn.cards?.name || lastTurn.cardId}`);
            }
        } catch (e) {
            console.error('oshibka parsinga:', e);
            console.log(event.data);
        }
    };
    //oshibka
    socket.onerror = (error) => {
        console.error('WebSocket Error', error);
        appendLog('oshibka podkljuchenia');
    };
    //otkljuchaem socket
    socket.onclose = (event) => {
        console.log('socket otkljuchen', event.code, event.reason);
        appendLog('Socket otkljuchen ot servera');

        // Avtoperepodkljuchenie
        if (event.code !== 1000) {
            setTimeout(() => {
                console.log('popitka podkljuchenia');
                connectWebSocket();
            }, 3000);
        }
    };
}

// funkcia dlja opravki dannih
function sendWebSocketMessage(data) {
    if (socket && socket.readyState === WebSocket.OPEN) {
        socket.send(JSON.stringify(data));
    } else {
        console.error('Socket ne podkljuchen');
    }
}

// Avtomatom podkljuchaem socket k matchu
const originalLoadMatch = document.getElementById('loadMatch').onclick;
document.getElementById('loadMatch').addEventListener('click', async function(e) {

    await originalLoadMatch?.call(this, e);


    connectWebSocket();
});

// Zakrivaem soedinenie pri pokidanii stranici
window.addEventListener('beforeunload', () => {
    if (socket) {
        socket.close(1000);
    }
});


function appendLog(message) {
    const logElement = document.getElementById('log') || console;
    if (logElement.innerHTML !== undefined) {
        logElement.innerHTML += `<div>${new Date().toLocaleTimeString()}: ${message}</div>`;
        logElement.scrollTop = logElement.scrollHeight;
    } else {
        console.log(message);
    }
}
</script>