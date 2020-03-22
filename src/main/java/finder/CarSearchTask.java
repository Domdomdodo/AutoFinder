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
            int totalCars = carViewWrappers.size();
            int searched = 0;
            for (CarViewWrapper carViewWrapper : carViewWrappers) {
                if (!carViewWrapper.selected) {
                    continue;
                }
                Thread.sleep(mainWindowController.getSearchDelay() * 1000);
                Elements elements = searcher.search(carViewWrapper.car);

                if (elements.size() > 0) {
                    carViewWrapper.setFound(true);
                    carViewWrapper.url = "test";
                } else {
                    carViewWrapper.setFound(false);
                }

                searched++;
                mainWindowController.updateProgress(searched / totalCars);
            }
        }
        catch (InterruptedException e){
            Thread.yield();
        }
        searching = false;
    }
}
