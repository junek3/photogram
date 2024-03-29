package com.rootlab.photogram.controller.api;

import com.rootlab.photogram.config.auth.PrincipalDetails;
import com.rootlab.photogram.domain.Image;
import com.rootlab.photogram.dto.CommonResponseDto;
import com.rootlab.photogram.service.ImageService;
import com.rootlab.photogram.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ImageApiController {

    private final ImageService imageService;
    private final LikesService likesService;

    @GetMapping("/api/image")
    public ResponseEntity<?> getFollowingUserImages(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PageableDefault(size = 3) Pageable pageable) {
        Page<Image> images = imageService.getFollowingUserImages(principalDetails.getUser().getId(), pageable);
        return new ResponseEntity<>(new CommonResponseDto<>(1, "구독한 유저들의 이미지 가져오기", images), HttpStatus.OK);
    }

    @PostMapping("/api/image/{imageId}/likes")
    public ResponseEntity<?> likes(
            @PathVariable Long imageId,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        likesService.likes(imageId, principalDetails.getUser().getId());
        return new ResponseEntity<>(new CommonResponseDto<>(1, "좋아요 성공", null), HttpStatus.CREATED);
    }

    @DeleteMapping("/api/image/{imageId}/likes")
    public ResponseEntity<?> dislikes(
            @PathVariable Long imageId,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        likesService.dislikes(imageId, principalDetails.getUser().getId());
        return new ResponseEntity<>(new CommonResponseDto<>(1, "좋아요 취소", null), HttpStatus.OK);
    }
}
