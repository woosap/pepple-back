package woosap.Pepple.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;
import woosap.Pepple.entity.type.Category;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoomWithUserImageDTO implements Serializable {
    private long roomId;

    private String title;

    private String subTitle;

    private LocalDateTime date;

    private List<Category> category;

    private int capacity;

    private List<String> imageUrl;
}
