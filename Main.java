import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Project project = new Project("Projekt z po", 20, 6, 2023);
        DefaultListModel<User> assignees = new DefaultListModel<>();
        User assignee1 = new User("Basia", "basia@mail.com", "haslo1");
        User assignee2 = new User("Kasia", "kasia@mail.com", "haslo2");
        User assignee3 = new User("Asia", "asia@mail.com", "haslo3");
        project.addParticipant(assignee1);
        project.addParticipant(assignee2);
        project.addParticipant(assignee3);
        // FIXME?: makabryczny jest ten konstruktor, ma 8 argumentów XDD
        Planned task1 = new Planned("task1", assignees,
                1, 5, 2024, 20, 5, 2023);
        Planned task2 = new Planned("task2", assignees,
                1, 5, 2024, 20, 5, 2023);
        Current task3 = new Current("task3", assignees, 3, 5, 2023);
        Done task4 = new Done("task4", assignees, 3, 5, 2023);
        project.addTask(task1);
        project.addTask(task2);
        project.addTask(task3);
        project.addTask(task4);
        project.getInfo();

        task1.addAssignee(assignee1);
        task1.getInfo();
        System.out.println();

        task1.deleteAssignee(assignee1);
        task1.getInfo();
        System.out.println();
    }
}
