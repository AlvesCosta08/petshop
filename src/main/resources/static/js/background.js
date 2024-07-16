    let page = 0; // Inicializa a página
    const size = [[${produtos.size()}]]; // Usando Thymeleaf para passar o tamanho da página
    let loading = false;

    window.addEventListener('scroll', () => {
        if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight - 500 && !loading) {
            loading = true;
            document.getElementById('loading').style.display = 'block';
            fetch(`/produtos?page=${page}&size=${size}`)
                .then(response => response.text())
                .then(html => {
                    document.getElementById('produtos-container').insertAdjacentHTML('beforeend', html);
                    page++; // Incrementa a página
                    loading = false;
                    document.getElementById('loading').style.display = 'none';
                })
                .catch(error => {
                    console.error('Erro ao carregar produtos:', error);
                    loading = false;
                    document.getElementById('loading').style.display = 'none';
                });
        }
    });
