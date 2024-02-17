package com.caeta.bookstore.estado;

import com.caeta.bookstore.pais.PaisRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@ResponseStatus(HttpStatus.CREATED)
@RequestMapping(value = "/estado")
public class EstadoController {

    @Autowired
    EstadoRepository estadoRepository;

    @Autowired
    PaisRepository paisRepository;

    @PostMapping
    @Transactional
    public String createPais(@RequestBody @Valid EstadoRequest request){
        Estado estado = request.toModel(paisRepository);
        estadoRepository.save(estado);

        return estado.toString();
    }
}
