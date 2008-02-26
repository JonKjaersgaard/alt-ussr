package ussr.model;

public interface ActBasedController extends Controller {
    public void initializationActStep();
    public void singleActStep();
}
