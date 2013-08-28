package lessons.backtracking;

import plm.core.model.Game;
import plm.core.model.lesson.ExecutionProgress;
import plm.core.model.lesson.ExerciseTemplated;
import plm.core.model.lesson.Lesson;
import plm.core.model.lesson.NoSuchEntityException;
import plm.universe.World;

public abstract class BacktrackingExercise extends ExerciseTemplated {
	public BacktrackingExercise(Lesson lesson) {
		super(lesson);
		nameOfCorrectionEntity = getClass().getCanonicalName()+".java";
	}
	protected void setup(World[] ws,BacktrackingEntity solver) {
		for (World w:ws) {
			w.setName(((BacktrackingPartialSolution) w.getParameter(0)).getTitle());
			w.addEntity(solver.copy());
		}
		setupWorlds(ws);
		try {
			// FIXME: Java only aint good
			newSourceFromFile(Game.JAVA,this.tabName, solver.getClass().getCanonicalName(), "java");
		} catch (NoSuchEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  

		computeAnswer();
	}
	protected void computeAnswer() {
		ExecutionProgress progress = new ExecutionProgress();
		
		for (World aw : answerWorld) {
			System.out.println("Compute answer for world "+aw.getName());
			try {
				aw.getEntity(0).runIt(progress);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	protected void newBestSolution(BacktrackingPartialSolution sol) {
		((BacktrackingWorld) getWorlds(WorldKind.CURRENT).get(0)).newBestSolution(sol);
	}

}
