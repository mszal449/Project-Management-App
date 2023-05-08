import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ProjectsScene extends JPanel {
    JList<Project> Jall_projects;

    public ProjectsScene() {
        Jall_projects = new JList<>(MainProgram.getProjects());
        CreateProjectsScene();
    }

    private void CreateProjectsScene() {
        new JPanel();
        setLayout(new BorderLayout());
        addElements();
    }

    private void addElements() {
        DefaultListModel<Project> all_projects = MainProgram.getProjects();
        User logged_user = MainProgram.getLoggedUser();
        add(new Label("Zalogowano jako " + logged_user), BorderLayout.NORTH);
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
        add(Jall_projects, BorderLayout.CENTER);
    }
}
