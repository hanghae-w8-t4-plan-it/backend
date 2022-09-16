package com.team4.planit.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    //MEMBER
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "M001", "해당 유저를 찾을 수 없습니다."),
    NOT_AUTHOR(HttpStatus.BAD_REQUEST.value(), "M002", "작성자가 아닙니다."),
    TOKEN_IS_EXPIRED(HttpStatus.UNAUTHORIZED.value(), "M003", "만료된 액세스 토큰 입니다."),
    REFRESH_TOKEN_IS_EXPIRED(HttpStatus.BAD_REQUEST.value(), "M004", "만료된 리프레시 토큰 입니다."),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST.value(), "M005", "유효하지 않은 토큰 입니다."),
    DUPLICATED_EMAIL(HttpStatus.BAD_REQUEST.value(), "M006", "이미 사용되고 있는 이메일입니다."),
    INVALID_MEMBER_INFO(HttpStatus.BAD_REQUEST.value(), "M007", "잘못된 사용자 정보입니다."),
    TOKEN_HIJACKED(HttpStatus.BAD_REQUEST.value(), "M008", "탈취된 토큰입니다."),

    //CATEGORY
    CATEGORY_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "C001", "해당 카테고리를 찾을 수 없습니다."),
    CATEGORY_END(HttpStatus.BAD_REQUEST.value(), "COO2", "해당 카테고리는 종료되었습니다."),

    //TODO_LIST
    TODO_LIST_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "T001", "해당 투두 리스트가 없습니다."),
    TODO_LIST_NOT_EXIST(HttpStatus.SERVICE_UNAVAILABLE.value(), "T002", "해당 날짜에 투두 리스트가 없습니다."),
    //TODO
    TODO_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "T001", "해당 투두가 없습니다."),

    //FILE
    FILE_TYPE_INVALID(HttpStatus.BAD_REQUEST.value(), "F001", "잘못된 파일 형식입니다."),
    FILE_SIZE_INVALID(HttpStatus.BAD_REQUEST.value(), "F002", "파일 크기가 너무 큽니다."),

    //FOLLOW
    FOLLOW_SELF_ERROR(HttpStatus.BAD_REQUEST.value(), "W001", "자기 자신을 팔로우 할 수 없습니다."),

    //DAY
    DATE_FORMAT_INVALID(HttpStatus.BAD_REQUEST.value(), "D001","날짜 형식이 맞지 않습니다."),
    PAST_DATE(HttpStatus.BAD_REQUEST.value(), "D002","지난 날짜를 변경할 수 없습니다."),
;

    private final int httpStatus;
    private final String code;
    private final String message;


}
