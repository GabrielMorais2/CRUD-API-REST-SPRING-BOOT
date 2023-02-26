package med.voll.api.domain.validacoes;

import med.voll.api.domain.consulta.DadosAgendamentoConsulta;


/*
Nesse momento estou utilizando o uma das boas praticas do SOLID chamada Single Responsibility Principle
porque cada classe de validação tem apenas uma responsabilidade.
 */

public interface ValidadorAgendamentoDeConsulta {

    void validar(DadosAgendamentoConsulta dados);
}
