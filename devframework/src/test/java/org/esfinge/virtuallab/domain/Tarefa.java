package org.esfinge.virtuallab.domain;

import org.esfinge.virtuallab.annotations.Label;
import org.esfinge.virtuallab.annotations.ServiceClass;
import org.esfinge.virtuallab.annotations.ServiceMethod;

@ServiceClass
public class Tarefa {
	
	private String nome = "compromissos";

	@ServiceMethod
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}	
	
	@ServiceMethod
	public int getPrioridade(@Label("meu_label") String categoria, int idade) {
		return 1;
	}
}