package DTO.courier.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthCourierRequestDTO {
    private String login;
    private String password;
}