package model;

public class UserAction {

    private LibraryEntry entry;
    private boolean confirmation;

    public UserAction(final LibraryEntry entry, final boolean confirmation) {
        this.entry = entry;
        this.confirmation = confirmation;
    }

    public LibraryEntry getEntry() {
        return this.entry;
    }

    public boolean getConfirmation() {
        return this.confirmation;
    }
}
