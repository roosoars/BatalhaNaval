import { auth } from './firebase-imports.js';

const BASE_URL = '/api/game';
const DEFAULT_SIZE = 10;

async function apiFetch(path, options = {}) {
    const user = auth.currentUser;
    const headers = { 'Content-Type': 'application/json', ...(options.headers||{}) };
    if (user) {
        const token = await user.getIdToken();
        headers['Authorization'] = `Bearer ${token}`;
    }
    const res = await fetch(`${BASE_URL}/${path}`, { ...options, headers });
    if (!res.ok && res.status !== 204) throw new Error(await res.text());
    return res.status === 204 ? null : res.json();
}

export function initGame() {
    return apiFetch(`init?size=${DEFAULT_SIZE}`);
}

export function placeShip(row, col, length, vertical) {
    return apiFetch('place', {
        method: 'POST',
        body: JSON.stringify({ row, col, length, vertical })
    });
}

export function playerAttack(row, col) {
    return apiFetch('attack', {
        method: 'POST',
        body: JSON.stringify({ row, col })
    });
}

export function computerAttack() {
    return apiFetch('attack/computer');
}

/**
 * Grava histórico de partida no back-end.
 * Recebe {
 * result: "win"/"lose",
 * opponentType: "computer"/"user"
 * }
 */
export async function addHistory(result, opponentType) {
    const user = auth.currentUser;
    const token = user && await user.getIdToken();
    const res = await fetch('/api/history', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            ...(token ? { 'Authorization': `Bearer ${token}` } : {})
        },
        body: JSON.stringify({ result, opponentType })
    });
    if (res.status !== 204) throw new Error('Falha ao gravar histórico');
}
