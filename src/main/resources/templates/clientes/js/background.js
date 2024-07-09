document.addEventListener('DOMContentLoaded', function () {
    var images = [
        'css/images/dog01.jpeg',
        'css/images/dog02.jpeg',
        'css/images/dog03.jpg',
        'css/images/dog04.jpg',
        'css/images/dog05.jpg',
        'css/images/dog06.jpg',
        'css/images/dog07.jpg',
        'css/images/dog08.jpeg',
        'css/images/dog09.jpg'
    ];

    var intervalTime = 5000; // Intervalo de 5 segundos (5000 milissegundos)
    var bodyElement = document.body;

    function changeBackground() {
        var randomImage = images[Math.floor(Math.random() * images.length)];
        bodyElement.style.backgroundImage = 'url(' + randomImage + ')';
    }

    // Iniciar mudança de imagem automaticamente e repetir a cada intervalTime
    changeBackground(); // Primeira mudança imediata ao carregar
    setInterval(changeBackground, intervalTime);
});
