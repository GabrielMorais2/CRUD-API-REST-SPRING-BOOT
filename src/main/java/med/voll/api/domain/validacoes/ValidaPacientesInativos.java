package med.voll.api.domain.validacoes;

import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidaPacientesInativos implements ValidadorAgendamentoDeConsulta{

    @Autowired
    private PacienteRepository pacienteRepository;

    public void validar(DadosAgendamentoConsulta dados){
        var paciente = pacienteRepository.findById(dados.idPaciente()).get();

        if(!paciente.isAtivo()){
            throw new ValidacaoException("Paciente não está ativo");
        }
    }
}
