package requests.courier;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCourierReq {
    private String login;
    private String password;
    private String firstName;

}