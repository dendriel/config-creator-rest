package com.rozsa.controller;

import com.rozsa.business.UserBusiness;
import com.rozsa.controller.dto.UserDto;
import com.rozsa.controller.mapper.UserMapper;
import com.rozsa.model.User;
import com.rozsa.security.UserContext;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserBusiness userBusiness;

    private final UserMapper userMapper;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/defaultProject/{id}")
    public void setDefaultProject(@PathVariable("id") ObjectId projectId) {
        userBusiness.setDefaultProject(UserContext.getId(), projectId);
    }

    @GetMapping
    public ResponseEntity<UserDto> get() {
        ObjectId id = UserContext.getId();

        User user = userBusiness.find(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        UserDto userDto = userMapper.toDto(user);

        return ResponseEntity.ok(userDto);
    }
}
