 (function(){
      const nav = document.getElementById('navUser');
      const id = localStorage.getItem('hc_userId');
      const name = localStorage.getItem('hc_userName');
      if(id && name){
        nav.innerHTML = `Вы: ${escapeHtml(name)} (id:${escapeHtml(id)}) &nbsp; <button id="logoutSmall" style="margin-left:8px;padding:4px 8px;border-radius:6px;border:none;background:#333;color:#fff;cursor:pointer">Выйти</button>`;
        document.getElementById('logoutSmall').addEventListener('click', ()=>{ localStorage.removeItem('hc_userId'); localStorage.removeItem('hc_userName'); location.reload(); });
        document.getElementById('p1').value = id;
      } else {
        nav.textContent = 'Не вошёл';
      }
    })();

    function escapeHtml(s){ if(!s && s!==0) return ''; return String(s).replace(/[&<>"']/g,c=>({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c])); }

    const p2typeRadios = document.getElementsByName('p2type');
    const p2id = document.getElementById('p2id');
    const p2name = document.getElementById('p2name');

    p2typeRadios.forEach(radio => {
      radio.addEventListener('change', ()=>{
        if(radio.value === 'id' && radio.checked){
          p2id.style.display = '';
          p2name.style.display = 'none';
        } else if(radio.value === 'name' && radio.checked){
          p2id.style.display = 'none';
          p2name.style.display = '';
        }
      });
    });

    document.getElementById('createMatch').addEventListener('click', async ()=>{
      const p1 = document.getElementById('p1').value;
      let p2;
      if([...p2typeRadios].find(r=>r.checked).value === 'id'){
        p2 = p2id.value.trim();
        if(!p2) return alert('Введите ID второго игрока');
      } else {
        const name = p2name.value.trim();
        if(!name) return alert('Введите ник второго игрока');
        try{
          const r = await fetch(`/users?name=${encodeURIComponent(name)}`);
          if(!r.ok) throw new Error('HTTP ' + r.status);
          const users = await r.json();
          if(!Array.isArray(users) || users.length===0) return alert('Игрок не найден');
          p2 = users[0].id;
        }catch(e){ return alert('Ошибка поиска игрока: ' + e); }
      }

      try{
        const r = await fetch(`/matches?player01Id=${p1}&player02Id=${p2}`, { method: 'POST' });
        if(!r.ok) throw new Error('HTTP ' + r.status);
        const match = await r.json();
        document.getElementById('created').textContent = 'Match id:' + match.id;
        document.getElementById('matchIdInput').value = match.id;
      }catch(e){
        alert('Ошибка создания матча: ' + e);
      }
    });

    document.getElementById('loadMatch').addEventListener('click', async ()=>{
      const id = document.getElementById('matchIdInput').value;
      if(!id) return alert('Введите match id');
      try{
        const r = await fetch(`/matches/${id}`);
        if(!r.ok) throw new Error('HTTP ' + r.status);
        const match = await r.json();
        const real = (match && match.id) ? match : (Array.isArray(match) ? match[0] : match);
        document.getElementById('mid').textContent = real.id || '';
        document.getElementById('mstatus').textContent = real.status || '';
        document.getElementById('matchArea').style.display = 'block';
        const stored = localStorage.getItem('hc_userId'); if(stored) document.getElementById('playerId').value = stored;
        appendLog('Матч загружен: ' + JSON.stringify(real));
      }catch(e){
        alert('Ошибка загрузки матча: ' + e);
      }
    });

    document.getElementById('playBtn').addEventListener('click', async ()=>{
      const matchId = document.getElementById('mid').textContent || document.getElementById('matchIdInput').value;
      const playerId = document.getElementById('playerId').value;
      const cardId = document.getElementById('cardId').value || document.getElementById('cardId')?.value;
      const target = document.getElementById('target').value || '';
      const result = document.getElementById('result').value || '';
      const turnNumber = document.getElementById('turnNumber').value || 1;

      if(!matchId || !playerId || !cardId) return alert('matchId, playerId и cardId обязательны');

      try{
        const url = `/matches/${matchId}/play?playerId=${playerId}&cardId=${cardId}&target=${encodeURIComponent(target)}&result=${encodeURIComponent(result)}&turnNumber=${turnNumber}`;
        const r = await fetch(url, { method:'POST' });
        if(!r.ok) throw new Error('HTTP ' + r.status);
        appendLog(`Ход отправлен: player=${playerId} card=${cardId} turn=${turnNumber}`);
        document.getElementById('turnNumber').value = Number(turnNumber) + 1;
        const mr = await fetch(`/matches/${matchId}`);
        const match = await mr.json();
        const real = (match && match.status) ? match : (Array.isArray(match) ? match[0] : match);
        document.getElementById('mstatus').textContent = real.status || 'UNKNOWN';
      }catch(e){
        appendLog('Ошибка при отправке хода: ' + e);
      }
    });

    function appendLog(s){
      const log = document.getElementById('log');
      const line = document.createElement('div');
      line.textContent = (new Date()).toLocaleTimeString() + ' - ' + s;
      log.prepend(line);
    }

</script>