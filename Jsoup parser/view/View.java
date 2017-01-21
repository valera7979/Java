package view;

import Controller;
import vo.Vacancy;

import java.util.List;

/**
 * Created by Валера on 14.01.2017.
 */
public interface View
{

    void update(List<Vacancy> vacancies);

    void setController(Controller controller);

}
