package logic;

public class Note {
    private final Integer id;
    private final String text;

    public Note(Integer id, String text) {
        this.id = id;
        this.text = text;
    }

    public Integer getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}
