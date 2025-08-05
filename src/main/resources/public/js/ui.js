/**
 * Cria um tabuleiro no elemento containerId.
 * handlers: { onDrop?, onClick? }
 */
export function createBoard(size, containerId, handlers = {}) {
    const { onDrop, onClick } = handlers;
    const container = document.getElementById(containerId);
    container.style.setProperty('--board-size', size);
    container.innerHTML = '';

    // Se onDrop foi passado, habilita drag-and-drop
    if (onDrop) {
        container.addEventListener('dragover', e => e.preventDefault());
        container.addEventListener('drop', e => {
            const { length, vertical } = JSON.parse(e.dataTransfer.getData('text'));
            const target = e.target;
            if (!target.classList.contains('cell')) return;
            const row = +target.dataset.row;
            const col = +target.dataset.col;
            onDrop(row, col, length, vertical, container);
        });
    }

    // Cria as células
    for (let r = 0; r < size; r++) {
        for (let c = 0; c < size; c++) {
            const cell = document.createElement('div');
            cell.classList.add('cell');
            cell.dataset.row = r;
            cell.dataset.col = c;
            if (onClick) {
                cell.addEventListener('click', () => onClick(r, c, cell));
            }
            container.appendChild(cell);
        }
    }
}

/**
 * Gera o card de um navio de comprimento `length`, pronto para arrastar.
 * A orientação atual vem de `dataset.vertical`.
 */
export function createShipCard(length, id, vertical) {
    const shipEl = document.createElement('div');
    shipEl.classList.add('ship');
    if (vertical) shipEl.classList.add('vertical');
    shipEl.draggable = true;
    shipEl.dataset.length = length;
    shipEl.dataset.vertical = vertical;

    shipEl.addEventListener('dragstart', e => {
        const len = parseInt(shipEl.dataset.length, 10);
        const vert = shipEl.dataset.vertical === 'true';
        e.dataTransfer.setData('text', JSON.stringify({ length: len, vertical: vert }));
    });

    // Cada célula do preview
    for (let i = 0; i < length; i++) {
        const cell = document.createElement('div');
        cell.classList.add('cell');
        shipEl.appendChild(cell);
    }
    return shipEl;
}
