
import model.HHStrategy;
import model.Model;
import model.MoikrugStrategy;
import model.Provider;
import view.HtmlView;
import view.View;

import java.io.IOException;


/**
 * 3. Для запуска нужно еще обновить метод main в Aggregator.
 3.1. Создай вью, модель, контроллер.
 3.2. Засэть во вью контроллер.
 3.3. Вызови у вью метод  userCitySelectEmulationMethod.

 2. В методе main создай провайдер для MoikrugStrategy. Передай этот провайдер в конструктор Model.
 Это удобно сделать, т.к. модель принимает много провайдеров.
 Остальную логику менять не нужно.

 */
public class Aggregator {


    public static void main(String[] args)
    {
        Provider HHprovider = new Provider(new HHStrategy());
        Provider MKprovider = new Provider(new MoikrugStrategy());
        HtmlView view = new HtmlView();
        Model model = new Model(view, HHprovider, MKprovider);
        Controller controller = new Controller(model);
        view.setController(controller);
        try
        {
            view.userCitySelectEmulationMethod();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
