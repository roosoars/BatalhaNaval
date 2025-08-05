const BASE_URL = '/api';
const DEFAULT_SIZE = 10;

/**
 * Inicializa o jogo com tamanho padrão.
 * @returns {Promise<{ size: number }>}
 */
export async function initGame() {
    const res = await fetch(`${BASE_URL}/init?size=${DEFAULT_SIZE}`);
    if (!res.ok) throw new Error('Falha ao inicializar o jogo');
    return res.json();
}

/**
 * Posiciona um navio do jogador.
 * @param {number} row – linha de início
 * @param {number} col – coluna de início
 * @param {number} length – comprimento do navio
 * @param {boolean} vertical – orientação vertical?
 */
export async function placeShip(row, col, length, vertical) {
    const res = await fetch(`${BASE_URL}/place`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ row, col, length, vertical })
    });
    if (res.status !== 204) throw new Error('Não foi possível posicionar o navio');
}

/**
 * Realiza ataque do jogador ao computador.
 * @param {number} row
 * @param {number} col
 * @returns {Promise<{ hit: boolean, gameOver: boolean, sunk: string|null, row: number, col: number }>}
 */
export async function playerAttack(row, col) {
    const res = await fetch(`${BASE_URL}/attack`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ row, col })
    });
    if (!res.ok) throw new Error('Falha no ataque');
    return res.json();
}

/**
 * Realiza ataque do computador ao jogador.
 * @returns {Promise<{ hit: boolean, gameOver: boolean, sunk: string|null, row: number, col: number }>}
 */
export async function computerAttack() {
    const res = await fetch(`${BASE_URL}/attack/computer`);
    if (!res.ok) throw new Error('Falha no ataque do computador');
    return res.json();
}
