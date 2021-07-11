package woosap.Pepple.dto;

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
public class UserDTO {
    private String userId;

    private String nickname;

    private String imageUrl;

    private Job job;

    private List<String> snsList;

    public User toEntity() {
        return new User(this.userId, this.nickname, this.imageUrl, this.job, this.snsList);
    }
}
