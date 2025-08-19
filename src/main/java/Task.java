public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone? "X" : " ");
    }

    public void markAsDone() {
        this.isDone = true;
        System.out.println(Ronaldo.encase("Nice! I've marked this task as done:\n " + this));
    }

    public void unmark() {
        this.isDone = false;
        System.out.println(Ronaldo.encase("OK, I've marked this task as not done yet:\n" + this));
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", this.getStatusIcon(), this.description);
    }
}
