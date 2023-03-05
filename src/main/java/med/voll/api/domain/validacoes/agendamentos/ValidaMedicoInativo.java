package med.voll.api.domain.validacoes.agendamentos;

import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.medico.MedicoRepositoy;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidaMedicoInativo implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private MedicoRepositoy medicoRepositoy;

    public void validar(DadosAgendamentoConsulta dados){
        //escolha do medico opcional

        if(dados.idMedico() == null){
            return;
        }

        var medicoEstaAtivo = medicoRepositoy.findAtivoById(dados.idMedico());

        if(!medicoEstaAtivo){
            throw new ValidacaoException("Médico não está ativo");
        }
    }
}
