package tn.esprit.services;

import tn.esprit.models.Comment;
import tn.esprit.models.Post;

public interface Listener {
    void onSupprimerClicked();
    void onModifierClicked(Comment comment);
    void onSupprimerPostClicked();
    void onModifierPostClicked(Post post);
}
