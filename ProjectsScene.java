import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ProjectsScene extends JPanel {
    DefaultListModel<Project> all_projects;
    JList<Project> Jall_projects;

    public ProjectsScene(DefaultListModel<Project> projects) {
        all_projects = projects;
        Jall_projects = new JList<>(projects);
        CreateProjectsScene();
    }

    private void CreateProjectsScene() {
        new JPanel();
        setLayout(new GridLayout(1, 1));
        addElements();
    }

    private void addElements() {
        Jall_projects.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) { // jeżeli kilknięto 2 razy...
                    // pobranie numeru indeksu
                    int index = Jall_projects.locationToIndex(evt.getPoint());
                    // wybranie wpisu o danym indeksie
                    Project selectedProject = all_projects.get(index);
                    System.out.println("Wybrano projekt " + selectedProject);
                }
            }
        });
        add(Jall_projects);
    }
}
