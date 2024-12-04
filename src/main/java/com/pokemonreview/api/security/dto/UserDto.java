package com.pokemonreview.api.security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserDto {
    private int id;
    private String username;
    private String firstName;
    private String lastName;

    // 역직렬화할 때에만 접근이 허용(WRITE_ONLY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String role;

    @Builder
    public UserDto(int id, String username, String firstName, String lastName, String role) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }
}