package com.example.api_updated;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TokenUserID {
    private String userID;
    private String userName;
    private String userEmail;
    private String userPhoneNumber;
    private String token;
    private boolean phoneVerified;
    private boolean userHasInternet;
}
