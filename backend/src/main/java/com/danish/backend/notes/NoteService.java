package com.danish.backend.notes;


import com.danish.backend.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }


    public List<Note> getNotesByUser(User user) {
        return noteRepository.findNotesByUser(user);
    }
}
