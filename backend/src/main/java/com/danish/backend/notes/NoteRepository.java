package com.danish.backend.notes;

import com.danish.backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NoteRepository extends JpaRepository<Note, UUID> {

    List<Note> findNotesByUser(User user);

    
}
