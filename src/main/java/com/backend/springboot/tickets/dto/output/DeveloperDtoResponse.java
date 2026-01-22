package com.backend.springboot.tickets.dto.output;

import com.backend.springboot.tickets.entity.Rol;
import com.backend.springboot.tickets.entity.Ticket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DeveloperDtoResponse {

    private Long id;
    private String name;

    private String email;

    private String username;

    private List<Rol> roles;
    private List<Ticket> tickets;
    private Boolean enabled;
    private Boolean admin;

    @Override
    public String toString() {
        return "DeveloperDtoOut{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", roles=" + roles +
                ", tickets=" + tickets +
                ", enabled=" + enabled +
                ", admin=" + admin +
                '}';
    }
}
