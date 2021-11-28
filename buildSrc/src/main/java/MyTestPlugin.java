import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * Created by dbf on 2021/5/2
 * describe:
 */
public class MyTestPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        System.out.println("我是 Gradle Plugin");
    }
}
