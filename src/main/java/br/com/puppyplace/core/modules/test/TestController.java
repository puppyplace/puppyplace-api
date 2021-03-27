package br.com.puppyplace.core.modules.test;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TestController {
  
  @GetMapping
  public ResponseEntity<?> firstEndPoint(){
    return ResponseEntity.ok().build();
  }

}
