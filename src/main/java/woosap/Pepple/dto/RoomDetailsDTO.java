package woosap.Pepple.dto;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomDetailsDTO implements Serializable {
    private RoomDTO roomInfo;
    private List<UserDTO> users;
}
