package menu;

public class Option {
    private String name;
    private MenuCommand command;

    public Option(String name, MenuCommand command) {
        this.name = name;
        this.command = command;
    }

    public String getName() {return name;}
    public MenuCommand getCommand() {return command;}
}
