package com.egg.EggNews2.servicios;

import com.egg.EggNews2.entidades.Usuario;
import com.egg.EggNews2.excepciones.ErrorServicio;
import com.egg.EggNews2.repositorios.UsuarioRepositorio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServicio {
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    public void crearUsuario(String idUsuario, String nombre) throws ErrorServicio{
        
        validar(idUsuario, nombre);
        
        Usuario usuario = new Usuario();
        usuario.setId(idUsuario);
        usuario.setNombre(nombre);
        
        usuarioRepositorio.save(usuario);
    }
    
    public void modificarUsuario(String idUsuario, String nombre) throws ErrorServicio{
        
        validar(idUsuario, nombre);
        
        Optional<Usuario> respuesta = usuarioRepositorio.findById(idUsuario);
        
        if(respuesta.isPresent()){
            Usuario usuario = respuesta.get();
            usuario.setNombre(nombre);
            usuarioRepositorio.save(usuario);
        }      
    }
    
    public void validar(String idUsuario, String nombre) throws ErrorServicio{
        
        if(idUsuario == null || idUsuario.isEmpty()){
            throw new ErrorServicio("Debe indicar su DNI como identificador de usuario");
        }
        if(nombre == null || nombre.isEmpty()){
            throw new ErrorServicio("El nombre no puede ser nulo o vacío");
        }
        
    }
}