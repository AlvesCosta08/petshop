document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById('satisfactionForm');
    const ctx = document.getElementById('satisfactionChart').getContext('2d');
    let satisfactionChart;

    if (form) {
        form.addEventListener('submit', handleFormSubmit);
    }

    function handleFormSubmit(event) {
        event.preventDefault();

        const rating = document.querySelector('input[name="rating"]:checked');
        const email = document.getElementById('email').value;

        if (rating && email) {
            saveFeedback(rating.value, email);
            alert('Obrigado pela sua avaliação!');
            updateChart();
            form.reset();
        }
    }

    function saveFeedback(rating, email) {
        const feedbacks = JSON.parse(localStorage.getItem('feedbacks')) || [];
        feedbacks.push({ rating, email });
        localStorage.setItem('feedbacks', JSON.stringify(feedbacks));
    }

    function updateChart() {
        const feedbacks = JSON.parse(localStorage.getItem('feedbacks')) || [];

        const ratings = [0, 0, 0, 0, 0];
        feedbacks.forEach(feedback => {
            const ratingIndex = feedback.rating - 1;
            if (ratingIndex >= 0 && ratingIndex < 5) {
                ratings[ratingIndex]++;
            }
        });

        const data = {
            labels: ['1 Estrela', '2 Estrelas', '3 Estrelas', '4 Estrelas', '5 Estrelas'],
            datasets: [{
                label: 'Quantidade de Avaliações',
                data: ratings,
                backgroundColor: [
                    'rgba(255, 99, 132, 0.8)',
                    'rgba(54, 162, 235, 0.8)',
                    'rgba(255, 206, 86, 0.8)',
                    'rgba(75, 192, 192, 0.8)',
                    'rgba(153, 102, 255, 0.8)'
                ],
                borderColor: [
                    'rgba(255, 99, 132, 1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(75, 192, 192, 1)',
                    'rgba(153, 102, 255, 1)'
                ],
                borderWidth: 2,
                borderRadius: 5,
                borderSkipped: false,
                shadowOffsetX: 3,
                shadowOffsetY: 3,
                shadowBlur: 5,
                shadowColor: 'rgba(0, 0, 0, 0.3)',
            }]
        };

        const options = {
            scales: {
                y: {
                    beginAtZero: true,
                    grid: {
                        color: 'rgba(200, 200, 200, 0.2)',
                    },
                    ticks: {
                        color: '#333',
                    }
                },
                x: {
                    grid: {
                        color: 'rgba(200, 200, 200, 0.2)',
                    },
                    ticks: {
                        color: '#333',
                    }
                }
            },
            plugins: {
                legend: {
                    display: true,
                    labels: {
                        color: '#333',
                        font: {
                            size: 14
                        }
                    }
                }
            },
            animation: {
                duration: 1000,
                easing: 'easeInOutQuart'
            }
        };

        if (satisfactionChart) {
            satisfactionChart.destroy();
        }

        satisfactionChart = new Chart(ctx, {
            type: 'bar',
            data: data,
            options: options,
            plugins: [{
                beforeDraw: (chart) => {
                    const ctx = chart.ctx;
                    ctx.save();
                    ctx.shadowColor = 'rgba(0, 0, 0, 0.3)';
                    ctx.shadowBlur = 10;
                    ctx.shadowOffsetX = 3;
                    ctx.shadowOffsetY = 3;
                    ctx.restore();
                }
            }]
        });
    }

    function clearStorage() {
        localStorage.clear();
    }

    clearStorage();
    updateChart();
});



