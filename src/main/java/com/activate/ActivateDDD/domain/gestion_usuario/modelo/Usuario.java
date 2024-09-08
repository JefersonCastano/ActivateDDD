package com.activate.ActivateDDD.domain.gestion_usuario.modelo;

import com.activate.ActivateDDD.domain.commons.Interes;
import com.activate.ActivateDDD.domain.commons.Ubicacion;
import lombok.Data;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class Usuario {
    private Long id;
    private String nombre;
    private int edad;
    private String email;
    private HashSet<Interes> intereses;
    private Ubicacion ubicacion;

    public Usuario(Long id, String nombre, int edad, String email, HashSet<Interes> intereses, Ubicacion ubicacion) throws Exception {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.email = email;
        if(intereses.size()<3) throw new Exception("Numero de intereses insuficiente");
        this.intereses = intereses;
        this.ubicacion = ubicacion;
    }

    public boolean editarPerfil(String nombre, int edad, String email) throws Exception {
        if(edad<0) throw new Exception("Edad negativa");
        if(edad<18) throw new Exception("Edad no valida (Debes ser mayor de edad)");
        if(!esEmailValido(email)) throw new Exception("Email no valido");
        this.nombre=nombre;
        this.edad=edad;
        this.email=email;
        return true;
    }

    private  boolean esEmailValido(String email){
        String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        if (email == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean agregarInteres(Interes interes){
        return intereses.add(interes);
    }

    public boolean actualizarUbicacion(Ubicacion ubicacion) throws Exception {
        if(ubicacion.getLatitud()==this.ubicacion.getLatitud() && ubicacion.getLongitud()==this.ubicacion.getLongitud())
            throw new Exception("Innecesario actualizar ubicacion");
        this.ubicacion=ubicacion;
        return true;
    }

}