package com.capzz.demobackendworker.producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PagamentoSucessoProducer {
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void gerarResposta(String mensagem) {

        amqpTemplate.convertAndSend(
                "pagamento-response-sucesso-exchange",
                "pagamento-response-sucesso-rout-key",
                mensagem
        );
        System.out.println("Mensagem enviada para fila de sucesso");
    }
}
