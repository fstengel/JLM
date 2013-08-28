package plm.core.ui.action;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import plm.core.model.Course;
import plm.core.model.Game;
import plm.core.model.ServerAnswer;
import plm.core.ui.TeacherConsoleDialog;

/**
 * Controller to handle clicks on the refresh button from the teacher console
 * It download updated data from the server and refresh the console ui
 */
public class RefreshCourse extends AbstractGameAction {

	private static final long serialVersionUID = 1L;
	
	private Course course;
    private TeacherConsoleDialog parentComponent;

    public RefreshCourse(Game game, String text, TeacherConsoleDialog parentComponent) {
        super(game, text);
        course = game.getCurrentCourse();
        this.parentComponent = parentComponent;
    }

    public RefreshCourse(Game game, String text, ImageIcon icon, TeacherConsoleDialog parentComponent) {
        super(game, text, icon);
        course = game.getCurrentCourse();
        this.parentComponent = parentComponent;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (course.getCourseId() != null) {
            String answer = course.refresh();
            try {
                if (ServerAnswer.values()[Integer.parseInt(answer)] == ServerAnswer.WRONG_TEACHER_PASSWORD)
                    JOptionPane.showMessageDialog(parentComponent, "Wrong teacher password for the course",
                            "Server error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException nfe) {
             // the answer was not a status message, it contains course data
            }
            // refresh the needing help, layabout, bad and good students lists
            course.refreshStudentsLists();

            parentComponent.refresh();
        }
    }
}
