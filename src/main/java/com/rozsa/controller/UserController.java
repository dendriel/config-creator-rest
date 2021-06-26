package com.rozsa.controller;

import com.rozsa.business.UserBusiness;
import com.rozsa.security.UserContext;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {

    private final UserBusiness userBusiness;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/defaultProject/{id}")
    public void setDefaultProject(@PathVariable("id") ObjectId projectId) {
        userBusiness.setDefaultProject(UserContext.getId(), projectId);
    }
}
