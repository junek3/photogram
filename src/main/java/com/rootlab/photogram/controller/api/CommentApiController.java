package com.rootlab.photogram.controller.api;

import com.rootlab.photogram.config.auth.PrincipalDetails;
import com.rootlab.photogram.domain.Comment;
import com.rootlab.photogram.dto.CommonResponseDto;
import com.rootlab.photogram.dto.comment.CommentDto;
import com.rootlab.photogram.handler.exception.CustomValidationApiException;
import com.rootlab.photogram.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/api/comment")
    public ResponseEntity<?> createComment(
            @Valid @RequestBody CommentDto commentDto,
            BindingResult bindingResult,
            @AuthenticationPrincipal PrincipalDetails principalDetails)
            throws CustomValidationApiException {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            throw new CustomValidationApiException("유효성 검사 실패", errorMap);
        }

        Comment comment = commentService.createComment(commentDto, principalDetails.getUser().getId());
        return new ResponseEntity<>(new CommonResponseDto<>(1, "댓글작성 성공", comment), HttpStatus.CREATED);
    }

    @DeleteMapping("/api/comment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(new CommonResponseDto<>(1, "댓글삭제 성공", null), HttpStatus.OK);
    }

}
