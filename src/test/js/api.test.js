// Teste unitário para a função addHistory do front-end usando Jest
// Este teste simula uma chamada bem-sucedida e uma chamada com erro
import { addHistory } from '../../src/main/resources/public/js/api.js';

// Mock do fetch global
beforeEach(() => {
    global.fetch = jest.fn();
});

afterEach(() => {
    jest.resetAllMocks();
});

describe('addHistory', () => {
    it('deve resolver quando o status for 204', async () => {
        fetch.mockResolvedValue({ status: 204 });
        await expect(addHistory('win', 'computer')).resolves.toBeUndefined();
    });

    it('deve lançar erro se o status não for 204', async () => {
        fetch.mockResolvedValue({ status: 400 });
        await expect(addHistory('lose', 'user')).rejects.toThrow('Falha ao gravar histórico');
    });
});

// Comentário: Este teste garante que a função addHistory lida corretamente com respostas de sucesso e erro do back-end.
