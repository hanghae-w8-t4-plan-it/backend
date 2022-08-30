package com.team4.planit.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Supplier;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException implements Supplier<ErrorCode> {
   private final ErrorCode errorCode;

   @Override
   public ErrorCode get() {
      return errorCode;
   }
}
