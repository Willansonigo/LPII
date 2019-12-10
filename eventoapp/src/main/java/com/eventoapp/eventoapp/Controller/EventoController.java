package com.eventoapp.eventoapp.Controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.eventoapp.eventoapp.models.Convidado;
import com.eventoapp.eventoapp.models.Evento;
import com.eventoapp.repository.ConvidadoRepository;
import com.eventoapp.repository.EventoRepository;

@Controller//Uma anotação do controller.
public class EventoController {//Controller aqui controlo os botões da view sem codificar na view somente no Controller dela.
    
	@Autowired//Ela faz uma injeção de dependencias,toda vez que for usar essa interface,ela vai criar uma nova instância
	private EventoRepository er;
	
	@Autowired
	private ConvidadoRepository cr;
	
	@RequestMapping(value="/CadastrarEvento" , method=RequestMethod.GET)//"/cadastrarEvento")
	public String form() {//Um metodo que vai retornar um formulario.
	  
		return "evento/FormEvento";
	
	}

	@RequestMapping(value="/CadastrarEvento" , method=RequestMethod.POST)//Na hora que apertar o botão salvar os dados, ele vai mandar para essa requisição onde vai salvar os dados no banco de dados.
	public String form(Evento evento) {//Um metodo que vai retornar um formulario.
	  
		er.save(evento);//Com isso ele vai salvar esse evento no banco de dados.
		
		return "redirect:/CadastrarEvento";

	}
    
	@RequestMapping("/eventos")
	public ModelAndView ListaEventos() {//Mostrar a pagina com objeto junto
		
		ModelAndView mv = new ModelAndView("index");//Index é a pagina que será renderizada porque ela esta pronta para receber os dados.
	    Iterable<Evento> eventos = er.findAll();//Não axatamente na LIST,pois é o java 8.findAll =  select * from.Eele busca todas as listas de eventos.
	    mv.addObject("eventos", eventos);
	    return mv;
	}
	
	@RequestMapping(value="/{codigo}", method=RequestMethod.GET)//codigo do evento
	public ModelAndView detalhesEvento(@PathVariable("codigo")long codigo) {//codigo que agente vai receber e atravez desse codigo vai fazer uma busca no banco de dados.
		Evento evento = er.findBycodigo(codigo);//Ele vai buscar o evento que corresponde a esse codigo e vai guardar na variavel evento.
		ModelAndView mv = new ModelAndView("evento/detalhesEvento");//Vai criar um objeto da modelandview que vai renderizar a pagina detalhesEvento.
	mv.addObject("evento", evento);
	

	
	Iterable<Convidado> convidados = cr.findByEvento(evento);
	mv.addObject("convidados", convidados);
	
	return mv;
	
	}
	
	@RequestMapping(value="/{codigo}", method=RequestMethod.POST)//codigo do evento
	public String detalhesEventoPOST(@PathVariable("codigo")long codigo, Convidado convidado) {//codigo que agente vai receber e atravez desse codigo vai fazer uma busca no banco de dados.
	Evento evento = er.findBycodigo(codigo);
	convidado.setEvento(evento);//Passar no convidado o evento que foi encontrado
	cr.save(convidado);
		
	return "redirect:/{codigo}";
	
	}

	@RequestMapping("/deletar")
public String deletarEvento(long codigo) {
	Evento evento = er.findBycodigo(codigo);
	er.delete(evento);
	return "redirect:/eventos";
	
}





}
