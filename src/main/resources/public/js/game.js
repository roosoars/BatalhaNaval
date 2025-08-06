import {initGame, placeShip, playerAttack, computerAttack, addHistory} from './api.js';
import {createBoard, createShipCard} from './ui.js';


export async function setup() {
    document.getElementById('ships-container').innerHTML = '';
    document.getElementById('start-button').disabled = true;

    const {size} = await initGame();
    const shipsToPlace = [5, 4, 3, 3, 2];
    let placedCount = 0;
    let vertical = false;

    document.getElementById('flip-button').onclick = () => {
        vertical = !vertical;
        document.querySelectorAll('.ship').forEach(el => {
            el.classList.toggle('vertical', vertical);
            el.dataset.vertical = vertical;
        });
    };

    const startBtn = document.getElementById('start-button');
    startBtn.onclick = () => startBtn.disabled = true;

    createBoard(size, 'player-board', {onDrop: handleDrop});
    createBoard(size, 'computer-board', {onClick: handleAttack});

    const shipsContainer = document.getElementById('ships-container');
    shipsToPlace.forEach(len => {
        const shipEl = createShipCard(len, null, vertical);
        shipsContainer.appendChild(shipEl);
    });

    async function handleDrop(row, col, length, isVertical, boardElem) {
        try {
            await placeShip(row, col, length, isVertical);
            markShipOnBoard(row, col, length, isVertical, boardElem);
            shipsContainer.querySelector(`[data-length="${length}"]`)?.remove();
            placedCount++;
            if (placedCount === shipsToPlace.length) startBtn.disabled = false;
        } catch {
            alert('Não foi possível posicionar o navio.');
        }
    }

    async function handleAttack(row, col) {
        const cell = document.querySelector(
            `#computer-board .cell[data-row="${row}"][data-col="${col}"]`
        );
        if (cell.classList.contains('hit') || cell.classList.contains('miss')) {
            return;
        }

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
            try {
                await addHistory(res.hit ? 'win' : 'lose', 'computer');
            } catch (e) {
                console.error('Erro ao salvar histórico:', e);
            }
            alert('Você ' + (res.hit ? 'venceu!' : 'perdeu!'));
        }
    }

    function markShipOnBoard(row, col, length, isVertical, boardElem) {
        for (let i = 0; i < length; i++) {
            const r = isVertical ? row + i : row;
            const c = isVertical ? col : col + i;
            boardElem
                .querySelector(`.cell[data-row="${r}"][data-col="${c}"]`)
                .classList.add('ship');
        }
    }
}
