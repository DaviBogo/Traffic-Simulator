# Traffic-Simulator
Davi Vinicius Bogo

Simulador de tráfego, onde carros passam por uma malha e devem respeitar o tempo de cruzamento de todos os 
carros presentes na malha.

Cada carro fica localizado em uma Thread única e a medida que sua passagem é liberada sua thread é liberada.

A malha conta com padrão de projetos Observer para ser atualizada para cada movimentação dos carros.
O movimento do carro é feito com padrão de projetos Strategy

-Funcionamento:
Basta rodar o projeto, selecionar a quantidade de carros que desejar que passem na malha, e clicar em 'Start',
assim os carros devem aparecer na malha gradativamente.

Quando desejar que parem de aparecer basta clicar em 'Stop', e os carros irão parar de aparecer gradativamente.

Pequena demonstração de uso.
![image](https://github.com/DaviBogo/Traffic-Simulator/assets/43791467/1bde7aaa-f735-4111-bbb1-ece9fcae0042)

