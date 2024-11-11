package DTO.courier.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCourierRequestDTO {
    private String login;
    private String password;
    private String firstName;
}