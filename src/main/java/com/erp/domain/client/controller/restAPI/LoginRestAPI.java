package com.erp.domain.client.controller.restAPI;

import com.erp.domain.client.dto.request.LoginRequestDto;
import com.erp.domain.client.service.ClientService;
import com.erp.global.jwt.TokenInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
public class LoginRestAPI {

    private final ClientService clientService;

    @PostMapping("/login")
    public TokenInfo toClientLogin(@RequestBody LoginRequestDto loginRequestDto){
        return clientService.login(loginRequestDto);
    }

}
