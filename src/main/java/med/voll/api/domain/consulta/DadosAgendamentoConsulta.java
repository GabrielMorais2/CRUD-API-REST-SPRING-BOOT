package med.voll.api.domain.consulta;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.medico.Especialidade;

import java.time.LocalDateTime;

public record DadosAgendamentoConsulta(
        @JsonAlias("medico_id")
        Long idMedico,

        @NotNull
        @JsonAlias("produto_id")
        Long idPaciente,

        Especialidade especialidade,

        @NotNull
        @Future
        @JsonAlias("data_da_compra")
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime data) {
}
