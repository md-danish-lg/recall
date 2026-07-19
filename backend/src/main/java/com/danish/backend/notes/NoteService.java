package com.danish.backend.notes;


import com.danish.backend.user.User;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }


    public List<Note> getNotesByUser(User user) {
        return noteRepository.findNotesByUser(user);
    }

    public Note addNote(User user,NoteRequest noteRequest) {

        Note note = new Note();

        note.setTitle(noteRequest.getTitle());
        note.setContent(noteRequest.getContent());

        note.setUser(user);

        return noteRepository.save(note);
    }


    public Note updateNoteById(UUID id, NoteRequest noteRequest, User user) {
        Note note = noteRepository.findById(id).orElseThrow(()->
                new NoteNotFoundException("Note doesnt exist"));

        if(note.getUser().getId().equals(user.getId())){
            note.setTitle(noteRequest.getTitle());
            note.setContent(noteRequest.getContent());
            noteRepository.save(note);
            return note;
        }else{
            throw new NoteNotFoundException("Note doesnt exist");
        }
    }

    public void deleteNoteById(UUID id, User user){
        Note note = noteRepository.findById(id).orElseThrow(()->
                new NoteNotFoundException("Note doesnt exist"));

        if(note.getUser().getId().equals(user.getId())){
            noteRepository.deleteById(id);
        }else{
            throw new NoteNotFoundException("Note doesnt exist");
        }
    }
}
