package twiliodemo.service;

/**
 * Created by jma on 6/13/14.
 */
public interface FortuneService {
    public void addFortune(String newFortune);
    public String getFortune(Long luckyNumberHint);
}
