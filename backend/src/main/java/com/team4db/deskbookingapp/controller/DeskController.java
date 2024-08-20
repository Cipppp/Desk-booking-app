package com.team4db.deskbookingapp.controller;

import com.team4db.deskbookingapp.jwt.payload.response.MessageResponse;
import com.team4db.deskbookingapp.model.Desk;
import com.team4db.deskbookingapp.repository.DeskRepository;
import com.team4db.deskbookingapp.service.DeskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("desk")
@Slf4j
public class DeskController {

    private final DeskService deskService;
    private final DeskRepository deskRepository;

    @PostMapping("/addDesk")
    public ResponseEntity<?> addDesk(@RequestBody Desk desk) {
        if (desk != null)
            deskService.save(desk);
        else
            return ResponseEntity.ok(new MessageResponse("No desk added. Check request body."));

        return ResponseEntity.ok(new MessageResponse("Desk added successful!"));
    }

    @DeleteMapping("/deleteDesk")
    public ResponseEntity<?> addDesk(@RequestBody String deskId) {
        Desk desk = deskService.findDeskByDeskId(deskId);
        if (desk != null)
            deskService.delete(desk);
        else
            return ResponseEntity.ok(new MessageResponse("No desk found. Check request body."));

        return ResponseEntity.ok(new MessageResponse("Desk deleted successful!"));
    }
}
