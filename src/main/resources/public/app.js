
const API = '/api';
const width = 10;

const gamesBoardContainer = document.getElementById('gamesboard-container');
const optionContainer      = document.querySelector('.option-container');
const flipButton           = document.getElementById('flip-button');
const startButton          = document.getElementById('start-button');
const infoDisplay          = document.getElementById('info');
const turnDisplay          = document.getElementById('turn-display');

let angle = 0;
let draggedShip = null;
let playerShips = [];
let gameStarted = false;

// 1) Flip dos previews
flipButton.addEventListener('click', () => {
    angle = angle === 0 ? 90 : 0;
    Array.from(optionContainer.children)
        .forEach(el => el.style.transform = `rotate(${angle}deg)`);
});

// 2) Função que **cria** o board no DOM
function createBoard(color, user) {
    const board = document.createElement('div');
    board.classList.add('game-board');
    board.id = user;
    board.style.backgroundColor = color;
    for (let i = 0; i < width * width; i++) {
        const cell = document.createElement('div');
        cell.classList.add('block');
        cell.id = i;
        board.append(cell);
    }
    gamesBoardContainer.append(board);
}

// 3) Inicializa jogo no back-end e recria os boards
async function initGame() {
    await fetch(`${API}/init?size=${width}`);
    gamesBoardContainer.innerHTML = '';
    createBoard('yellow', 'player');
    createBoard('pink',   'computer');
    infoDisplay.textContent = 'Posicione seus navios e clique em START';
    turnDisplay.textContent = '';
    attachPlayerDragHandlers();
}
initGame();

// 4) Drag & Drop dos navios do jogador
function attachPlayerDragHandlers() {
    Array.from(optionContainer.children)
        .forEach(el => el.addEventListener('dragstart', e => draggedShip = e.target));
    document.querySelectorAll('#player .block').forEach(cell => {
        cell.addEventListener('dragover', e => e.preventDefault());
        cell.addEventListener('drop', dropShip);
    });
}

function dropShip(e) {
    const idx  = Number(e.target.id);
    const ship = ships[draggedShip.id];
    const row  = Math.floor(idx / width);
    const col  = idx % width;

    // Marca visualmente no front
    for (let i = 0; i < ship.length; i++) {
        const cellIndex = angle === 0 ? idx + i : idx + i * width;
        document.querySelector(`#player .block[id='${cellIndex}']`)
            .classList.add(ship.name, 'taken');
    }

    // Armazena para enviar depois
    playerShips.push({ row, col, length: ship.length, vertical: angle !== 0 });
    draggedShip.remove();
}

// 5) Definição simples dos barcos (só para previews)
class Ship { constructor(name, length){ this.name = name; this.length = length; } }
const ships = [
    new Ship('destroyer',  2),
    new Ship('submarine',  3),
    new Ship('cruiser',    3),
    new Ship('battleship', 4),
    new Ship('carrier',    5),
];

// 6) Ao clicar em START, envia todas as posições ao back-end
startButton.addEventListener('click', async () => {
    if (playerShips.length !== ships.length) {
        infoDisplay.textContent = 'Coloque todos os navios antes de iniciar!';
        return;
    }
    for (const ps of playerShips) {
        await fetch(`${API}/place`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(ps)
        });
    }
    gameStarted = true;
    infoDisplay.textContent = 'Jogo iniciado! Seu turno.';
    turnDisplay.textContent = 'Você';
    attachAttackHandlers();
});

// 7) Lida com ataque e contra-ataque
function attachAttackHandlers() {
    document.querySelectorAll('#computer .block')
        .forEach(cell => cell.addEventListener('click', handlePlayerAttack));
}

async function handlePlayerAttack(e) {
    if (!gameStarted) return;

    const idx = Number(e.target.id);
    const row = Math.floor(idx / width);
    const col = idx % width;

    // Jogador ataca
    let res  = await fetch(`${API}/attack`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ row, col })
    });
    let data = await res.json();
    e.target.classList.add(data.hit ? 'boom' : 'empty');
    infoDisplay.textContent = data.sunk
        ? `Você afundou um ${data.sunk}!`
        : (data.hit ? 'Você acertou!' : 'Você errou!');
    if (data.gameOver) return infoDisplay.textContent = 'Você venceu!';

    // Contra-ataque do computador
    turnDisplay.textContent = 'Computador';
    res  = await fetch(`${API}/attack/computer`);
    data = await res.json();
    const compIdx    = data.row * width + data.col;
    const playerCell = document.querySelector(`#player .block[id='${compIdx}']`);
    playerCell.classList.add(data.hit ? 'boom' : 'empty');
    infoDisplay.textContent = data.sunk
        ? `Computador afundou seu ${data.sunk}!`
        : (data.hit ? 'Computador acertou!' : 'Computador errou!');
    if (data.gameOver) return infoDisplay.textContent = 'Computador venceu!';

    turnDisplay.textContent = 'Você';
}