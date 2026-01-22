package com.backend.springboot.tickets.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String ticketNumber;
    private String title;
    private String description;
    private Status status;
    private String priority;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer estimatedHours;
    @ManyToMany
    @JsonIgnoreProperties({"tickets", "handler", "hibernateLazyInitializer"})
    @JoinTable(
            name = "tickets_developers",
            joinColumns = @JoinColumn(name = "ticket_id"),
            inverseJoinColumns = @JoinColumn(name = "developer_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"ticket_id", "developer_id"})
    )
    private List<Developer> developers = new ArrayList<>();
    private String area;

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", ticketNumber='" + ticketNumber + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", priority='" + priority + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", estimatedHours=" + estimatedHours +
                ", developers=" + developers +
                ", area='" + area + '\'' +
                '}';
    }
}
