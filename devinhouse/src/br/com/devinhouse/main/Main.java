package br.com.devinhouse.main;

import java.sql.Date;

import br.com.devinhouse.dto.Atendimento;
import br.com.devinhouse.dto.Medico;
import br.com.devinhouse.dto.Paciente;
import br.com.devinhouse.service.AtendimentoService;
import br.com.devinhouse.service.MedicoService;
import br.com.devinhouse.service.PacienteService;

public class Main {
    public static void main(String[] args) throws Exception {

        MedicoService mService = new MedicoService();

        Medico medico = new Medico();
        
        medico = mService.retornaMedicoComMaisAtendimento();

        System.out.printf("O Medico com mais atendimento é o %d", medico.getCodigo());

        /* 
         * MedicoService mService = new MedicoService();
         * 
         * Medico novoMedico = new Medico(); novoMedico.setNome("Robson");
         * novoMedico.setCrm("765/2008");
         * 
         * System.out.println(mService.inserirMedico(novoMedico));
         */

       /*  PacienteService service = new PacienteService();

        Paciente novoPaciente = new Paciente();
        novoPaciente.setNome("Jorge");
        novoPaciente.setNascimento(Date.valueOf("1982-01-29"));
        novoPaciente.setEmail("jorge@gmail.com");
        novoPaciente.setTelefone("21988731223");

        System.out.println(service.inserirPacienteComAtendimento(novoPaciente, 3, "consulta", 500, Date.valueOf("2021-05-27")));
        */
        /* Paciente paciente = new Paciente();
        paciente.setCodigo(3);
        paciente.setNome("Jorge");
        paciente.setNascimento(Date.valueOf("1982-01-29"));
        paciente.setEmail("jorge@gmail.com");
        paciente.setTelefone("21988731223");

        System.out.println(service.deletarPaciente(paciente)); */

        /* AtendimentoService aService = new AtendimentoService();

        Atendimento novoAtendimento = new Atendimento();
        novoAtendimento.setCodigo(100);
        novoAtendimento.setPaciente(3);
        novoAtendimento.setDescricao("consulta");
        novoAtendimento.setMedico(2);
        novoAtendimento.setSituacao("Marcado");
        novoAtendimento.setValor(500);
        novoAtendimento.setData(Date.valueOf("2021-05-20"));

        System.out.println(aService.inserirAtendimento(novoAtendimento)); */
    }
}
