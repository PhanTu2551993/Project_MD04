package ra.project_md04.model.entity;

import jakarta.persistence.Entity;
import lombok.*;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.UUID;

import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "serial_number", nullable = false, unique = true, length = 100)
    @UUID
    private String serialNumber;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "receive_name", length = 100)
    private String receiveName;

    @Column(name = "receive_address", length = 254)
    private String receiveAddress;

    @Column(name = "receive_phone", length = 15)
    private String receivePhone;

    @Column(name = "note", length = 100)
    private String note;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "received_at", nullable = false)
    private Date receivedAt;
}
