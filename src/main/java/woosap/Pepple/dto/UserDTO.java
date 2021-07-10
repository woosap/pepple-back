package woosap.Pepple.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import woosap.Pepple.entity.type.Job;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String userId;

    private String nickname;

    private String imageUrl;

    private Job job;

    private List<String> snsList;
}
