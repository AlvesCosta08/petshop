document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById('satisfactionForm');
    const ctx = document.getElementById('satisfactionChart').getContext('2d');
    let satisfactionChart;

    if (form) {
        form.addEventListener('submit', handleFormSubmit);
    }

    // Função para lidar com o envio do formulário
    function handleFormSubmit(event) {
        event.preventDefault(); // Previne o envio padrão do formulário

        // Coleta os dados do formulário
        const rating = document.querySelector('input[name="rating"]:checked');
        const email = document.getElementById('email').value;

        if (rating && email) {
            // Armazena os dados no localStorage
            saveFeedback(rating.value, email);

            // Exibe uma mensagem de confirmação
            alert('Obrigado pela sua avaliação!');

            // Atualiza o gráfico
            updateChart();

            // Limpa o formulário
            form.reset();
        } else {
            alert('Por favor, selecione uma avaliação e insira seu e-mail.');
        }
    }

    // Função para salvar feedback no localStorage
    function saveFeedback(rating, email) {
        const feedbacks = JSON.parse(localStorage.getItem('feedbacks')) || [];
        feedbacks.push({ rating, email });
        localStorage.setItem('feedbacks', JSON.stringify(feedbacks));
    }

    // Função para atualizar o gráfico
    function updateChart() {
        const feedbacks = JSON.parse(localStorage.getItem('feedbacks')) || [];

        const ratings = feedbacks.reduce((acc, feedback) => {
            acc[feedback.rating - 1]++;
            return acc;
        }, [0, 0, 0, 0, 0]);

        const data = {
            labels: ['1 Estrela', '2 Estrelas', '3 Estrelas', '4 Estrelas', '5 Estrelas'],
            datasets: [{
                label: 'Quantidade de Avaliações',
                data: ratings,
                backgroundColor: [
                    'rgba(255, 99, 132, 0.2)',
                    'rgba(54, 162, 235, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(75, 192, 192, 0.2)',
                    'rgba(153, 102, 255, 0.2)'
                ],
                borderColor: [
                    'rgba(255, 99, 132, 1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(75, 192, 192, 1)',
                    'rgba(153, 102, 255, 1)'
                ],
                borderWidth: 1
            }]
        };

        // Destrói o gráfico se já existir
        if (satisfactionChart) {
            satisfactionChart.destroy();
        }

        // Cria um novo gráfico
        satisfactionChart = new Chart(ctx, {
            type: 'bar',
            data: data,
            options: {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    }

    // Inicializa o gráfico ao carregar a página
    updateChart();
});







