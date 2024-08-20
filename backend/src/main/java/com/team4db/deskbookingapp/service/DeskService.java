package com.team4db.deskbookingapp.service;

import com.team4db.deskbookingapp.model.Desk;
import com.team4db.deskbookingapp.repository.DeskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DeskService {
    @Autowired
    DeskRepository deskRepository;

    public Desk findDeskByDeskId(String deskId) {
        return deskRepository.findDeskByDeskId(deskId);
    }

    public void save(Desk desk) {
        deskRepository.save(desk);
    }

    public void delete(Desk desk) {
        deskRepository.delete(desk);
    }

    public List<Desk> getAllDesks() {
        return deskRepository.findAll();
    }
}
