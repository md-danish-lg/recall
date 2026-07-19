package com.danish.backend.notes;


import com.danish.backend.user.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }


    @GetMapping
    public ResponseEntity<List<Note>> getNotes(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(noteService.getNotesByUser(user));
    }

    @PostMapping
    public ResponseEntity<Note> addNewNote(@AuthenticationPrincipal User user, @RequestBody @Valid NoteRequest noteRequest){
        Note note = noteService.addNote(user, noteRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(note);

    }

    @PutMapping("{id}")
    public ResponseEntity<Note> updateNote(
            @AuthenticationPrincipal User user,
            @PathVariable UUID id,
            @RequestBody @Valid NoteRequest noteRequest){

        Note note = noteService.updateNoteById(id, noteRequest, user);
        return ResponseEntity.ok(note);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteNote(
            @AuthenticationPrincipal User user,
            @PathVariable UUID id){
        noteService.deleteNoteById(id, user);
        return ResponseEntity.ok("Note Deleted Successfully!");
    }

}
