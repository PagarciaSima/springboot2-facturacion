package com.bolsadeideas.springboot.app.view.xml;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.xml.MarshallingView;

import com.bolsadeideas.springboot.app.models.entity.Cliente;

@Component("listar.xml")
public class ClienteListXmlView extends MarshallingView{
	
	public ClienteListXmlView(Jaxb2Marshaller marshaller) {
		super(marshaller);
	}	
	
	@SuppressWarnings("unchecked")
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Page<Cliente> clientes = (Page<Cliente>) model.get("clientes");
		model.clear();
		model.put("clienteList", new ClienteList(clientes.getContent()));
		super.renderMergedOutputModel(model, request, response);
	}
	
}
