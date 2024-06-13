package vn.edu.hcmuaf.st.SmartphoneStore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OtpResponse {
    private int status;
    private String otp;
    private String message;
    public OtpResponse(int status, String message){
        this.status = status;
        this.message= message;
    }
}
