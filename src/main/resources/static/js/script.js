// Adicione aqui seu código JavaScript para efeitos dinâmicos
document.addEventListener('DOMContentLoaded', function() {
    // Exemplo de efeito de foco
    const inputs = document.querySelectorAll('input, textarea');
    inputs.forEach(input => {
        input.addEventListener('focus', () => {
            input.style.borderColor = '#007bff';
        });
        input.addEventListener('blur', () => {
            input.style.borderColor = '#ccc';
        });
    });
});
