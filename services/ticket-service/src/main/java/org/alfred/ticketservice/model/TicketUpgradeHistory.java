package org.alfred.ticketservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "ticket_upgrade_history")
@Data
public class TicketUpgradeHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Tickets ticket;

    @ManyToOne
    @JoinColumn(name = "from_fare_matrix_id")
    private FareMatrix fromFareMatrix;

    @ManyToOne
    @JoinColumn(name = "to_fare_matrix_id")
    private FareMatrix toFareMatrix;

    @Column(name = "upgrade_amount")
    private double upgradeAmount;

    @Column(name = "upgraded_at")
    private LocalDateTime upgradedAt;
}
