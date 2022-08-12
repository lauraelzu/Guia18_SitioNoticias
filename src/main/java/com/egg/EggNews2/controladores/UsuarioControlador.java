package com.egg.EggNews2.controladores;

import com.egg.EggNews2.excepciones.ErrorServicio;
import com.egg.EggNews2.servicios.UsuarioServicio;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/usuario")     //localhost:8080/usuario
public class UsuarioControlador {
    
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @GetMapping("/registrar")   //localhost:8080/usuario/registrar
    public String registrar(){
        return "usuario_formulario.html";
    }
    
    @PostMapping("/guardar")
                         //atributos con el mismo nombre que las variables de los input de usuario_formulario.html
    public String guardar(@RequestParam String idUsuario, @RequestParam String nombre, ModelMap modelo){ 

        try {
            usuarioServicio.crearUsuario(idUsuario, nombre);
            modelo.put("exito","usuario creado correctamente");
        } catch (ErrorServicio ex) {
            modelo.put("error",ex.getMessage());
            return "usuario_formulario.html";
        }
       
        return "index.html";
    }
}
