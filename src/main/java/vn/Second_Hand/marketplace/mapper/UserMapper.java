package vn.Second_Hand.marketplace.mapper;

import org.mapstruct.Mapper;
import vn.Second_Hand.marketplace.dto.requests.RegisterRequest;
import vn.Second_Hand.marketplace.dto.responses.UserResponse;
import vn.Second_Hand.marketplace.entity.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User registerUser(RegisterRequest request);
    List<UserResponse> toUserResponse(List<User> users);
}

