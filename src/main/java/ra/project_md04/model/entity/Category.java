package ra.project_md04.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @Column(nullable = false,name = "category_name", length = 100)
    private String categoryName;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private Boolean status;

}
