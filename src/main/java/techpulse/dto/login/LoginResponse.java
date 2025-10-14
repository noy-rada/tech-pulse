package techpulse.dto.login;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class LoginResponse {

    private String email;
    private String token;
    private Date expiredDate;

}
