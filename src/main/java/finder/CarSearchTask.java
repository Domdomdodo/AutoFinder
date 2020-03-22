package finder;

import finder.view.CarViewWrapper;
import finder.view.MainWindowController;
import org.jsoup.select.Elements;

import java.util.HashSet;

public class CarSearchTask implements Runnable {

    private HashSet<CarViewWrapper> carViewWrappers;
    private MainWindowController mainWindowController;
    public boolean searching;

    public CarSearchTask(HashSet<CarViewWrapper> carViewWrappers, MainWindowController mainWindowController){
        this.carViewWrappers = carViewWrappers;
        this.mainWindowController = mainWindowController;
    }

    @Override
    public void run() {
        try {
            searching = true;
            CarSearcher searcher = new CarSearcher();
            int totalCars = 0;
            for (CarViewWrapper carViewWrapper : carViewWrappers){
                if (carViewWrapper.selected){
                    totalCars++;
                }
            }

            int searched = 0;
            for (CarViewWrapper carViewWrapper : carViewWrappers) {
                if (!carViewWrapper.selected) {
                    continue;
                }
                Thread.sleep(mainWindowController.getSearchDelay() * 1000);
                Elements elements = searcher.search(carViewWrapper.car);

                carViewWrapper.searchUrl = searcher.buildSearchUrl(carViewWrapper.car);

                if (elements.size() > 0) {
                    carViewWrapper.setFound(true);
                } else {
                    carViewWrapper.setFound(false);
                }

                searched++;
                mainWindowController.updateProgress(((double)searched / (double)totalCars));
            }
        }
        catch (InterruptedException e){
            Thread.yield();
        }
        searching = false;
    }
}
