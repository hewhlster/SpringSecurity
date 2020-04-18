package org.fjh.action;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("product")
public class ProductAction {

    @RequestMapping("add")
    public ResponseEntity add(){
        return ResponseEntity.ok("productAction--add");
    }

    @RequestMapping("delete")
    public ResponseEntity delete(){
        return ResponseEntity.ok("productAction--delete");
    }

    @RequestMapping("update")
    public ResponseEntity update(){
        return ResponseEntity.ok("productAction--update");
    }

    @RequestMapping("select")
    public ResponseEntity select(){
        return ResponseEntity.ok("productAction--select");
    }
}
