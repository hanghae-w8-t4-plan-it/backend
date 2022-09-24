package com.team4.planit.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class MemberRequestDto {
    @Email(message = "유효하지 않은 이메일 형식입니다.",
            regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
    private String email;

    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z-0-9]{2,9}$", message = "닉네임은 특수문자를 제외한 2~9자리여야 합니다.")
    private String nickname;

    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{10,20}$|^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{10,20}$|^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9!@#$%^&*]{10,20}$",
            message = "10~20 자리 이내 영문, 숫자, 특수문자 중 2종류 이상 조합")
    private String password;

    @Builder
    public MemberRequestDto(String email, String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

}
