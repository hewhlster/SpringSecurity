package org.fjh.action;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserAction {

    @GetMapping("add")
    public ResponseEntity add(){
        return ResponseEntity.ok("userAction-add");
    }
}
