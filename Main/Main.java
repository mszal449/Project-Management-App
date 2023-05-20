package Main;

import Classes.*;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Project project = new Project("Projekt z po", 20, 6, 2023);
        Project project2 = new Project("Projekt pusty", 13, 5, 2024);

        User assignee1 = new User("Basia", "basia@mail.com", "haslo1");
        User assignee2 = new User("Kasia", "kasia@mail.com", "haslo2");
        User assignee3 = new User("Asia", "asia@mail.com", "haslo3");
        User assignee4 = new User("Lusia", "lusia@mail.com", "haslo4");
        User assignee5 = new User("test", "test", "");
        project.addParticipant(assignee1);
        project.addParticipant(assignee2);
        project.addParticipant(assignee3);
        project.addParticipant(assignee5);
        project2.addParticipant(assignee1);
        project2.addParticipant(assignee4);
        Planned task1 = new Planned("task1",
                1, 5, 2024, 20, 5, 2023);
        Planned task2 = new Planned("task2",
                1, 5, 2024, 20, 5, 2023);
        Current task3 = new Current("task3", 3, 5, 2023);
        Done task4 = new Done("task4", 3, 5, 2023);
        task1.addAssignee(assignee1);
        task1.addAssignee(assignee2);
        task1.addAssignee(assignee3);
        task3.addAssignee(assignee3);
        task4.addAssignee(assignee4);
        project.addTask(task1);
        project.addTask(task2);
        project.addTask(task3);
        project.addTask(task4);
        project.getInfo();

        Planned task5 = new Planned("task2",
                1, 5, 2024, 20, 5, 2023);
        Current task6 = new Current("task3", 3, 5, 2023);
        Done task7 = new Done("task4", 3, 5, 2023);
        project2.addTask(task5);
        project2.addTask(task6);
        project2.addTask(task7);
        task5.addAssignee(assignee1);

        task1.addAssignee(assignee1);
        task1.getInfo();
        System.out.println();

        task1.deleteAssignee(assignee1);
        task1.getInfo();
        System.out.println();
        task1.start();
        project.getInfo();
        project.setPrivileges(assignee1, true);
        project.getInfo();

        DefaultListModel<Project> p = new DefaultListModel<>();
        p.addElement(project);
        p.addElement(project2);
        DefaultListModel<User> u = new DefaultListModel<>();
        u.addElement(assignee1);
        u.addElement(assignee2);
        u.addElement(assignee3);
        u.addElement(assignee4);
        u.addElement(assignee5);
        System.out.println(task1.getAssignees());
        User[] assignees_list_copy = new User[5];
        task1.getAssignees().copyInto(assignees_list_copy);

        // Uruchomienie programu
        MainProgram program = MainProgram.getInstance(u, p);
    }
}
