package com.egg.EggNews2.controladores;

import com.egg.EggNews2.entidades.Noticia;
import com.egg.EggNews2.excepciones.ErrorServicio;
import com.egg.EggNews2.servicios.NoticiaServicio;
import com.egg.EggNews2.servicios.UsuarioServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/noticia")
public class NoticiaControlador {
    
    @Autowired
    private NoticiaServicio noticiaServicio;
    
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @GetMapping("/registrar")      //localhost:8080/noticia/registrar
    public String registrar(){
        return "noticia_formulario.html";
    }
    
    @PostMapping("/guardar")      
    public String guardar(@RequestParam String titulo,@RequestParam String cuerpo,@RequestParam String dni, ModelMap modelo){
        try {
            noticiaServicio.crearNoticia(titulo, cuerpo, dni);
            modelo.put("exito","noticia publicada correctamente");
        } catch (ErrorServicio ex) {
            modelo.put("error",ex.getMessage());
            return "noticia_formulario.html";
        }
        return "index.html";
    }
    
    
    
    @GetMapping("/mostrar/{id}")
    public String mostrarNoticia(@PathVariable Long id, ModelMap modelo){
        Noticia noticia = noticiaServicio.mostrarNoticia(id);
        modelo.addAttribute("noticia", noticia);
        return "noticia_completa.html";
    }
    
    @GetMapping("/editar")
    public String editarNoticia(ModelMap modelo){
        List<Noticia> noticias = noticiaServicio.mostrarNoticiasOrdenadas();
        modelo.addAttribute("noticias",noticias);
        return "panelAdmin.html";
    }
    
    //muestra la noticia según su id
    @GetMapping("/modificar/{id}")
    public String modificarNoticia(@PathVariable Long id, ModelMap modelo){
        modelo.put("noticia", noticiaServicio.obtenerNoticia(id));
        return "noticia_modificar.html";
    }
    
    //edita la noticia específica (título y cuerpo)
    @PostMapping("/modificar/{id}")
    public String modificarNoticia(@PathVariable Long id, String titulo, String cuerpo,ModelMap modelo){
        try {
            noticiaServicio.modificarNoticia(id, titulo, cuerpo);
            return "index.html";
        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            return "noticia_modificar.html";
        }
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminarNoticia(@PathVariable Long id, ModelMap modelo){
        noticiaServicio.eliminarNoticia(id);
        return "index.html";
    }
    
}
