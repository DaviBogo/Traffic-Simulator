# Traffic-Simulator
Davi Vinicius Bogo

Simulador de tráfego, onde carros passam por uma malha e devem respeitar o tempo de cruzamento de todos os 
carros presentes na malha.

Cada carro fica localizado em uma Thread única e a medida que sua passagem é liberada sua thread é liberada.

A malha conta com padrão de projetos Observer para ser atualizada para cada movimentação dos carros.
O movimento do carro é feito com padrão de projetos Strategy
