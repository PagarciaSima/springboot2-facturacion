package com.bolsadeideas.springboot.app.view.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.bolsadeideas.springboot.app.models.entity.Cliente;

@XmlRootElement(name = "clientes")
public class ClienteList {
	
	@XmlElement(name = "cliente")
	public List<Cliente> cliente;
	
	public ClienteList() {
	}	
	
	public ClienteList(List<Cliente> cliente) {
		this.cliente = cliente;
	}
	
	public List<Cliente> getCliente() {
		return cliente;
	}
	
}
