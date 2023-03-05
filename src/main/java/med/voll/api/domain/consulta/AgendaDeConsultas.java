package med.voll.api.domain.consulta;

import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepositoy;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.domain.validacoes.agendamentos.ValidadorAgendamentoDeConsulta;
import med.voll.api.domain.validacoes.cancelamentos.ValidadorCancelamentoDeConsulta;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
Boas praticas utilizadas nessa classe:

Open-Closed Principle: pois na classe service, AgendadeConsultas, porque ela está fechada para modificação, não precisamos mexer nela.

Dependency Inversion Principle: porque nossa classe service depende de uma abstração, que é a interface, não depende dos validadores,
das implementações  especificamente.
 */
@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepositoy medicoRepositoy;

    @Autowired
    private PacienteRepository pacienteRepository;

    //Lista de validações de agendamentos para facilitar a chamada de função.
    @Autowired
    List<ValidadorAgendamentoDeConsulta> validadoresAgendamento;

    //Lista de validações de cancelamentos para facilitar a chamada de função.
    @Autowired
    List<ValidadorCancelamentoDeConsulta> validadoresCancelamento;

    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados){

        if(!pacienteRepository.existsById(dados.idPaciente())){
            throw new ValidacaoException("O Id do paciente informado não existe");
        }

        if(dados.idMedico() != null && !medicoRepositoy.existsById(dados.idMedico())) {
            throw new ValidacaoException("O Id do medico informado não existe");
        }

        validadoresAgendamento.forEach(v -> v.validar(dados));

        var medico = escolherMedico(dados);
        if(medico == null) {
            throw new ValidacaoException("Não existe médico disponível nessa data");
        }
        var paciente = pacienteRepository.findById(dados.idPaciente()).get();

        var consulta = new Consulta(null, medico, paciente, dados.data(), null);

        consultaRepository.save(consulta);

        return new DadosDetalhamentoConsulta(consulta);
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if(dados.idMedico() != null){
            return medicoRepositoy.getReferenceById(dados.idMedico());
        }

        if(dados.especialidade() == null){
            throw new ValidacaoException("Especialidade é obrigatoria quando o médico não for escolhido");
        }

        return medicoRepositoy.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
    }

    public void cancelar(DadosCancelamentoConsulta dados) {
        if (!consultaRepository.existsById(dados.idConsulta())) {
            throw new ValidacaoException("Id da consulta informado não existe!");
        }

        validadoresCancelamento.forEach(v -> v.validar(dados));

        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivo());
    }
}
