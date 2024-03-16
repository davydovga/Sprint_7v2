package responses.courier;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateCourierResponse {
    private boolean ok;
    private String message;
}
