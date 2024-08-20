package com.team4db.deskbookingapp.repository;

import com.team4db.deskbookingapp.model.Desk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeskRepository extends JpaRepository<Desk, String> {
    Desk findDeskByDeskId(String id);
}
