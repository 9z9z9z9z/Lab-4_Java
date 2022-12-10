package model;

public enum Group {

    admin("admin"),
    user("user");

    private String group;

    Group(String group) {
        this.group = group;
    }

    public String getGroup() {
        return group;
    }
}
