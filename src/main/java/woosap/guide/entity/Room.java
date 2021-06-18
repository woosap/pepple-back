package woosap.guide.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class Room {
    private Long id;
    private String title;
    private String subheading;
    private int capacity;
    private LocalDateTime createdAt;

}
