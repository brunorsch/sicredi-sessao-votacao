package io.github.brunorsch.sicredi.sessao.votacao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SicrediSessaoVotacaoApplication {
	public static void main(String[] args) {
		SpringApplication.run(SicrediSessaoVotacaoApplication.class, args);
	}
}