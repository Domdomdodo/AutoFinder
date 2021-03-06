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

            mainWindowController.setResults(0,0);

            int found = 0;
            int notFound = 0;
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
                    found++;
                } else {
                    carViewWrapper.setFound(false);
                    notFound++;
                }

                searched++;
                mainWindowController.setResults(found, notFound);
                mainWindowController.updateProgress(((double)searched / (double)totalCars));
            }
        }
        catch (InterruptedException e){
            Thread.yield();
        }
        searching = false;
    }
}
