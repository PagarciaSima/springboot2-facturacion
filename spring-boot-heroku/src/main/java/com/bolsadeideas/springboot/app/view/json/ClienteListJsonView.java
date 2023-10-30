package com.bolsadeideas.springboot.app.view.json;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.bolsadeideas.springboot.app.models.entity.Cliente;

@Component("listar.json")
public class ClienteListJsonView extends MappingJackson2JsonView{

	@SuppressWarnings("unchecked")
	@Override
	protected Object filterModel(Map<String, Object> model) {

		Page<Cliente> clientes = (Page <Cliente>) model.get("clientes");
		model.clear();
		model.put("clientes", clientes.getContent());
		return super.filterModel(model);
	}
	
}
