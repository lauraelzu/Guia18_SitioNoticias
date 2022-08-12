package com.egg.EggNews2.servicios;

import com.egg.EggNews2.entidades.Noticia;
import com.egg.EggNews2.entidades.Usuario;
import com.egg.EggNews2.excepciones.ErrorServicio;
import com.egg.EggNews2.repositorios.NoticiaRepositorio;
import com.egg.EggNews2.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoticiaServicio {
    
    @Autowired
    private NoticiaRepositorio noticiaRepositorio;
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    
    @Transactional
    public void crearNoticia(String titulo, String cuerpo, String idUsuario) throws ErrorServicio{
        
        validar(titulo, cuerpo);
        
        Optional<Usuario> respuesta =  usuarioRepositorio.findById(idUsuario);
        Usuario usuario = new Usuario();
        if(respuesta.isPresent()){
            usuario = respuesta.get();
        } else{
            throw new ErrorServicio("Usuario no registrado");
        }
        
        Noticia noticia = new Noticia();
        noticia.setTitulo(titulo);
        noticia.setCuerpo(cuerpo);
        noticia.setFechaPublicacion(new Date());
        noticia.setUsuario(usuario);
        
        noticiaRepositorio.save(noticia);
    }
    
    public List<Noticia> mostrarNoticiasOrdenadas(){
        List<Noticia> noticias = noticiaRepositorio.ordenarPorFecha();
        return noticias;
    }
    
    
    public Noticia mostrarNoticia(Long idNoticia){
        Optional<Noticia> respuesta = noticiaRepositorio.findById(idNoticia);
        Noticia noticia =new Noticia();
        if(respuesta.isPresent()){
            noticia = respuesta.get();
        }
        return noticia;
    }
    
    
    @Transactional
    public void modificarNoticia(Long idNoticia, String titulo, String cuerpo, String idUsuario) throws ErrorServicio{
        
        validar(titulo, cuerpo);
        if(idUsuario == null || idUsuario.isEmpty()){
            throw new ErrorServicio("Debe indicar un autor de la noticia");
        }
        
        Optional<Noticia> respuestaNoticia = noticiaRepositorio.findById(idNoticia);
        Noticia noticia = new Noticia();
        
        Optional<Usuario> respuestaUsuario = usuarioRepositorio.findById(idUsuario);
        
        if(respuestaUsuario.isPresent()){
            Usuario usuario = respuestaUsuario.get();
            noticia.setUsuario(usuario);
        }
        
        if(respuestaNoticia.isPresent()){
            noticia.setTitulo(titulo);
            noticia.setCuerpo(cuerpo);         
        } 
        
        noticiaRepositorio.save(noticia);  
    }
    
    
    @Transactional
    public void eliminarNoticia(Long idNoticia){
        
        Optional<Noticia> respuestaNoticia = noticiaRepositorio.findById(idNoticia);
        if(respuestaNoticia.isPresent()){
            noticiaRepositorio.deleteById(idNoticia);
        }
    }
    
    
    public void validar(String titulo, String cuerpo) throws ErrorServicio{
        
        if(titulo == null || titulo.isEmpty()){
            throw new ErrorServicio("El título no puede ser nulo o vacío");
        }
        if(cuerpo == null || cuerpo.isEmpty()){
            throw new ErrorServicio("El cuerpo de la noticia no puede ser nulo o vacío");
        }
        
    }
}
