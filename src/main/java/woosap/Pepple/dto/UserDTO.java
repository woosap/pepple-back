package woosap.Pepple.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import woosap.Pepple.entity.User;
import woosap.Pepple.entity.type.Job;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {
    private String userId;

    private String nickname;

    private String imageUrl;

    private Job job;

    private String profile;

    private List<String> snsList;

}
