package com.eventoapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.eventoapp.eventoapp.models.Evento;

public interface EventoRepository extends CrudRepository<Evento , String> {//Essa classe facilita a nossa aplicação fazer o CRUDE.

	Evento findBycodigo(long codigo);//ele vai buscar apenas um codigo especifico.
}//Toda implementação seleção de dados no banco,insert,update.
//Quando anota algum metodo no eventorepository depois pode ultilizar no controller.