package com.egg.EggNews2.controladores;

import com.egg.EggNews2.entidades.Noticia;
import com.egg.EggNews2.excepciones.ErrorServicio;
import com.egg.EggNews2.servicios.NoticiaServicio;
import com.egg.EggNews2.servicios.UsuarioServicio;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
    public String guardar(@RequestParam String titulo,@RequestParam String cuerpo,@RequestParam MultipartFile imagen, @RequestParam String dni, ModelMap modelo){
        try {
            noticiaServicio.crearNoticia(titulo, cuerpo, imagen, dni);
            modelo.put("exito","noticia publicada correctamente");
        } catch (ErrorServicio ex) {
            modelo.put("error",ex.getMessage());
            return "noticia_formulario.html";
        }catch (IOException e){   //error que lanza imagen
            modelo.put("error",e.getMessage());
            return "noticia_formulario.html";
        }
        return "redirect:../";
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
    public String modificarNoticia(@PathVariable Long id, String titulo, String cuerpo, MultipartFile imagen, ModelMap modelo){
        try {
            noticiaServicio.modificarNoticia(id, titulo, cuerpo, imagen);
        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            return "noticia_modificar.html";
        } catch (IOException e){   //error que lanza imagen
            modelo.put("error",e.getMessage());
            return "noticia_formulario.html";
        }
        
        return "redirect:../../";
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminarNoticia(@PathVariable Long id, ModelMap modelo){
        noticiaServicio.eliminarNoticia(id);
        return "redirect:../../";
    }
    
}
