package com.backend.springboot.tickets.dto.input;

import com.backend.springboot.tickets.entity.Rol;
import com.backend.springboot.tickets.entity.Ticket;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DeveloperDtoRequest {

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    private String name;

    @NotBlank(message = "El email no puede estar vacío")
    @Size(min = 3, max = 50, message = "El email debe tener entre 3 y 50 caracteres")
    private String email;

    @NotBlank(message = "El username no puede estar vacío")
    @Size(min = 3, max = 50, message = "El username debe tener entre 3 y 50 caracteres")
    private String username;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 6, max = 100, message = "La contraseña debe tener entre 6 y 100 caracteres")
    private String password;
    private List<Rol> roles;
    private List<Ticket> tickets = new ArrayList<>();
    private Boolean enabled;
    private Boolean admin;


    @Override
    public String toString() {
        return "DeveloperDtoInput{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                ", tickets=" + tickets +
                ", enabled=" + enabled +
                ", admin=" + admin +
                '}';
    }
}
