package med.voll.api.controller;

import med.voll.api.paciente.DadosCadastroPaciente;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pacientes")
public class PacienteController {

    @PostMapping
    public void Cadastrar(@RequestBody DadosCadastroPaciente dadosPacientes){
        System.out.println(dadosPacientes);
    }
}
