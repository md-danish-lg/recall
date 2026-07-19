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
    public ResponseEntity<List<NoteResponse>> getNotes(@AuthenticationPrincipal User user){
        List<Note> note = noteService.getNotesByUser(user);
        List<NoteResponse> noteResponse = note.stream()
                .map(NoteResponse::new)
                .toList();

        return ResponseEntity.ok(noteResponse);
    }

    @PostMapping
    public ResponseEntity<NoteResponse> addNewNote(@AuthenticationPrincipal User user, @RequestBody @Valid NoteRequest noteRequest){
        Note note = noteService.addNote(user, noteRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new NoteResponse(note));

    }

    @PutMapping("{id}")
    public ResponseEntity<NoteResponse> updateNote(
            @AuthenticationPrincipal User user,
            @PathVariable UUID id,
            @RequestBody @Valid NoteRequest noteRequest){

        Note note = noteService.updateNoteById(id, noteRequest, user);
        return ResponseEntity.ok(new NoteResponse(note));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteNote(
            @AuthenticationPrincipal User user,
            @PathVariable UUID id){
        noteService.deleteNoteById(id, user);
        return ResponseEntity.ok("Note Deleted Successfully!");
    }

}
