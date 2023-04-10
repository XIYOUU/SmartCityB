package freemodlue;

public class appraise {
    private String name;
    private String content;
    private int avatar;
    private String time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public appraise(String name, String content, int avatar, String time) {
        this.name = name;
        this.content = content;
        this.avatar = avatar;
        this.time = time;
    }
}
