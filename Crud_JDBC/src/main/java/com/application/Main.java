package com.application;


import java.util.Date;

import com.dao.ContatoDAO;
import com.model.Contato;

public class Main {

	public static void main(String[] args) {
		
		ContatoDAO contatoDAO = new ContatoDAO();
		
		Contato contato = new Contato();
		
		contato.setId(1);
		contato.setNome("Joao Mendonsa");
		contato.setIdade(55);
		contato.setDataCadastro( new Date());
		
		Contato c1 = new Contato();
		c1.setId(1);
		c1.setNome("Maria Alejandra mendonsa");
		c1.setDataCadastro(new Date());
		c1.setIdade(34);
		
		contatoDAO.save(c1);
		contatoDAO.save(contato);
		
		contatoDAO.deleteById(14);
		
//		contatoDAO.getContatos();
		
		for(Contato con : contatoDAO.getContatos()) {
			System.out.println(con.getNome());
		}
	}
	
}
