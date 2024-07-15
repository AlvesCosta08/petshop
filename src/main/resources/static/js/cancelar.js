 function limparFormulario() {
            document.getElementById('meuFormulario').reset(); // Limpa o formulário

            // Redefina o valor de um botão específico, se necessário
            // document.getElementById('meuBotao').value = '';
        }

        document.getElementById('cancelarBtn').addEventListener('click', function(event) {
            event.preventDefault(); // Previne o comportamento padrão do link
            limparFormulario(); // Chama a função para limpar o formulário
        });