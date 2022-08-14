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
    
    public void crearUsuario(String dni, String nombre) throws ErrorServicio{
        
        validar(dni, nombre);
        
        Usuario usuario = new Usuario();
        usuario.setDni(dni);
        usuario.setNombre(nombre);
        
        usuarioRepositorio.save(usuario);
    }
    
    public void modificarUsuario(String dni, String nombre) throws ErrorServicio{
        
        validar(dni, nombre);
        
        Optional<Usuario> respuesta = usuarioRepositorio.findById(dni);
        
        if(respuesta.isPresent()){
            Usuario usuario = respuesta.get();
            usuario.setNombre(nombre);
            usuarioRepositorio.save(usuario);
        }      
    }
    
    public void validar(String dni, String nombre) throws ErrorServicio{
        
        if(dni == null || dni.isEmpty()){
            throw new ErrorServicio("Debe indicar su DNI como identificador de usuario");
        }
        if(nombre == null || nombre.isEmpty()){
            throw new ErrorServicio("El nombre no puede ser nulo o vacío");
        }
        
    }
}
