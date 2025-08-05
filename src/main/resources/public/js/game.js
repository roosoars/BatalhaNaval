import { initGame, placeShip, playerAttack, computerAttack } from './api.js';
import { createBoard, createShipCard }      from './ui.js';

let boardSize;
const shipsToPlace = [5, 4, 3, 3, 2];
let placedCount = 0;

const flipButton     = document.getElementById('flip-button');
const startButton    = document.getElementById('start-button');
const shipsContainer = document.getElementById('ships-container');

let vertical = false;

// Inverte orientação no card
flipButton.addEventListener('click', () => {
    vertical = !vertical;
    document.querySelectorAll('.ship').forEach(el => {
        el.classList.toggle('vertical', vertical);
        el.dataset.vertical = vertical;
    });
});

// Depois de todos posicionados, libera Start Game
startButton.addEventListener('click', () => {
    startButton.disabled = true;
});

async function setup() {
    const { size } = await initGame();
    boardSize = size;

    // Tabuleiro do jogador: arraste para posicionar
    createBoard(boardSize, 'player-board', { onDrop: handleDrop });
    // Tabuleiro do CPU: clique para atacar
    createBoard(boardSize, 'computer-board', { onClick: handleAttack });

    // Gera cards de navio
    shipsToPlace.forEach(len => {
        shipsContainer.appendChild(createShipCard(len, null, vertical));
    });
}

async function handleDrop(row, col, length, isVertical, boardElem) {
    try {
        await placeShip(row, col, length, isVertical);
        markShipOnBoard(row, col, length, isVertical, boardElem);

        // Remove preview usado
        const shipEl = shipsContainer.querySelector(`[data-length="${length}"]`);
        if (shipEl) shipEl.remove();

        placedCount++;
        if (placedCount === shipsToPlace.length) {
            startButton.disabled = false;
        }
    } catch {
        alert('Não foi possível posicionar o navio aí.');
    }
}

async function handleAttack(row, col) {
    try {
        // evita ataques repetidos
        const cell = document.querySelector(
            `#computer-board .cell[data-row="${row}"][data-col="${col}"]`
        );
        if (cell.classList.contains('hit') || cell.classList.contains('miss')) return;

        const res = await playerAttack(row, col);
        cell.classList.remove('ship');
        cell.classList.add(res.hit ? 'hit' : 'miss');

        if (!res.gameOver) {
            const compRes = await computerAttack();
            const pCell = document.querySelector(
                `#player-board .cell[data-row="${compRes.row}"][data-col="${compRes.col}"]`
            );
            pCell.classList.remove('ship');
            pCell.classList.add(compRes.hit ? 'hit' : 'miss');
        } else {
            alert('Você venceu!');
        }
    } catch (err) {
        alert(err.message);
    }
}

function markShipOnBoard(row, col, length, isVertical, boardElem) {
    for (let i = 0; i < length; i++) {
        const r = isVertical ? row + i : row;
        const c = isVertical ? col      : col + i;
        boardElem
            .querySelector(`.cell[data-row="${r}"][data-col="${c}"]`)
            .classList.add('ship');
    }
}

document.addEventListener('DOMContentLoaded', setup);
