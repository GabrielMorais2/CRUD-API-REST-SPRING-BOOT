package med.voll.api.domain.validacoes;

import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

//valida horario de funcionamento da cliente (seg a sab das 07:00 às 19:00 )
@Component
public class ValidaHorarioFuncionamentoClinicaParaMarcarConsultas implements ValidadorAgendamentoDeConsulta{

    public void validar(DadosAgendamentoConsulta dados){
        var dataConsulta = dados.data();

        var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var antesDaAberturaDaClinica = dataConsulta.getHour() < 7;
        var depoisDoEncerramentoDaclinica = dataConsulta.getHour() < 18;

        if(domingo || antesDaAberturaDaClinica || depoisDoEncerramentoDaclinica){
            throw new ValidacaoException("Consulta fora do horario de funcionamento da clinica");
        }
    }

}
