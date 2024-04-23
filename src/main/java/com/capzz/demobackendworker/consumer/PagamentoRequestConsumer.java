package com.capzz.demobackendworker.consumer;

import com.capzz.demobackendworker.dto.PagamentoMensagemDTO;
import com.capzz.demobackendworker.producer.PagamentoErroProducer;
import com.capzz.demobackendworker.producer.PagamentoSucessoProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class PagamentoRequestConsumer {

    @Autowired
    PagamentoErroProducer pagamentoErroProducer;
    @Autowired
    PagamentoSucessoProducer pagamentoSucessoProducer;

    private final ObjectMapper mapper = new ObjectMapper();

    @RabbitListener(queues = "pagamento-request-queue")
    public void recebeMensagem(@Payload Message message) throws JsonProcessingException {

        System.out.println("Mensagem consumida com sucesso: " + message);
        PagamentoMensagemDTO pagamentoMensagemDTO = mapper.readValue(message.getPayload().toString(), PagamentoMensagemDTO.class);
        if(pagamentoMensagemDTO.getValido()){
            pagamentoSucessoProducer.gerarResposta("Mensagem de sucesso de pagamento" + message);
        } else {
            pagamentoErroProducer.gerarResposta("Mensagem de erro de pagamento" + message);
        }

    }
}
