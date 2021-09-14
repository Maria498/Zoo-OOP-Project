package animation;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

// Animation class that realize shake method used to note user of wrong data entry or missing fields
public class Shake {
	private TranslateTransition tt;
	
	public Shake(Node node) {
		tt = new TranslateTransition(Duration.millis(70), node);
		tt.setFromX(0f);
		tt.setByY(10f);
		tt.setCycleCount(4);
		tt.setAutoReverse(true);
	}

	public void playAnim() {
		tt.playFromStart();
	}

}
