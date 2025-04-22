package com.sparta.moim.comment.application.service;

import static com.sparta.moim.comment.infrastructure.response.CommentCode.*;

import com.sparta.moim.comment.infrastructure.feignclient.RoleCheckClient;
import com.sparta.moim.comment.infrastructure.feignclient.enums.OrganizationMemberRole;
import com.sparta.moim.comment.infrastructure.response.CommentCode;
import com.sparta.moim.comment.presentation.request.CommentRequestDTO;
import com.sparta.moim.common.exception.BaseException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@RequiredArgsConstructor
public class UserCheckService {

  public void roleCheck(RoleCheckClient roleCheckClient, String organizationId, String userTrackingId, List<OrganizationMemberRole> roles){
    //권한을 체크 (feignClient)
    if(!roleCheckClient.checkRole(organizationId,userTrackingId,roles).getData()){
      throw new BaseException(COMMENT_NOT_VALID_AUTH);
    }
  }
}
